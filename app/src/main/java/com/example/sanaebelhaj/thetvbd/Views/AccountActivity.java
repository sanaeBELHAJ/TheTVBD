package com.example.sanaebelhaj.thetvbd.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.sanaebelhaj.thetvbd.R;


public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        Spinner dropdown = findViewById(R.id.list_languages);
        String[] items = new String[]{"English", "Français"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent;
        switch (item.getItemId()){
            case R.id.menu_last_series:
                intent = new Intent(AccountActivity.this, SeriesActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_search:
                intent = new Intent(AccountActivity.this, SearchActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_account:
                intent = new Intent(AccountActivity.this, AccountActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_logout:
                intent = new Intent(AccountActivity.this, LoginActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
