package com.example.QuickReactionMJ;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.QuickReactionMJ.get.GetLoginResult;
import com.example.QuickReactionMJ.network.ApplicationController;
import com.example.QuickReactionMJ.network.NetworkService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    private AlertDialog dialog;
    private NetworkService networkService; //NetworkService 객체 생성

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        //통신
        networkService = ApplicationController.getInstance().getNetworkService();

        final EditText idStr = (EditText) findViewById(R.id.id);
        final EditText passStr = (EditText) findViewById(R.id.pass);
        final boolean adminStr = false;

        final Button loginUser = (Button) findViewById(R.id.loginUser);
        loginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        /*
        final Button loginAdmin = (Button) findViewById(R.id.loginAdmin);
        loginAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ManagerActivity.class);
                startActivity(intent);
            }
        });*/

        final Button login = (Button) findViewById(R.id.loginAdmin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Long id = Long.parseLong(idStr.getText().toString());
                String pass = passStr.getText().toString();

                /*
                JSONObject jsonObject  = new JSONObject();
                JsonObject gsonObject = new JsonObject();
                JsonParser jsonParser = new JsonParser();


                //로그인 json형식 만들기
                try {
                    jsonObject.put("spotAdminId",id);
                    Log.i("TEST1", jsonObject.toString());
                    Log.i("TEST2", "로그인 됨");
                    Log.i("TEST2-1", networkService.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                gsonObject = (JsonObject) jsonParser.parse(jsonObject.toString());
                Log.i("TEST3", gsonObject.toString());
                */

                Call<GetLoginResult> joinContentCall = networkService.GetAdminLoginResponse(id);


                joinContentCall.enqueue(new Callback<GetLoginResult>() {
                    @Override
                    public void onResponse(Call<GetLoginResult> call, Response<GetLoginResult> response) {
                        if (response.isSuccessful()) {
                            Log.i("LoginAcitivity : suc ", response.body().getBusinessNumber());
                        } else {
                            if (response.code() == 500);
                            else if (response.code() == 503);
                            else if (response.code() == 401);
                            //요청 실패, 응답 코드 봐야 함
                        }
                    }

                    @Override
                    public void onFailure(Call<GetLoginResult> call, Throwable t) {
                        Log.i("LoginAcitivity : fail ",  t.getMessage());

                    }
                });
            }
        });


        final CheckBox loginCheck = (CheckBox) findViewById(R.id.loginCheck);
        loginCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setMessage("자동 로그인으로 설정하시겠습니까?");
                builder.setPositiveButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        loginCheck.setChecked(false);
                    }
                });
                builder.setNegativeButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        loginCheck.setChecked(true);
                    }
                });
                builder.create().show();
            }
        });

        final Button loginPractice = (Button) findViewById(R.id.go_register);
        loginPractice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, AgreeActivity.class);
                startActivity(intent);
            }
        });

        final Button map = (Button) findViewById(R.id.mapButton);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });
        getHashKey();
    }

    private void getHashKey(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }
}
