package com.example.sanaebelhaj.thetvbd.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.sanaebelhaj.thetvbd.R;
import com.example.sanaebelhaj.thetvbd.Services.Session;

public class SerieActivity extends AppCompatActivity {

    private Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serie);
        session = new Session(getApplicationContext());
        Log.i("BUILD", session.getToken());

        //Daredevil ID
        //Call<List<TheTVDBLogin>> call = client.repoSeries("281662");

        /*call.enqueue(new Callback<List<TheTVDBLogin>>() {
        @Override
            public void onResponse(Call<List<TheTVDBLogin>> call, Response<List<TheTVDBLogin>> response) {
                List<TheTVDBLogin> repos = response.body();
                listView.setAdapter((ListAdapter) new TheTVDBRepoAdapter(SeriesActivity.this,repos));
            }

            @Override
            public void onFailure(Call<List<TheTVDBLogin>> call, Throwable t) {
                Toast.makeText(SeriesActivity.this, "error :(",Toast.LENGTH_SHORT).show();
            }
        });*/
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
