package com.example.QuickReactionMJ;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterTest extends AppCompatActivity {

    private AlertDialog dialog;
    private boolean validate = false;
    private String checkID = "";
    private String userName = "";
    private String userPhone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_practice);

        CheckBox validateId = (CheckBox) findViewById(R.id.validateCheck1);
        validateId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate) {
                    return;
                }
                if (checkID.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterTest.this);
                    dialog = builder.setMessage("아이디는 빈 칸일 수 없습니다")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterTest.this);
                                dialog = builder.setMessage("사용할 수 있는 아이디입니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                validate = true;
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterTest.this);
                                dialog = builder.setMessage("이미 가입되어 있습니다.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ValidateRequest validateRequest = new ValidateRequest(checkID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterTest.this);
                queue.add(validateRequest);
            }
        });


        final Button register = (Button) findViewById(R.id.registerButton1);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "가입 완료", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}

