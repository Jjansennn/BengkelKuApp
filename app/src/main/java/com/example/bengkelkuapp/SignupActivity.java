package com.example.bengkelkuapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class SignupActivity extends AppCompatActivity {

    ImageView btnBack;
    AppCompatButton btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide(); // Hilangkan ActionBar
        }

        btnBack = findViewById(R.id.btnBack);
        btnSignup = findViewById(R.id.btnSignup);

        // Tombol Back ke Landing
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, LandingActivity.class);
            startActivity(intent);
            finish(); // Opsional: tutup SignupActivity
        });

        // Tombol Sign Up ke Login
        btnSignup.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // Opsional: tutup SignupActivity
        });

        RadioGroup rgVerification = findViewById(R.id.rgVerification);

        rgVerification.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbEmail || checkedId == R.id.rbSms) {
                Intent intent = new Intent(SignupActivity.this, VerificationActivity.class);
                startActivity(intent);
            }
        });
    }
}
