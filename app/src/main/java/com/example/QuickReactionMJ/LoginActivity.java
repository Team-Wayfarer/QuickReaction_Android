package com.example.QuickReactionMJ;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        final Button loginUser = (Button) findViewById(R.id.loginUser);
        loginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                startActivity(intent);
            }
        });

        final Button loginAdmin = (Button) findViewById(R.id.loginAdmin);
        loginAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
    }

}
