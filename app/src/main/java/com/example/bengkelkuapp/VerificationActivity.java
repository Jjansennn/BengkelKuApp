package com.example.bengkelkuapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class VerificationActivity extends AppCompatActivity {
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_verification);

        btnBack = findViewById(R.id.btnBack);

        AppCompatButton btnVerify = findViewById(R.id.btnVerify);
        btnVerify.setOnClickListener(v -> {
            Intent intent = new Intent(VerificationActivity.this, SignupActivity.class);
            // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Optional, jika ingin menghapus back stack
            startActivity(intent);
            finish(); // Tutup activity verification agar tidak bisa di-back
        });

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(VerificationActivity.this, SignupActivity.class);
            startActivity(intent);
            finish(); // Opsional: tutup SignupActivity
        });


    }
}