package com.example.sanaebelhaj.thetvbd.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sanaebelhaj.thetvbd.Services.TheTVDBClient;
import com.example.sanaebelhaj.thetvbd.Models.TheTVDBRepo;
import com.example.sanaebelhaj.thetvbd.R;
import com.example.sanaebelhaj.thetvbd.Views.Adapter.TheTVDBRepoAdapter;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SerieActivity extends AppCompatActivity {

    private ListView listView;

    private String[] prenoms = new String[]{
            "Antoine", "Benoit", "Cyril", "David", "Eloise", "Florent",
            "Gerard", "Hugo", "Ingrid", "Jonathan", "Kevin", "Logan",
            "Mathieu", "Noemie", "Olivia", "Philippe", "Quentin", "Romain",
            "Yann", "Zoé"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series);


        /*Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.thetvdb.com/")
                .addConverterFactory(GsonConverterFactory.create());
        Retrofit retrofit = builder.build();

        TheTVDBClient client =  retrofit.create(TheTVDBClient.class);
        */

        //Daredevil ID
        //Call<List<TheTVDBRepo>> call = client.repoSeries("281662");

        /*call.enqueue(new Callback<List<TheTVDBRepo>>() {
        @Override
            public void onResponse(Call<List<TheTVDBRepo>> call, Response<List<TheTVDBRepo>> response) {
                List<TheTVDBRepo> repos = response.body();
                listView.setAdapter((ListAdapter) new TheTVDBRepoAdapter(SeriesActivity.this,repos));
            }

            @Override
            public void onFailure(Call<List<TheTVDBRepo>> call, Throwable t) {
                Toast.makeText(SeriesActivity.this, "error :(",Toast.LENGTH_SHORT).show();
            }
        });
        */
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
