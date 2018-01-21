package com.example.sanaebelhaj.thetvbd.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanaebelhaj.thetvbd.R;
import com.example.sanaebelhaj.thetvbd.Services.TheTVDBClient;

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
    private TextView textViewLog;

    private String searchedValue;
    private String result;

    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(THETVDB_URL_API)
            .addConverterFactory(GsonConverterFactory.create());

    Retrofit retrofit = builder.build();
    TheTVDBClient userClient = retrofit.create(TheTVDBClient.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchText  = findViewById(R.id.searchText);
        btnSearch   = findViewById(R.id.btnSearch);
        textViewLog = findViewById(R.id.textViewLog);

        btnSearch.setOnClickListener(btnListenerSearch);

        //init();
    }

    private void searchSeries(){

        Call<ResponseBody> call = userClient.search("Bearer eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1MTY0ODg5MjEsImlkIjoiRVNHSV9BbmRyb2lkX3Byb2plY3QiLCJvcmlnX2lhdCI6MTUxNjQwMjUyMSwidXNlcmlkIjo0OTY2MzYsInVzZXJuYW1lIjoiU2FiZXJ0b290aDI4In0.tdI6Z8BDjyHRxUWQBsC9Q2G1yaFloVHjXMuKSEYQwuWtcTCVbsSTy6s_JAtR8q1QwffiXT5bs1WEfwMS8i3WAIPZJj_kSaqvB_trmxaa8aZ3dSj7rdTTDTqI5E_e-6QhOojMrAkzKmDp4UocyvsrpOccIJBpJo9NzY_xJj490LsPxYI-tAIVr366yCkni2HAobPKt7119aXwtkhRc2RmshdVcIQO8lYq3y3QumM2OwRKO2JLQ0G3jwQMwqjLMS_QS0ETE40x-UtCmrgLCFYmkLP_ubTsshMb7Ruz4cxLFsVIBFru2qOG9xkbOHqBQvjD36vCyDo6-XbX76qVU17bgA",searchText.getText().toString());

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.isSuccessful()){
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

    private void init(){
        searchedValue = null;

        searchText.setText("");
        textViewLog.setText("");
        searchText.requestFocus();
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
                intent = new Intent(SearchActivity.this, SeriesActivity.class);
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
