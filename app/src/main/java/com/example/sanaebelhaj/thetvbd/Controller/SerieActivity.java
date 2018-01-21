package com.example.sanaebelhaj.thetvbd.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanaebelhaj.thetvbd.R;
import com.example.sanaebelhaj.thetvbd.Services.Session;
import com.example.sanaebelhaj.thetvbd.Services.TheTVDBClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SerieActivity extends AppCompatActivity {
    private final String THETVDB_URL_API = "https://api.thetvdb.com";
    private String id;
    private Session session;
    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(THETVDB_URL_API)
            .addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit = builder.build();
    TheTVDBClient userClient = retrofit.create(TheTVDBClient.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serie);
        session = new Session(getApplicationContext());
        Log.i("BUILD", session.getToken());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getString(SeriesActivity.IDSerie);
            getSerieInfos();
            getFavoriteSerie();
        }
    }

    public void getSerieInfos(){
        Call<ResponseBody> call = userClient.getInfos("Bearer "+session.getToken(), id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.isSuccessful()){
                    try {
                        String string = response.body().string();
                        Log.i("BODY", string);
                        try {
                            JSONObject data = new JSONObject(string).getJSONObject("data");

                            TextView name = (TextView) findViewById(R.id.nameTVDB);
                            name.setText(data.getString("seriesName"));

                            TextView mark = (TextView) findViewById(R.id.markTVDBText);
                            mark.setText(data.getString("siteRating")+"/10");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(SerieActivity.this,"Response OK",Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(SerieActivity.this,"error HTTP code " + response.code(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(SerieActivity.this,"error :(",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getFavoriteSerie(){
        Call<ResponseBody> call = userClient.getFavorites("Bearer "+session.getToken());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        String string = response.body().string();
                        Log.i("BODY", string);
                        try {
                            JSONObject data = new JSONObject(string).getJSONObject("data");
                            JSONArray jsonArray = data.getJSONArray("favorites");

                            final ArrayList<String> list = new ArrayList<String>();
                            if (jsonArray != null) {
                                int len = jsonArray.length();
                                for (int i=0;i<len;i++)
                                    list.add(jsonArray.get(i).toString());

                                if(Arrays.asList(list).contains(id)){
                                    //remove
                                }
                                else{
                                    //put
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(SerieActivity.this,"Response OK",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(SerieActivity.this,"error HTTP code " + response.code(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(SerieActivity.this,"error :(",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void putFavoriteSerie(View v){
        Call<ResponseBody> call = userClient.addFavorite("Bearer "+session.getToken(), id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.isSuccessful()){
                    Toast.makeText(SerieActivity.this,"Done",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(SerieActivity.this,"error HTTP code " + response.code(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(SerieActivity.this,"error :(",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void removeFavoriteSerie(View v){
        Call<ResponseBody> call = userClient.rmvFavorite("Bearer "+session.getToken(), id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.isSuccessful()){
                    Toast.makeText(SerieActivity.this,"Done",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(SerieActivity.this,"error HTTP code " + response.code(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(SerieActivity.this,"error :(",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent;
        switch (item.getItemId()){
            case R.id.menu_last_series:
                intent = new Intent(SerieActivity.this, SeriesActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_search:
                intent = new Intent(SerieActivity.this, SearchActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_account:
                intent = new Intent(SerieActivity.this, AccountActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_logout:
                intent = new Intent(SerieActivity.this, LoginActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
