package com.example.sanaebelhaj.thetvbd.Services;

import com.example.sanaebelhaj.thetvbd.Models.TheTVDBLogin;
import com.example.sanaebelhaj.thetvbd.Models.TheTVDBToken;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Cyriaque on 14/01/2018.
 */

public interface TheTVDBClient {

    /**
     *  Authentication
     *  Obtaining and refreshing your JWT token
    */

    // Returns a session token
    @POST("/login")
    Call <TheTVDBToken> login(@Body TheTVDBLogin login);

    // Refresh Token
    @GET("/refresh_token")
    Call <TheTVDBToken> refresh_token(@Body TheTVDBToken token);

    /**
     *  Episodes
     *  Information about a specific episode
     */

    // Information about a specific episode
    @GET("/episodes/{id}")
    Call <ResponseBody> episodeInfo(@Path(value = "id") int idEpisode);

    /**
     *  Languages
     *  Available languages and information
     */

    /**
     *  Search
     *  Search for a particular series
     */
    @GET("/search/series")
    Call <ResponseBody> search(@Header("Authorization") String token,@Query("name") String keywords);

    /**
     *  Series
     *  Information about a specific series
     */

    // Returns information about the serie
    @GET("/series/{id}")
    Call<ResponseBody> getInfos(@Header("Authorization") String token, @Path(value = "id") String id);

    // Returns actors about a serie
    @GET("/series/{id}/actors")
    Call<ResponseBody> getActors(@Header("Authorization") String token, @Path(value = "id") String id);

    /**
     *  Updates
     *  Series that have been recently updated.
     */

    // Returns information about the serie
    @GET("/updated/query")
    Call<ResponseBody> getUpdated(@Header("Authorization") String token, @Query("fromTime") String fromTime);


    /**
     *  Users
     *  Routes for handling user data.
     */

    // Returns basic information about the currently authenticated user.
    @GET("/user")
    Call<ResponseBody> getUserInfo(@Header("Authorization") String token);


    // Returns list of the favorites series for the user
    @GET("/user/favorites")
    Call<ResponseBody> getFavorites(@Header("Authorization") String token);

    // Add a serie to the favorite list
    @PUT("/user/favorites/{id}")
    Call<ResponseBody> addFavorite(@Header("Authorization") String token, @Path(value = "id") String id);

    // Remove a serie to the favorite list
    @DELETE("/user/favorites/{id}")
    Call<ResponseBody> rmvFavorite(@Header("Authorization") String token, @Path(value = "id") String id);
}
