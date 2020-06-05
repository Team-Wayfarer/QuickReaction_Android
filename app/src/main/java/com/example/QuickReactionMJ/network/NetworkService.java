package com.example.QuickReactionMJ.network;


import com.example.QuickReactionMJ.domain.Spot;
import com.example.QuickReactionMJ.domain.SpotAdmin;
import com.example.QuickReactionMJ.get.GetAdminLoginResult;
import com.example.QuickReactionMJ.post.PostAdminJoinResult;
import com.example.QuickReactionMJ.post.PostSpotSaveResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface NetworkService {
/*

    @HTTP(method = "GET", path = "/cfcqr/api/spotAdmins/", hasBody = true)
    Call<GetLoginResult> GetAdminLoginResponse(
            @Header("Content-Type") String content_type,
            @Body JsonObject body
    );*/


    //점주 로그인
    @GET("/cfcqr/api/spotAdmins/{spotAdminId}")
    @Headers({"Name:Content-Type"})
    Call<GetAdminLoginResult> GetAdminLoginResponse(
            @Path("spotAdminId") Long spotAdminId
    );

    //점주 전체조회
    @GET("/cfcqr/api/spotAdmins/")
    @Headers({"Name:Content-Type"})
    Call<List<GetAdminLoginResult>> GetAdminFindAllResponse(
    );

    //점주 회원가입
    @POST("/cfcqr/api/spotAdmins/")
    @Headers({"Name:Content-Type"})
    Call<PostAdminJoinResult> PostAdminJoinResponse(
            @Body SpotAdmin spotAdmin
            );

    //스팟 등록
    @POST("/cfcqr/api/spots/{spotAdminId}")
    @Headers({"Name:Content-Type"})
    Call<PostSpotSaveResult> PostSpotSaveResponse(
            @Path("spotAdminId") Long spotAdminId,
            @Body Spot spot
    );




}
