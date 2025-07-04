package com.example.bengkelkuapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class LandingActivity extends AppCompatActivity {

    AppCompatButton btnLogin, btnSignUp;
    private boolean isFirstLaunch = true; // flag untuk mencegah splash terus muncul

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Cek apakah ini adalah pertama kali dibuka setelah launcher
        if (isFirstLaunch) {
            isFirstLaunch = false;

            // Tampilkan splash layout dari SplashActivity dulu
            new Handler().postDelayed(() -> {
                // Setelah 2 detik, lanjut ke tampilan landing
                loadLandingScreen();
            }, 2000);
        } else {
            loadLandingScreen(); // Jika bukan pertama kali, langsung tampilkan landing
        }
    }

    private void loadLandingScreen() {
        setContentView(R.layout.activity_landing);

        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}
