package com.example.sanaebelhaj.thetvbd.Controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sanaebelhaj.thetvbd.R;
import com.example.sanaebelhaj.thetvbd.Services.Helper;
import com.example.sanaebelhaj.thetvbd.Services.Session;
import com.example.sanaebelhaj.thetvbd.Services.TheTVDBClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SerieActivity extends AppCompatActivity {
    private final String THETVDB_URL_API = "https://api.thetvdb.com";
    private String id;
    private String name;
    private Session session;
    private ListView listView;
    private Button buttonAdd;
    private Button buttonRmv;
    private ImageView bannerImage;
    private Bitmap bitmap;
    Boolean favorite;
    private final ArrayList<String> actors = new ArrayList<String>();
    private final ArrayList<String> chapters = new ArrayList<String>();
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
        buttonAdd = (Button)findViewById(R.id.favoriteAdd);
        buttonRmv = (Button)findViewById(R.id.favoriteRmv);
        buttonAdd.setVisibility(View.GONE);
        buttonRmv.setVisibility(View.GONE);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name = extras.getString(UpdatedSeriesActivity.NameSerie);
            searchSerie(name);
        }
    }

    public void searchSerie(String name){
        Call<ResponseBody> call = userClient.searchSerie("Bearer "+session.getToken(), name);
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
                                    id = serie.getString("id");
                                    break;
                                }
                                getSerieInfos();
                                getUserMark();
                                getFavoriteSerie();
                                getChapters();
                                getActors();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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

    public void getSerieInfos(){
        Call<ResponseBody> call = userClient.getInfos("Bearer "+session.getToken(), id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.isSuccessful()){
                    try {
                        String string = response.body().string();
                        try {
                            JSONObject data = new JSONObject(string).getJSONObject("data");

                            String bannerImageURL = "https://www.thetvdb.com/banners/"+data.getString("banner");

                            new DownloadImageTask((ImageView) findViewById(R.id.bannerImage))
                                    .execute(bannerImageURL);

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

    public void getChapters(){
        Call<ResponseBody> call = userClient.getChapters("Bearer "+session.getToken(), id);
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
                                    String name = serie.getString("episodeName");
                                    String order = serie.getString("airedEpisodeNumber");
                                    String season = serie.getString("airedSeason");
                                    if(!name.equals("null"))
                                        chapters.add(name+" ("+season+" - "+order+")");
                                }
                                listView = findViewById(R.id.list_chapters);
                                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(SerieActivity.this, android.R.layout.simple_list_item_1, chapters);
                                listView.setAdapter(adapter);
                                Helper.getListViewSize(listView);
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
                else
                    Toast.makeText(SerieActivity.this,"error HTTP code " + response.code(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(SerieActivity.this,"error :(",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getActors(){
        Call<ResponseBody> call = userClient.getActors("Bearer "+session.getToken(), id);
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
                                    String name = serie.getString("name");
                                    String role = serie.getString("role");
                                    actors.add(name+" ("+role+")");
                                }
                                listView = findViewById(R.id.list_actors);
                                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(SerieActivity.this, android.R.layout.simple_list_item_1, actors);
                                listView.setAdapter(adapter);
                                Helper.getListViewSize(listView);
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                                {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        //TODO : webpage de l'acteur
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
                else
                    Toast.makeText(SerieActivity.this,"error HTTP code " + response.code(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(SerieActivity.this,"error :(",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getUserMark(){
        Call<ResponseBody> call = userClient.getRatings("Bearer "+session.getToken());
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
                                    String idSerie = serie.getString("ratingItemId");
                                    if(idSerie.equals(id)) {
                                        String mark = serie.getString("rating");
                                        EditText editText = (EditText)findViewById(R.id.markUserText);
                                        editText.setText(mark, TextView.BufferType.EDITABLE);
                                    }
                                }
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
                else
                    Toast.makeText(SerieActivity.this,"error HTTP code " + response.code(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(SerieActivity.this,"error :(",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void sendMark(View v){
        EditText markText = (EditText) findViewById(R.id.markUserText);
        Integer mark = Integer.parseInt(markText.getText().toString());
        if(mark != null && mark >= 0 && mark <= 10){
            Call<ResponseBody> call = userClient.setRatings("Bearer "+session.getToken(), "series", id, mark.toString());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful()){
                        Intent intent = getIntent().addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        finish();
                        startActivity(intent);
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
        else
            Toast.makeText(SerieActivity.this,"Incorrect value",Toast.LENGTH_LONG).show();
    }

    public void getFavoriteSerie(){
        Call<ResponseBody> call = userClient.getFavorites("Bearer "+session.getToken());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        String string = response.body().string();
                        try {
                            JSONObject data = new JSONObject(string).getJSONObject("data");
                            JSONArray jsonArray = data.getJSONArray("favorites");

                            if (jsonArray != null) {
                                int len = jsonArray.length();
                                favorite = false;
                                for (int i=0;i<len;i++) {
                                    if(id.equals(jsonArray.get(i).toString())) {
                                        favorite = true;
                                        break;
                                    }
                                }

                                if(favorite)
                                    buttonRmv.setVisibility(View.VISIBLE);
                                else
                                    buttonAdd.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
                    Intent intent = getIntent().addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    finish();
                    startActivity(intent);
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

    public void removeFavoriteSerie(View v){
        Call<ResponseBody> call = userClient.rmvFavorite("Bearer "+session.getToken(), id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if(response.isSuccessful()){
                    Intent intent = getIntent().addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    finish();
                    startActivity(intent);
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

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent;
        switch (item.getItemId()){
            case R.id.menu_last_series:
                intent = new Intent(SerieActivity.this, UpdatedSeriesActivity.class);
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

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
