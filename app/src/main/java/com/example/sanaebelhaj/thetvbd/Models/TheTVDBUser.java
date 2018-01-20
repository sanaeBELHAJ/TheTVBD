package com.example.sanaebelhaj.thetvbd.Models;

import okhttp3.ResponseBody;

/**
 * Created by guillaumequirin on 20/01/2018.
 */

public class TheTVDBUser {
    private ResponseBody data;

    public ResponseBody getData() {
        return data;
    }
    public void setData(ResponseBody data) {
        this.data = data;
    }
}
