package com.example.sanaebelhaj.thetvbd.Models;

/**
 * Created by Cyriaque on 14/01/2018.
 */

public class TheTVDBLogin {

    private String apikey;
    private String userkey;
    private String username;

    public TheTVDBLogin(String apikey, String userkey, String username){
        this.apikey = apikey;
        this.userkey = userkey;
        this.username = username;
    }
}
