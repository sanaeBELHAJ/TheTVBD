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
import android.widget.Toast;

import com.example.sanaebelhaj.thetvbd.R;

public class MainActivity extends AppCompatActivity {

    private final static int ACTIVITY_CALL_ID = 1001;

    private EditText searchText;
    private Button btnSearch;
    private TextView textViewLog;

    private String searchedValue;
    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        searchText  = (EditText) findViewById(R.id.searchText);
        btnSearch   = (Button) findViewById(R.id.btnSearch);
        textViewLog = (TextView) findViewById(R.id.textViewLog);

        btnSearch.setOnClickListener(btnListenerSearch);

        init();
    }

    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_home:
                Toast.makeText(this,"Home menu selected",Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_series:
                homeSeries();
                return true;
            case R.id.menu_settings:
                Toast.makeText(this,"Settings menu selected",Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_help:
                Toast.makeText(this,"Help menu selected",Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void homeSeries(){
        Intent intent = new Intent(
                MainActivity.this,
                seriesActivity.class
        );
        startActivity(intent);
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


}
