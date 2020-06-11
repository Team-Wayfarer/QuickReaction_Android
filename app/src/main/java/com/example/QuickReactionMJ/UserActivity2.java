package com.example.QuickReactionMJ;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.QuickReactionMJ.db.SharedPreferenceController;

public class UserActivity2 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_afterlayout);

        Button moveQRMaker = (Button) findViewById(R.id.userAfter_logout_Button);

        moveQRMaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserActivity2.this, LoginActivity.class);
                SharedPreferenceController.INSTANCE.clearSPC(UserActivity2.this);
                startActivity(intent);
            }
        });
    }
}
