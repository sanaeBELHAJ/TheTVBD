package com.example.sanaebelhaj.thetvbd.Views;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sanaebelhaj.thetvbd.R;

import java.util.Locale;


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

    public void sendFeedback(View v){
        final Spinner feedbackSpinner = (Spinner) findViewById(R.id.list_languages);
        String language = feedbackSpinner.getSelectedItem().toString();

        switch(language){
            case "Français":
                Locale localeFR = new Locale("fr");
                Locale.setDefault(localeFR);
                Configuration configFR = new Configuration();
                configFR.locale = localeFR;
                getBaseContext().getResources().updateConfiguration(configFR, getBaseContext().getResources().getDisplayMetrics());
                Toast.makeText(this, "Locale en Francais !", Toast.LENGTH_LONG).show();
                break;
            default:
                Locale localeEN = new Locale("en");
                Locale.setDefault(localeEN);
                Configuration configEN = new Configuration();
                configEN.locale = localeEN;
                getBaseContext().getResources().updateConfiguration(configEN, getBaseContext().getResources().getDisplayMetrics());
                Toast.makeText(this, "Locale in English !", Toast.LENGTH_LONG).show();
                break;
        }
        Intent intent = getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        startActivity(intent);
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
