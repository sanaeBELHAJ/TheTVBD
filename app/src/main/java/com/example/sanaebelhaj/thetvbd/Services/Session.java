package com.example.sanaebelhaj.thetvbd.Services;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by guillaumequirin on 21/01/2018.
 */

public class Session {

    private SharedPreferences prefs;

    public Session(Context cntx) {
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setToken(String token) {
        prefs.edit().putString("token", token).commit();
    }

    public String getToken() {
        String token = prefs.getString("token","");
        return token;
    }
}