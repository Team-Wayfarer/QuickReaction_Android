package com.example.QuickReactionMJ;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_practice);


        final EditText userNameText = (EditText) findViewById(R.id.registerID1);
        final EditText userContactText = (EditText) findViewById(R.id.registerContact1);


        final Button register = (Button) findViewById(R.id.registerButton1);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = userNameText.getText().toString();
                String userContact = userContactText.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject( response );
                            boolean success = jsonObject.getBoolean( "success" );

                            //회원가입 성공시
                            if(success) {

                                Toast.makeText( getApplicationContext(), "가입 성공", Toast.LENGTH_SHORT ).show();
                                Intent intent = new Intent( RegisterTest.this, LoginActivity.class );
                                startActivity( intent );

                                //회원가입 실패시
                            } else {
                                Toast.makeText( getApplicationContext(), "가입 실패", Toast.LENGTH_SHORT ).show();
                                return;
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                RegisterRequest registerRequest = new RegisterRequest(userContact, userName, responseListener);
                RequestQueue queue = Volley.newRequestQueue( RegisterTest.this );
                queue.add( registerRequest );
            }
        });
    }
}

