package com.example.sanaebelhaj.thetvbd.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Cyriaque on 19/01/2018.
 */

public class TheTVDBSearch {

    @SerializedName("id")
    private int id;
    @SerializedName("banner")
    private String banner;
    @SerializedName("firstAired")
    private String firstAired;
    @SerializedName("network")
    private String network;
    @SerializedName("overview")
    private String overview;
    @SerializedName("seriesName")
    private String seriesName;
    @SerializedName("status")
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getFirstAired() {
        return firstAired;
    }

    public void setFirstAired(String firstAired) {
        this.firstAired = firstAired;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
