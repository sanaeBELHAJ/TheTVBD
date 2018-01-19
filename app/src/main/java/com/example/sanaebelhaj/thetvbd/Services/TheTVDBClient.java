package com.example.sanaebelhaj.thetvbd.Services;

import com.example.sanaebelhaj.thetvbd.Models.TheTVDBLogin;
import com.example.sanaebelhaj.thetvbd.Models.TheTVDBToken;

import java.util.List;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by Cyriaque on 14/01/2018.
 */

public interface TheTVDBClient {

    // Returns a session token
    @POST("/login")
    Call <TheTVDBToken> login(@Body TheTVDBLogin login);

    // Returns basic information about the currently authenticated user.
    @GET("/user")
    Call<ResponseBody> getUserInfo(@Header("Authorization") String token);
}
