package com.example.QuickReactionMJ;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.QuickReactionMJ.domain.Address;
import com.example.QuickReactionMJ.domain.Spot;
import com.example.QuickReactionMJ.network.ApplicationController;
import com.example.QuickReactionMJ.network.NetworkService;
import com.example.QuickReactionMJ.post.PostSpotSaveResult;
import com.example.QuickReactionMJ.rest.Rest;

import retrofit2.Call;

public class RegisterSpotActivity extends AppCompatActivity {

    private NetworkService networkService; //NetworkService 객체 생성

    private AlertDialog dialog;
    private boolean validate = false;
    private String checkID = "";
    private String userName = "";
    private String userPhone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_spot);

        //통신
        networkService = ApplicationController.getInstance().getNetworkService();


        final Button register = (Button) findViewById(R.id.spotSave_button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                EditText ed_city = (EditText)findViewById(R.id.spotSaveCity_edittxt);
                EditText ed_detail = (EditText)findViewById(R.id.spotSaveBuilName_edittxt);
                EditText ed_gungu = (EditText)findViewById(R.id.spotSaveGungu_edittxt);
                EditText ed_zipCode = (EditText)findViewById(R.id.spotSaveHosu_edittxt);

                EditText ed_lat = (EditText)findViewById(R.id.spotSaveLat_edittxt);
                EditText ed_lng = (EditText)findViewById(R.id.spotSaveLng_edittxt);
                EditText ed_name = (EditText)findViewById(R.id.spotSaveName_edittxt);


                //순서대로
                String input_city = ed_city.getText().toString();
                String input_detail = ed_detail.getText().toString();
                String input_gungu = ed_gungu.getText().toString();
                String input_zipCode = ed_zipCode.getText().toString();

                String input_lat = ed_lat.getText().toString();
                String input_lng = ed_lng.getText().toString();
                String input_name = ed_name.getText().toString();

                //시티, 디테일, 군구, 집코드
                Address addressDto = new Address(input_city,input_detail,input_gungu,input_zipCode);
                Log.i("주소 입력",addressDto.toString());

                Spot dto = new Spot(addressDto,input_lat,input_lng,input_name);
                Log.i("스팟 입력",dto.toString());


                //연결
                //long 부분은 Admin_id
                Call<PostSpotSaveResult> spotSaveCall = networkService.PostSpotSaveResponse((long) 24,dto);
                Rest.SpotSaveMethod(spotSaveCall);




                Toast.makeText(getApplicationContext(), "저장 완료", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}

