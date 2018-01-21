package com.example.sanaebelhaj.thetvbd.Controller;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanaebelhaj.thetvbd.Models.TheTVDBUser;
import com.example.sanaebelhaj.thetvbd.R;
import com.example.sanaebelhaj.thetvbd.Services.Session;
import com.example.sanaebelhaj.thetvbd.Services.TheTVDBClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AccountActivity extends AppCompatActivity {
    private static ResponseBody infos;
    private final String THETVDB_URL_API = "https://api.thetvdb.com";
    private Session session;

    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(THETVDB_URL_API)
            .addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit = builder.build();
    TheTVDBClient userClient = retrofit.create(TheTVDBClient.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        session = new Session(getApplicationContext());
        getUserInfos();

        //List languages
        Spinner dropdown = findViewById(R.id.list_languages);
        String[] items = new String[]{"English", "Français"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
    }

    public void getUserInfos(){
        Call<ResponseBody> call = userClient.getUserInfo("Bearer "+session.getToken());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.isSuccessful()){
                    try {
                        String string = response.body().string();
                        Log.i("BODY", string);
                        try {
                            JSONObject data = new JSONObject(string).getJSONObject("data");
                            TextView pseudo = (TextView) findViewById(R.id.pseudoInput);
                            pseudo.setText(data.getString("userName"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(AccountActivity.this,"Response OK",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(AccountActivity.this,"error HTTP code " + response.code(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AccountActivity.this,"error :(",Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Change language
    public void changeLanguage(View v){
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
                intent = new Intent(AccountActivity.this, UpdatedSeriesActivity.class);
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
