package com.example.freshmarket;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class loginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void iniciarSesion(View view){

         Intent intent;
         //Usuario
         //intent= new Intent(this, principalActivity.class);

         //Admin
         intent= new Intent(this, activityPrincipalAdmin.class);


        startActivity(intent);

        this.finish();
    }
    public void registrarse(View view){
        Intent intent = new Intent(this, resgistrarseActivity.class);
        startActivity(intent);
        this.finish();

    }

}