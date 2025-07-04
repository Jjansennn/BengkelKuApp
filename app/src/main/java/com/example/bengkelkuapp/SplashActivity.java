package com.example.bengkelkuapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(() -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }, 10);

        startActivity(new Intent(this, LandingActivity.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out); // 👈 transisi halus
        finish();

    }
}

