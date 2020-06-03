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

import com.example.QuickReactionMJ.get.GetAdminLoginResult;
import com.example.QuickReactionMJ.network.ApplicationController;
import com.example.QuickReactionMJ.network.NetworkService;
import com.example.QuickReactionMJ.rest.Rest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;


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

                //점주 전체조회
               /*
                Call<List<GetAdminLoginResult>> adminFindAllCall = networkService.GetAdminFindAllResponse();
                Rest.AdminFindAllMethod(adminFindAllCall);

                */

                //점주 로그인 연결
                 Call<GetAdminLoginResult> adminLoginCall = networkService.GetAdminLoginResponse(id);
                 Rest.AdminLoginMethod(adminLoginCall);

                 Intent intent = new Intent(LoginActivity.this, ManagerActivity.class);
                 startActivity(intent);




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
