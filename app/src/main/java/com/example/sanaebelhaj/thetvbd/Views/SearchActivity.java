package com.example.sanaebelhaj.thetvbd.Views;

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

import com.example.sanaebelhaj.thetvbd.R;


public class SearchActivity extends AppCompatActivity {

    private final static int ACTIVITY_CALL_ID = 1001;

    private EditText searchText;
    private Button btnSearch;
    private TextView textViewLog;

    private String searchedValue;
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchText  = findViewById(R.id.searchText);
        btnSearch   = findViewById(R.id.btnSearch);
        textViewLog = findViewById(R.id.textViewLog);

        //btnSearch.setOnClickListener(btnListenerSearch);

        //init();
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
            Log.i("DEBUG","Bouton cliqué");

            String valueSearch = searchText.getText().toString();

            Log.i("SEARCH_TEXT",valueSearch);

            if(valueSearch.equals("")) return;

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
