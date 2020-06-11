package com.example.QuickReactionMJ;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    TextView spot;
    TextView time;
    IntentIntegrator integrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spot = (TextView) findViewById(R.id.QRresult);
        time = (TextView) findViewById(R.id.timeText);
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

    public void getData(String spot, String time) {

        this.spot.setText(spot);
        this.time.setText(time);
    }
}
