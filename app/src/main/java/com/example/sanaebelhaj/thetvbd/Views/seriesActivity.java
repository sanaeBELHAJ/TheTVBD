package com.example.sanaebelhaj.thetvbd.Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sanaebelhaj.thetvbd.Services.TheTVDBClient;
import com.example.sanaebelhaj.thetvbd.Models.TheTVDBRepo;
import com.example.sanaebelhaj.thetvbd.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class seriesActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series);

        listView = (ListView) findViewById(R.id.list_series);

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.thetvdb.com/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        TheTVDBClient client =  retrofit.create(TheTVDBClient.class);

        //Daredevil ID
        Call<List<TheTVDBRepo>> call = client.repoSeries("281662");

        call.enqueue(new Callback<List<TheTVDBRepo>>() {
            @Override
            public void onResponse(Call<List<TheTVDBRepo>> call, Response<List<TheTVDBRepo>> response) {
                List<TheTVDBRepo> repos = response.body();
                listView.setAdapter(new TheTVDBRepoAdapter(seriesActivity.this,repos));
            }

            @Override
            public void onFailure(Call<List<TheTVDBRepo>> call, Throwable t) {
                Toast.makeText(seriesActivity.this, "error :(",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
