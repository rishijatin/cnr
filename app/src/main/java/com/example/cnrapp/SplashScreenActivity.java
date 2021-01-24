package com.example.cnrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import java.util.Calendar;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_OUT_TIME= 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();

        new Handler(getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, CategoryActivity.class));
                finish();
            }
        },SPLASH_OUT_TIME);


    }
}