package com.example.QuickReactionMJ;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.QuickReactionMJ.db.SharedPreferenceController;
import com.example.QuickReactionMJ.network.ApplicationController;
import com.example.QuickReactionMJ.network.NetworkService;
import com.example.QuickReactionMJ.post.PostScanQrResult;
import com.example.QuickReactionMJ.rest.Rest;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;

import static android.content.ContentValues.TAG;

public class UserActivity extends AppCompatActivity {
    TextView spot;
    TextView time;
    IntentIntegrator integrator;
    ArrayList<VisitData> visitDataList;
    VisitDataAdapter visitDataAdapter;

    ArrayList<VisitData> diseaseList;
    EditText diseaseStr;

    ListView listView;

    private static final String SETTINGS_PLAYER_JSON = "settings_item_json";
    private long startTime = 0;

    private NetworkService networkService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_layout);

        networkService = ApplicationController.getInstance().getNetworkService();

        this.InitializeVisitData();
        this.InitializeDiseaseData();

        spot = (TextView) findViewById(R.id.QRresult);
        time = (TextView) findViewById(R.id.timeText);

        listView = (ListView) findViewById(R.id.visitInfo);

        //user 방문 리스트
        visitDataList = getStringArrayPref(getApplicationContext(), SETTINGS_PLAYER_JSON);
        visitDataAdapter = new VisitDataAdapter(this, visitDataList);
        listView.setAdapter(visitDataAdapter);

        integrator = new IntentIntegrator(this);

        final Button QR = (Button) findViewById(R.id.QRbutton);
        QR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                integrator.setPrompt("바코드를 사각형 안에 비춰주세요");
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(true);
                integrator.setCaptureActivity(CaptureActivity.class);
                integrator.setOrientationLocked(false);
                integrator.initiateScan();
            }
        });

        final Button disease = (Button) findViewById(R.id.diseaseButton);
        disease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText disease = (EditText) findViewById(R.id.diseaseStr);
                String diseaseVisit = disease.getText().toString();

                long nowTime = System.currentTimeMillis();
                Date date = new Date(nowTime);
                SimpleDateFormat sdNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String formatDate = sdNow.format(date);

                diseaseList.add(new VisitData(diseaseVisit, formatDate));
                disease.setText("");
            }
        });

        final Button find = (Button) findViewById(R.id.findDisease);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkDisease();
            }
        });

    }
    @Override protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
            } else {
                long nowTime = System.currentTimeMillis();
                Date date = new Date(nowTime);
                SimpleDateFormat sdNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String formatDate = sdNow.format(date);

                visitDataList.add(new VisitData(result.getContents(), formatDate));
                visitDataAdapter.notifyDataSetChanged();

                //서버통신
                Long userId = Long.parseLong(SharedPreferenceController.INSTANCE.getAuthorizationOfId(UserActivity.this));
                Long spotId = Long.parseLong(result.getContents());

                Call<PostScanQrResult> call = networkService.PostQrScanResult(userId,spotId);
                Rest.QrScanMethod(call);

                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - startTime >= 2000) {
            startTime = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(), "한번 더 누르면 종료합니다!", Toast.LENGTH_SHORT).show();
        } else if (System.currentTimeMillis() - startTime < 2000) {
            finish();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        setStringArrayPref(getApplicationContext(), SETTINGS_PLAYER_JSON, visitDataList);
        Log.d(TAG, "Put json");
    }

    private void setStringArrayPref(Context context, String key, ArrayList<VisitData> values) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();

        Gson gson = new Gson();
        try {
            JSONArray jsonArray = new JSONArray();
            String json = "";
            JSONObject jsonObject;
            for (int i = 0; i < values.size(); i++) {
                jsonObject = new JSONObject();
                jsonObject.put("visit", values.get(i).getVisit());
                jsonObject.put("time", values.get(i).getTime());
                jsonArray.put(jsonObject);
            }
            if (!values.isEmpty()) {
                editor.putString(key, jsonArray.toString());
            } else {
                editor.putString(key, null);
            }
            System.out.println(json);
            editor.apply();
        }
        catch (Exception e) {
            e.getMessage();
        }
    }

    private ArrayList<VisitData> getStringArrayPref(Context context, String key) {

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String json = prefs.getString(key, null);

        ArrayList<VisitData> arrayList = new ArrayList();

        if(json != null) {
            try {
                JSONArray jsonArray = new JSONArray(json);
                for(int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String visit = jsonObject.optString("visit");
                    String time = jsonObject.optString("time");
                    if(calculateDate(time)) {
                        arrayList.add(new VisitData(visit, time));
                    }
                }

            } catch (Exception e) {
                e.getMessage();
            }
        }

        return arrayList;
    }


    public void InitializeVisitData() {
        visitDataList = new ArrayList<VisitData>();
    }

    public void InitializeDiseaseData() {
        diseaseList = new ArrayList<VisitData>();
    }

    // 방문기록 저장 기간
    private boolean calculateDate(String time) {
        long nowTime = System.currentTimeMillis();
        Date date = new Date(nowTime);
        Date visitTime = null;

        DateFormat parser = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss");

        try {
            visitTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(time);

        }
        catch (Exception e) {
            e.getMessage();
        }
        // 일 : 86400000  시간 : 3600000  분 : 60000
        long result = (date.getTime() - visitTime.getTime()) / 3600000;
        if (result < 1) {
            return true;
        }
        return false;
    }

    protected void checkDisease() {
        for(int i = 0; i < visitDataList.size(); i++) {
            checkVisitAndTime(visitDataList.get(i).getVisit(), visitDataList.get(i).getTime(), i);
        }
    }

    protected void checkVisitAndTime(String visit, String time, int index) {

        for(int i = 0; i < diseaseList.size(); i++) {
            if(diseaseList.get(i).getVisit().equals(visit)) {
                View view = listView.getChildAt(index);
                try {
                    Date visitTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(time);
                    Date diseaseTime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(diseaseList.get(i).getTime());
                    if(diseaseTime.getTime() < visitTime.getTime()) {
                        String setColor = "#FF1111";

                        TextView visitText = view.findViewById(R.id.QRresult);
                        TextView timeText = view.findViewById(R.id.timeText);
                        visitText.setTextColor(Color.parseColor(setColor));
                        timeText.setTextColor(Color.parseColor(setColor));
                    }
                }
                catch (Exception e) {
                    e.getMessage();
                }
            }
        }
    }
}
