package com.example.sanaebelhaj.thetvbd.Models;

/**
 * Created by Sanae BELHAJ on 16/01/2018.
 */

public class Series {
    public long id;
    public int note_user;
    public int note_tvdb;
    public String episode;
    public String image;
    public String poster;

    public Series(int note_user, int note_tvdb, String episode, String image, String poster) {
        this.note_user = note_user;
        this.note_tvdb = note_tvdb;
        this.episode = episode;
        this.image = image;
        this.poster = poster;
    }
    public long getId(){
        return id;
    }
    public int getNote_user(){
        return note_user;
    }
    public int getNote_tvbd(){
        return note_user;
    }
    public String getEpisode(){

        return episode;
    }
    public String getImage(){
        return image;
    }
    public String getPoster(){
        return poster;
    }
    public void setNote_user(int note_user){
        this.note_user = note_user;
    }
    public void setNote_tvdb(int note_tvdb){
        this.note_tvdb = note_tvdb;
    }
    public void setEpisode(String episode){
        this.episode=episode;
    }
    public void setEmail(String image){
        this.image=image;
    }
    public void setPwd(String poster){
        this.poster=poster;
    }

}
