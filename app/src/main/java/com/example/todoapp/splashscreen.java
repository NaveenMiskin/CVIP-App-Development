package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;


public class splashscreen extends AppCompatActivity {
    View mContentView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        mContentView = findViewById(R.id.logoid);
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
         | View.SYSTEM_UI_FLAG_FULLSCREEN
         | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
         | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
         | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
         | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);


        final Intent i = new Intent(splashscreen.this,MainActivity.class);
        new Handler().postDelayed(() -> {
            startActivity(i);
            finish();
        },3000);
    }
}