package com.example.QuickReactionMJ.rest;

import android.util.Log;

import com.example.QuickReactionMJ.get.GetAdminLoginResult;
import com.example.QuickReactionMJ.post.PostAdminJoinResult;
import com.example.QuickReactionMJ.post.PostSpotSaveResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Rest {


    //어드민 로그인
    public static void AdminLoginMethod(Call<GetAdminLoginResult> callParam){

        callParam.enqueue(new Callback<GetAdminLoginResult>() {
            @Override
            public void onResponse(Call<GetAdminLoginResult> call, Response<GetAdminLoginResult> response) {
                if (response.isSuccessful()) {
                    Log.i("AdminLogin : suc ", response.body().getBusinessNumber());
                } else {
                    if (response.code() == 500);
                    else if (response.code() == 503);
                    else if (response.code() == 401);
                    //요청 실패, 응답 코드 봐야 함
                }
            }

            @Override
            public void onFailure(Call<GetAdminLoginResult> call, Throwable t) {
                Log.i("AdminLogin : fail ",  t.getMessage());

            }
        });


    }


    //어드민 회원가입
    public static void AdminJoinMethod(Call<PostAdminJoinResult> callParam){

        callParam.enqueue(new Callback<PostAdminJoinResult>() {
            @Override
            public void onResponse(Call<PostAdminJoinResult> call, Response<PostAdminJoinResult> response) {
                if (response.isSuccessful()) {
                    Log.i("AdminJoin : suc ", response.body().getSpotAdmin_id());
                } else {
                    if (response.code() == 500);
                    else if (response.code() == 503);
                    else if (response.code() == 401);
                    //요청 실패, 응답 코드 봐야 함
                }
            }

            @Override
            public void onFailure(Call<PostAdminJoinResult> call, Throwable t) {
                Log.i("AdminJoin : fail ",  t.getMessage());

            }
        });
    }

    //Admin 전체조회
    public static void AdminFindAllMethod(Call<List<GetAdminLoginResult>> callParam){

        callParam.enqueue(new Callback<List<GetAdminLoginResult>> () {
            @Override
            public void onResponse(Call<List<GetAdminLoginResult>> call, Response<List<GetAdminLoginResult>> response) {
                if (response.isSuccessful()) {
                    Log.i("AdminFindAll : suc ", "" + response.body().size());
                } else {
                    if (response.code() == 500);
                    else if (response.code() == 503);
                    else if (response.code() == 401);
                    //요청 실패, 응답 코드 봐야 함
                }
            }

            @Override
            public void onFailure(Call<List<GetAdminLoginResult>> call, Throwable t) {
                Log.i("AdminFindAll : fail ",  t.getMessage());

            }
        });
    }

    //Spot 등록
    public static void SpotSaveMethod(Call<PostSpotSaveResult> callParam){

        callParam.enqueue(new Callback<PostSpotSaveResult> () {
            @Override
            public void onResponse(Call<PostSpotSaveResult> call, Response<PostSpotSaveResult> response) {
                if (response.isSuccessful()) {
                    Log.i("SpotSave : suc 1 ", "" + response.body().getName());
                    Log.i("SpotSave : suc 2 ", "" + response.body().toString());

                } else {

                    if (response.code() == 500)  Log.i("SpotSave : fail  ", "500");
                    else if (response.code() == 503)  Log.i("SpotSave : fail  ", "503");
                    else if (response.code() == 401)  Log.i("SpotSave : fail  ", "401");
                    //요청 실패, 응답 코드 봐야 함
                }
            }

            @Override
            public void onFailure(Call<PostSpotSaveResult> call, Throwable t) {
                Log.i("SpotSave : fail ",  t.getMessage());

            }
        });
    }




}
