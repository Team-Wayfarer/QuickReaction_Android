package com.example.QuickReactionMJ.network;


import com.example.QuickReactionMJ.get.GetLoginResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
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
    Call<GetLoginResult> GetAdminLoginResponse(
            @Path("spotAdminId") Long spotAdminId
    );

}
