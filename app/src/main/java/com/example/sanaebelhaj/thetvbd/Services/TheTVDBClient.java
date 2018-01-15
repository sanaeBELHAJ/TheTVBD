package com.example.sanaebelhaj.thetvbd.Services;

import com.example.sanaebelhaj.thetvbd.Models.TheTVDBRepo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Cyriaque on 14/01/2018.
 */

public interface TheTVDBClient {

    @GET("/series/{id}")
    Call <List<TheTVDBRepo>> repoSeries(@Path("id") String series);

}
