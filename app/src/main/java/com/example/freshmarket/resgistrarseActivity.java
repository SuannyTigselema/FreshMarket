package com.example.freshmarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class resgistrarseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resgistrarse);
    }

    public void IniciarSesion(View view){
        Intent intent = new Intent(this, loginActivity.class);
        startActivity(intent);
        this.finish();
    }
}