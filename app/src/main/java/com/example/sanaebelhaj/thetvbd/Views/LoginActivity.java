package com.example.sanaebelhaj.thetvbd.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.widget.Toast;

import com.example.sanaebelhaj.thetvbd.Models.TheTVDBLogin;
import com.example.sanaebelhaj.thetvbd.Models.TheTVDBToken;
import com.example.sanaebelhaj.thetvbd.R;
import com.example.sanaebelhaj.thetvbd.Services.TheTVDBClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity{

    private EditText pseudoInput;
    private EditText passwordInput;
    private Button login;

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

        pseudoInput = findViewById(R.id.pseudoInput);
        passwordInput = findViewById(R.id.passwordInput);

        login = findViewById(R.id.login);

        login.setOnClickListener(btnListenerLogin);
    }

    private static String token;

    private void login(){
        TheTVDBLogin login = new TheTVDBLogin("398D024B39FD5AEE","7367A4BB383FE4A0","Sabertooth28");
        Call<TheTVDBToken> call = userClient.login(login);

        call.enqueue(new Callback<TheTVDBToken>() {
            @Override
            public void onResponse(Call<TheTVDBToken> call, Response<TheTVDBToken> response) {
                if(response.isSuccessful()){
                    Toast.makeText(LoginActivity.this,response.body().getToken(),Toast.LENGTH_LONG).show();
                    token = response.body().getToken();
                    Log.i("LOGIN","Token => " + response.body().getToken());
                }
                else{
                    Toast.makeText(LoginActivity.this,"Token error :(",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TheTVDBToken> call, Throwable t) {
                Toast.makeText(LoginActivity.this,"error :(",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private View.OnClickListener btnListenerLogin = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            Log.i("DEBUG","Bouton cliqué");

            //TODO : Contrôle de login via API

            Intent series = new Intent(v.getContext(), SeriesActivity.class);
            //startActivity(series);
            login();
        }
    };
}
