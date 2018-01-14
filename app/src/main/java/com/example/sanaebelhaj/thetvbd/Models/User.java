package com.example.sanaebelhaj.thetvbd.Models;

/**
 * Created by Sanae BELHAJ on 13/10/2017.
 */

public class User {
    public String firstname;
    public String lastname;
    public String email;
    public String pwd;

    public User(String firstname, String prenom, String email, String pwd) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.pwd = pwd;
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
    public String getPwd(){
        return pwd;
    }
    public void setFirstname(String firstname){
        this.firstname = firstname;
    }
    public void setLastname(String lastname){
         this.lastname=lastname;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public void setPwd(String pwd){
        this.pwd=pwd;
    }
}
