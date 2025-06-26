package com.example.bengkelkuapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 2000; // 2 detik

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hilangkan ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Set layout splash screen
        setContentView(R.layout.activity_splash);

        // Delay ke LandingActivity
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, LandingActivity.class);
            startActivity(intent);
            finish(); // Supaya tidak bisa kembali ke splash
        }, SPLASH_DELAY);
    }
}
