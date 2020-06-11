package com.example.QuickReactionMJ;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    TextView spot;
    TextView time;
    IntentIntegrator integrator;
    SharedPreferences sharedPreferences;
    Spinner spinner;
    String token;
    List<String> item;
    ArrayAdapter<String> spinnerArrayAdapter;

    private ArrayAdapter monthAdapter;
    ListView listView;
    ArrayList<VisitData> visitDataList;
    VisitDataAdapter visitDataAdapter;
    private static final String SETTINGS_PLAYER_JSON = "settings_item_json";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.visitInfo);
        listView.setAdapter(visitDataAdapter);
        setSpinner();

        spinner = (Spinner) findViewById(R.id.user_spinner);
        monthAdapter = ArrayAdapter.createFromResource(this, R.array.동선, android.R.layout.simple_spinner_dropdown_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(monthAdapter);

        spot = (TextView) findViewById(R.id.QRresult);
        time = (TextView) findViewById(R.id.timeText);
        integrator = new IntentIntegrator(this);

        final Button QR = (Button) findViewById(R.id.QRbutton);
        QR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                integrator.setPrompt("바코드를 사각형 안에 비춰주세요");
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(true);
                integrator.setCaptureActivity(CaptureActivity.class);
                integrator.setOrientationLocked(false);
                integrator.initiateScan();
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
                getData(result.getContents() ,formatDate);

                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void setSpinner() {

        spinner = (Spinner) findViewById(R.id.spinner);
        item = new ArrayList<>();

        for(int i = 0; i < 14; i++) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            try {
                // String str = "2020/07/22 22:01:11";
                // Date co = sdf.parse(str);
                Calendar today = Calendar.getInstance();
                today.add(Calendar.DAY_OF_MONTH, -1);
                item.add(sdf.format(today.getTime()));
            }
            catch (Exception e) {
                e.getMessage();
            }
        }
    }

    public void getData(String spot, String time) {

        this.spot.setText(spot);
        this.time.setText(time);
    }
}
