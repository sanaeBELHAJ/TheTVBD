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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanaebelhaj.thetvbd.R;
import com.example.sanaebelhaj.thetvbd.Services.Session;
import com.example.sanaebelhaj.thetvbd.Services.TheTVDBClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdatedSeriesActivity extends AppCompatActivity {
    private final String THETVDB_URL_API = "https://api.thetvdb.com";
    private ListView listView;
    private final ArrayList<String> list = new ArrayList<String>();
    private Session session;
    public static String IDSerie;

    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(THETVDB_URL_API)
            .addConverterFactory(GsonConverterFactory.create());
    Retrofit retrofit = builder.build();
    TheTVDBClient userClient = retrofit.create(TheTVDBClient.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series);
        session = new Session(getApplicationContext());
        getSeries();
    }

    public void getSeries(){
        //1514805943
        Call<ResponseBody> call = userClient.getUpdated("Bearer "+session.getToken(), "1516532449");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.isSuccessful()){
                    try {
                        String string = response.body().string();
                        try {
                            JSONArray data = new JSONObject(string).getJSONArray("data");

                            if (data != null) {
                                for (int i=0;i<data.length();i++) {
                                    JSONObject serie = data.getJSONObject(i);
                                    String idSerie = serie.getString("id");
                                    getInfos(idSerie);
                                }
                                listView = findViewById(R.id.list_series);
                                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(UpdatedSeriesActivity.this, android.R.layout.simple_list_item_1, list);
                                listView.setAdapter(adapter);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                                {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Intent intent = new Intent(UpdatedSeriesActivity.this, SerieActivity.class);
                                    intent.putExtra(IDSerie, list.get(position));
                                    startActivity(intent);
                                    }
                                });
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Toast.makeText(UpdatedSeriesActivity.this,"error HTTP code " + response.code(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(UpdatedSeriesActivity.this,"error :(",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getInfos(String id){
        Call<ResponseBody> call = userClient.getInfos("Bearer "+session.getToken(), id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.isSuccessful()){
                    try {
                        String string = response.body().string();
                        try {
                            JSONObject data = new JSONObject(string).getJSONObject("data");
                            String nom = data.getString("seriesName");
                            if(nom != null && !nom.equals("null")){
                                list.add(nom);
                                Log.i("Nom", nom);
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else
                    Toast.makeText(UpdatedSeriesActivity.this,"error HTTP code " + response.code(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(UpdatedSeriesActivity.this,"error :(",Toast.LENGTH_SHORT).show();
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
                intent = new Intent(UpdatedSeriesActivity.this, UpdatedSeriesActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_search:
                intent = new Intent(UpdatedSeriesActivity.this, SearchActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_account:
                intent = new Intent(UpdatedSeriesActivity.this, AccountActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_logout:
                intent = new Intent(UpdatedSeriesActivity.this, LoginActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
