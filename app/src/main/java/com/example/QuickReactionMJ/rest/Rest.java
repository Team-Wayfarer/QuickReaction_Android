package com.example.QuickReactionMJ.rest;

import android.util.Log;

import com.example.QuickReactionMJ.domain.UserLoginDto;
import com.example.QuickReactionMJ.get.GetAdminLoginResult;
import com.example.QuickReactionMJ.network.MyEventListener;
import com.example.QuickReactionMJ.post.PostAdminJoinResult;
import com.example.QuickReactionMJ.post.PostAdminLoginResult;
import com.example.QuickReactionMJ.post.PostScanQrResult;
import com.example.QuickReactionMJ.post.PostSpotSaveResult;
import com.example.QuickReactionMJ.post.PostUserJoinResult;
import com.example.QuickReactionMJ.post.PostUserLoginResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Rest {

    /** 이벤트를 전달 받을 인터페이스 */
    private static MyEventListener mListener;

    public void setMyEventListener(MyEventListener listener) { mListener = listener; }


    //어드민 상세조회
    public static void AdminDetailMethod(Call<GetAdminLoginResult> callParam){

        final String error_str = "AdminLogin";

        callParam.enqueue(new Callback<GetAdminLoginResult>() {
            @Override
            public void onResponse(Call<GetAdminLoginResult> call, Response<GetAdminLoginResult> response) {
                if (response.isSuccessful()) {
                    Log.i(error_str + " : suc ", response.body().getBusinessNumber());
                    mListener.onMyEvent(true);
                } else {
                    if (response.code() == 500) ;
                    else if (response.code() == 503);
                    else if (response.code() == 401);
                    //요청 실패, 응답 코드 봐야 함
                }
            }

            @Override
            public void onFailure(Call<GetAdminLoginResult> call, Throwable t) {
                Log.i(error_str + " : fail ",  t.getMessage());
                mListener.onMyEvent(false);
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


    //유저 로그인
    public static void UserLoginMethod(final Call<PostUserLoginResult> callParam){

        callParam.enqueue(new Callback<PostUserLoginResult> () {

            String error_str = "UserLogin";

            @Override
            public void onResponse(Call<PostUserLoginResult> call, Response<PostUserLoginResult> response) {
                if (response.isSuccessful()) {
                    Log.i(error_str + " : suc 1 ", "" + response.body().getAccessToken());
                    Log.i(error_str + " : suc 2 ", "" + response.body().getTokenType());
                    Log.i(error_str + " : suc 3 ", "" + response.body().getId());

                    mListener.onTokenReceiveEvent(true, response.body().getAccessToken(), "USER", response.body().getId());
                    mListener.onMyEvent(true);
                 } else {

                    if (response.code() == 500)  Log.i(error_str + " : fail  ", "500");
                    else if (response.code() == 503)  Log.i(error_str + " : fail  ", "503");
                    else if (response.code() == 401)  Log.i(error_str + " : fail  ", "401");
                    //요청 실패, 응답 코드 봐야 함
                    mListener.onMyEvent(false);
                }
            }

            @Override
            public void onFailure(Call<PostUserLoginResult> call, Throwable t) {
                Log.i(error_str + " : fail ",  t.getMessage());
                mListener.onMyEvent(false);
            }
        });
    }

    //User 등록
    public static void UserJoinMethod(Call<PostUserJoinResult> callParam){

        final String error_str = "UserJoin";

        callParam.enqueue(new Callback<PostUserJoinResult> () {
            @Override
            public void onResponse(Call<PostUserJoinResult> call, Response<PostUserJoinResult> response) {
                if (response.isSuccessful()) {
                    Log.i( error_str + " : suc 1 ", "" );

                } else {

                    if (response.code() == 500)  Log.i(error_str + " : fail  ", "500");
                    else if (response.code() == 503)  Log.i(error_str + " : fail  ", "503");
                    else if (response.code() == 401)  Log.i(error_str +" : fail  ", "401");
                    //요청 실패, 응답 코드 봐야 함
                }
            }

            @Override
            public void onFailure(Call<PostUserJoinResult> call, Throwable t) {
                Log.i( error_str + ": fail ",  t.getMessage());

            }
        });
    }

    //점주 로그인
    public static void AdminLoginMethod(Call<PostAdminLoginResult> callParam){

        callParam.enqueue(new Callback<PostAdminLoginResult> () {

            String error_str = "AdminLogin";

            @Override
            public void onResponse(Call<PostAdminLoginResult> call, Response<PostAdminLoginResult> response) {
                if (response.isSuccessful()) {
                    Log.i(error_str + " : suc 1 ", "" + response.body().getAccessToken());
                    Log.i(error_str + " : suc 2 ", "" + response.body().getId());
                    Log.i(error_str + " : suc 3 ", "" + response.body().getTokenType());
                    mListener.onTokenReceiveEvent(true, response.body().getAccessToken(), "ADMIN",response.body().getId());
                    mListener.onMyEvent(true);
                } else {

                    if (response.code() == 500)  Log.i(error_str + " : fail  ", "500");
                    else if (response.code() == 503)  Log.i(error_str + " : fail  ", "503");
                    else if (response.code() == 401)  Log.i(error_str + " : fail  ", "401");
                    //요청 실패, 응답 코드 봐야 함
                    mListener.onMyEvent(false);
                }
            }

            @Override
            public void onFailure(Call<PostAdminLoginResult> call, Throwable t) {
                Log.i(error_str + " : fail ",  t.getMessage());
                mListener.onMyEvent(false);

            }
        });
    }

    //QR 스캔
    public static void QrScanMethod(Call<PostScanQrResult> callParam){

        callParam.enqueue(new Callback<PostScanQrResult> () {

            String error_str = "QrScan";

            @Override
            public void onResponse(Call<PostScanQrResult> call, Response<PostScanQrResult> response) {
                if (response.isSuccessful()) {
                    Log.i(error_str + " : suc 1 ", "" + response.body().getVisitInfo_Id());
                    mListener.onMyEvent(true);
                } else {

                    if (response.code() == 500)  Log.i(error_str + " : fail  ", "500");
                    else if (response.code() == 503)  Log.i(error_str + " : fail  ", "503");
                    else if (response.code() == 401)  Log.i(error_str + " : fail  ", "401");
                    //요청 실패, 응답 코드 봐야 함
                    mListener.onMyEvent(false);
                }
            }

            @Override
            public void onFailure(Call<PostScanQrResult> call, Throwable t) {
                Log.i(error_str + " : fail ",  t.getMessage());
                mListener.onMyEvent(false);

            }
        });
    }




}
