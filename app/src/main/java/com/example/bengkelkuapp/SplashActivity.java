package com.example.bengkelkuapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Tampilkan layout splash
        setContentView(R.layout.activity_splash);

        // Delay 2 detik lalu pindah ke LandingActivity
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashActivity.this, LandingActivity.class));
            finish(); // Supaya tidak bisa balik ke splash pakai tombol back
        }, 2000); // 2000 ms = 2 detik
    }
}
