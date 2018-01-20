package com.example.sanaebelhaj.thetvbd.Views;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sanaebelhaj.thetvbd.Models.TheTVDBLogin;
import com.example.sanaebelhaj.thetvbd.Models.TheTVDBToken;
import com.example.sanaebelhaj.thetvbd.R;
import com.example.sanaebelhaj.thetvbd.Services.TheTVDBClient;

import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AccountActivity extends AppCompatActivity {
    private static String token;
    private static ResponseBody infos;
    private final String THETVDB_URL_API = "https://api.thetvdb.com";

    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(THETVDB_URL_API)
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();
    TheTVDBClient userClient = retrofit.create(TheTVDBClient.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        TheTVDBLogin login = new TheTVDBLogin("398D024B39FD5AEE","7367A4BB383FE4A0","Sabertooth28");
        Call<TheTVDBToken> callLogin = userClient.login(login);
        getToken(callLogin);

        //List languages
        Spinner dropdown = findViewById(R.id.list_languages);
        String[] items = new String[]{"English", "Français"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
    }


    public void getToken(Call<TheTVDBToken> call){
        call.enqueue(new Callback<TheTVDBToken>() {
            @Override
            public void onResponse(Call<TheTVDBToken> call, Response<TheTVDBToken> response) {
                if(response.isSuccessful()){
                    token = response.body().getToken();
                    Log.i("LOGIN","Token => " + token);

                    Call<ResponseBody> callUser = userClient.getUserInfo(token);
                    getUserInfos(callUser);
                }
                else{
                    Toast.makeText(AccountActivity.this,"error :(",Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<TheTVDBToken> call, Throwable t) {
                Toast.makeText(AccountActivity.this,"error :(",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getUserInfos(Call<ResponseBody> call){
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    infos = response.body();
                    Log.i("LOGIN","Infos de l'utilisateur => " + infos);
                }
                else{
                    Toast.makeText(AccountActivity.this,"error :(",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AccountActivity.this,"error :(",Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Change language
    public void sendFeedback(View v){
        final Spinner feedbackSpinner = (Spinner) findViewById(R.id.list_languages);
        String language = feedbackSpinner.getSelectedItem().toString();

        switch(language){
            case "Français":
                Locale localeFR = new Locale("fr");
                Locale.setDefault(localeFR);
                Configuration configFR = new Configuration();
                configFR.locale = localeFR;
                getBaseContext().getResources().updateConfiguration(configFR, getBaseContext().getResources().getDisplayMetrics());
                Toast.makeText(this, "Locale en Francais !", Toast.LENGTH_LONG).show();
                break;
            default:
                Locale localeEN = new Locale("en");
                Locale.setDefault(localeEN);
                Configuration configEN = new Configuration();
                configEN.locale = localeEN;
                getBaseContext().getResources().updateConfiguration(configEN, getBaseContext().getResources().getDisplayMetrics());
                Toast.makeText(this, "Locale in English !", Toast.LENGTH_LONG).show();
                break;
        }
        Intent intent = getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        startActivity(intent);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent;
        switch (item.getItemId()){
            case R.id.menu_last_series:
                intent = new Intent(AccountActivity.this, SeriesActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_search:
                intent = new Intent(AccountActivity.this, SearchActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_account:
                intent = new Intent(AccountActivity.this, AccountActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_logout:
                intent = new Intent(AccountActivity.this, LoginActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
