package com.example.QuickReactionMJ;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class AgreeActivity extends AppCompatActivity {

    CheckBox op1, op2, op3, allCheck;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_info);

        next = (Button) findViewById(R.id.nextButton);
        allCheck = (CheckBox) findViewById(R.id.allCheck);
        op1 = (CheckBox) findViewById(R.id.option1);
        op1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allCheck(op1, allCheck);
            }
        });

        op2 = (CheckBox) findViewById(R.id.option2);
        op2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allCheck(op2, allCheck);
            }
        });

        op3 = (CheckBox) findViewById(R.id.option3);
        op3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allCheck(op3, allCheck);
            }
        });

        allCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (allCheck.isChecked()) {
                    op1.setChecked(true);
                    op2.setChecked(true);
                    op3.setChecked(true);
                } else {
                    op1.setChecked(false);
                    op2.setChecked(false);
                    op3.setChecked(false);
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(allCheck.isChecked()) {
                    Intent intent = new Intent(AgreeActivity.this, RegisterTest.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AgreeActivity.this);
                    builder.setMessage("모든 약관에 동의하셔야 합니다.");
                    builder.setPositiveButton("확인", null);
                    builder.create().show();
                }
            }
        });

    }

    public void allCheck(CheckBox op, CheckBox all) {
        if(!op.isChecked()) {
            all.setChecked(false);
        }
    }
}
