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
import android.widget.Button;
import android.widget.EditText;
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
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class SearchActivity extends AppCompatActivity {

    private final String THETVDB_URL_API = "https://api.thetvdb.com";

    private EditText searchText;
    private Button btnSearch;
    private ListView searchResult;
    private Session session;
    private String searchedValue;
    private ArrayList list = new ArrayList<String>();
    private String result;

    public static String NameSerie;

    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(THETVDB_URL_API)
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();
    TheTVDBClient userClient = retrofit.create(TheTVDBClient.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        session = new Session(getApplicationContext());
        Log.i("BUILD", session.getToken());
        searchText  = findViewById(R.id.searchText);
        btnSearch   = findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(btnListenerSearch);

        //init();
    }

    private void searchSeries(){

        Call<ResponseBody> call = userClient.search("Bearer "+session.getToken(),searchText.getText().toString());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.isSuccessful()){

                    try {
                        String string = response.body().string();

                        try{

                            JSONArray data = new JSONObject(string).getJSONArray("data");

                            if (data != null){
                                for (int i=0;i<data.length();i++) {
                                    JSONObject series = data.getJSONObject(i);
                                    String seriesName = series.getString("seriesName");
                                    String IDSerie = series.getString("id");
                                    list.add(seriesName);

                                    searchResult = findViewById(R.id.searchResult);
                                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(SearchActivity.this,android.R.layout.simple_list_item_1,list);
                                    searchResult.setAdapter(adapter);
                                    searchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            Intent intent = new Intent(SearchActivity.this, SerieActivity.class);
                                            intent.putExtra(NameSerie, list.get(position).toString());
                                            startActivity(intent);
                                        }
                                    });
                                }
                            }

                        }catch (JSONException e){

                            e.printStackTrace();

                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(SearchActivity.this,"Response OK",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(SearchActivity.this,"error HTTP code " + response.code(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(SearchActivity.this,"error :(",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    private View.OnClickListener btnListenerSearch = new View.OnClickListener(){
        @Override
        public void onClick(View v){

            String valueSearch = searchText.getText().toString();
            Log.i("SEARCH_TEXT",valueSearch);
            if(valueSearch.equals("")) return;

            searchSeries();
        }
    };

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent;
        switch (item.getItemId()){
            case R.id.menu_last_series:
                intent = new Intent(SearchActivity.this, UpdatedSeriesActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_search:
                intent = new Intent(SearchActivity.this, SearchActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_account:
                intent = new Intent(SearchActivity.this, AccountActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_logout:
                intent = new Intent(SearchActivity.this, LoginActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
