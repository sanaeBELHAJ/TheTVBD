package com.example.sanaebelhaj.thetvbd.Models;

/**
 * Created by Sanae BELHAJ on 13/10/2017.
 */

public class User {
    private long id;
    public String firstname;
    public String lastname;
    public String pseudo;
    public String email;
    public String pwd;
    public String language;

    public User(String firstname, String lastname, String pseudo, String email, String pwd, String language) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.pseudo = pseudo;
        this.email = email;
        this.pwd = pwd;
        this.language = language;
    }
    public long getId(){
        return id;
    }

    public String getFirstname(){
        return firstname;
    }

    public String getLastname(){
        return lastname;
    }
    public String getEmail(){
        return email;
    }
    public String getPseudo(){
        return pseudo;
    }
    public String getPwd(){
        return pwd;
    }
    public String getLanguage(){
        return language;
    }
    public void setFirstname(String firstname){
        this.firstname = firstname;
    }
    public void setLastname(String lastname){
         this.lastname=lastname;
    }
    public void setPseudo(String pseudo){
         this.pseudo=pseudo;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public void setPwd(String pwd){
        this.pwd=pwd;
    }
    public void setLanguage(String language){
        this.language=language;
    }
}
