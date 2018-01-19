package com.example.sanaebelhaj.thetvbd.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;

import com.example.sanaebelhaj.thetvbd.R;

public class LoginActivity extends AppCompatActivity{

    private EditText pseudoInput;
    private EditText passwordInput;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pseudoInput = findViewById(R.id.pseudoInput);
        passwordInput = findViewById(R.id.passwordInput);

        login = findViewById(R.id.login);

        login.setOnClickListener(btnListenerLogin);
    }

    private View.OnClickListener btnListenerLogin = new View.OnClickListener(){
        @Override
        public void onClick(View v){
            Log.i("DEBUG","Bouton cliqué");
            Intent series = new Intent(v.getContext(), SeriesActivity.class);
            startActivity(series);
        }
    };
}
