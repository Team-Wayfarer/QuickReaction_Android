package com.example.QuickReactionMJ;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.QuickReactionMJ.domain.SpotAdmin;
import com.example.QuickReactionMJ.network.ApplicationController;
import com.example.QuickReactionMJ.network.NetworkService;
import com.example.QuickReactionMJ.post.PostAdminJoinResult;
import com.example.QuickReactionMJ.rest.Rest;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;

public class RegisterTest extends AppCompatActivity {

    private NetworkService networkService; //NetworkService 객체 생성

    private AlertDialog dialog;
    private boolean validate = false;
    private String checkID = "";
    private String userName = "";
    private String userPhone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_practice);

        //통신
        networkService = ApplicationController.getInstance().getNetworkService();

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

                EditText ed_id = (EditText)findViewById(R.id.registerID1);
                EditText ed_pw = (EditText)findViewById(R.id.registerPassWord1);
                EditText ed_pn = (EditText)findViewById(R.id.registerPhoneNumber1);

                //ID, PASS, NUMBER로 일단 채울거임 테스트라..
                String input_businessNumber = ed_id.getText().toString();
                String input_contact = ed_pw.getText().toString();
                String input_name = ed_pn.getText().toString();

                SpotAdmin dto = new SpotAdmin(input_businessNumber,input_contact,input_name);
                Log.i("회원가입",dto.toString());

                //연결
                Call<PostAdminJoinResult> adminJoinCall = networkService.PostAdminJoinResponse(dto);
                Rest.AdminJoinMethod(adminJoinCall);


                Toast.makeText(getApplicationContext(), "가입 완료", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}

