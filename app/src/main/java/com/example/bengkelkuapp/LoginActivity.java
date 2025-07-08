package com.example.bengkelkuapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bengkelkuapp.model.User;

import io.realm.Realm;

public class LoginActivity extends AppCompatActivity {

    ImageView btnBack;
    Button btnLogin;
    EditText etEmail, etPassword;
    TextView tvRegister;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getSupportActionBar() != null) getSupportActionBar().hide();

        realm = Realm.getDefaultInstance();

        btnBack = findViewById(R.id.btnBack);
        btnLogin = findViewById(R.id.btnLogin);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        tvRegister = findViewById(R.id.tvRegister);

        btnBack.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, LandingActivity.class));
            finish();
        });

        btnLogin.setOnClickListener(view -> loginUser());

        tvRegister.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            finish();
        });

        Log.d("DEBUG", "Total user: " + realm.where(User.class).findAll().size());

    }

    private void loginUser() {
        String email = etEmail.getText().toString().trim().toLowerCase();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty()) {
            etEmail.setError("Email wajib diisi");
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Format email tidak valid");
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError("Password wajib diisi");
            return;
        }

        Log.d("DEBUG", "Mau login email: " + email);
        for (User u : realm.where(User.class).findAll()) {
            Log.d("DEBUG", "User di database: " + u.getEmail() + ", pass: " + u.getPassword());
        }


        User user = realm.where(User.class).equalTo("email", email).findFirst();

        if (user == null) {
            Toast.makeText(this, "Akun tidak ditemukan", Toast.LENGTH_SHORT).show();
        } else if (!user.getPassword().equals(password)) {
            Toast.makeText(this, "Password salah", Toast.LENGTH_SHORT).show();
        } else {
            SharedPreferences prefs = getSharedPreferences("userSession", MODE_PRIVATE);
            prefs.edit().putString("email", email).apply();

            Toast.makeText(this, "Login berhasil", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null && !realm.isClosed()) {
            realm.close();
        }
    }
}
