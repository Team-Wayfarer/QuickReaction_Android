package com.example.QuickReactionMJ;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ManagerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manager_layout);

        Button moveQRMaker = (Button) findViewById(R.id.makeButton);
        Button spotSave = (Button) findViewById(R.id.makeButton4);


        moveQRMaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManagerActivity.this, QRMaker.class);
                startActivity(intent);
            }
        });

        //스팟저장
        spotSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManagerActivity.this, RegisterSpotActivity.class);
                startActivity(intent);
            }
        });


    }
}
