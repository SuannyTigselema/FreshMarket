package com.example.freshmarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //icono en actionBar
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
       // getSupportActionBar().setIcon(R.mipmap.ic_launcher);

    }
    public void registrarse(View view){
            Intent intent = new Intent(this, resgistrarseActivity.class);
            startActivity(intent);
            this.finish();

    }

    public void iniciarSesion(View view){
        Intent intent = new Intent(this, loginActivity.class);
        startActivity(intent);
        this.finish();
    }
}