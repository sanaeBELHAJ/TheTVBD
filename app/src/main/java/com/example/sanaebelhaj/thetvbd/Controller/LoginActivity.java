package com.example.sanaebelhaj.thetvbd.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sanaebelhaj.thetvbd.Models.TheTVDBLogin;
import com.example.sanaebelhaj.thetvbd.Models.TheTVDBToken;
import com.example.sanaebelhaj.thetvbd.R;
import com.example.sanaebelhaj.thetvbd.Repository.SeriesDB;

import com.example.sanaebelhaj.thetvbd.Services.Session;

import com.example.sanaebelhaj.thetvbd.Services.TheTVDBClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity{

    private EditText apikey;
    private EditText userkey;
    private EditText username;
    private Button login;
    private Session session;//global variable
    private final String THETVDB_URL_API = "https://api.thetvdb.com";

    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(THETVDB_URL_API)
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();
    TheTVDBClient userClient = retrofit.create(TheTVDBClient.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        session = new Session(getApplicationContext());
        //Put the token value on NULL when the user logout
        session.setToken("");
        login = findViewById(R.id.login);
        login.setOnClickListener(btnListenerLogin);
    }

    private static String token;


    private View.OnClickListener btnListenerLogin = new View.OnClickListener(){
        @Override
        public void onClick(View v){
        apikey = (EditText) findViewById(R.id.apikeyInput);
        userkey = (EditText) findViewById(R.id.userkeyInput);
        username = (EditText) findViewById(R.id.usernameInput);

        TheTVDBLogin login = new TheTVDBLogin(apikey.getText().toString(),userkey.getText().toString(),username.getText().toString());
        Call<TheTVDBToken> call = userClient.login(login);

        call.enqueue(new Callback<TheTVDBToken>() {
            @Override
            public void onResponse(Call<TheTVDBToken> call, Response<TheTVDBToken> response) {
                if(response.isSuccessful()){
                    token = response.body().getToken();
                    Log.i("LOGIN","Token => " + response.body().getToken());
                    session.setToken(token);
                    Intent series = new Intent(LoginActivity.this, UpdatedSeriesActivity.class);
                    startActivity(series);
                }
                else{
                    Toast.makeText(LoginActivity.this,"Error :(",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TheTVDBToken> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"error :(",Toast.LENGTH_SHORT).show();
            }
        });
        }
    };
}
