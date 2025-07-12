package com.example.bengkelkuapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.bengkelkuapp.model.User;

import io.realm.Realm;

public class SignupActivity extends AppCompatActivity {

    ImageView btnBack;
    AppCompatButton btnSignup;
    EditText etPhone, etEmail, etPassword;
    RadioGroup rgVerification;
    RadioButton rbEmail, rbSms;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        if (getSupportActionBar() != null) getSupportActionBar().hide();

        Realm.init(this);
        realm = Realm.getDefaultInstance();

        // Inisialisasi View
        btnBack = findViewById(R.id.btnBack);
        btnSignup = findViewById(R.id.btnSignup);
        etPhone = findViewById(R.id.etPhone);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        rgVerification = findViewById(R.id.rgVerification);
        rbEmail = findViewById(R.id.rbEmail);
        rbSms = findViewById(R.id.rbSms);

        btnBack.setOnClickListener(v -> {
            startActivity(new Intent(SignupActivity.this, LandingActivity.class));
            finish();
        });

        btnSignup.setOnClickListener(v -> registerUser());

        Log.d("DEBUG", "Total user: " + realm.where(User.class).findAll().size());
    }

    private void registerUser() {
        String phone = etPhone.getText().toString().trim();
        String email = etEmail.getText().toString().trim().toLowerCase();
        String password = etPassword.getText().toString().trim();

        if (phone.isEmpty()) {
            etPhone.setError("Nomor telepon wajib diisi");
            return;
        }

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

        if (password.length() < 6) {
            etPassword.setError("Password minimal 6 karakter");
            return;
        }

        if (rgVerification.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Pilih metode verifikasi (Email/SMS)", Toast.LENGTH_SHORT).show();
            return;
        }

        User existingUser = realm.where(User.class).equalTo("email", email).findFirst();
        if (existingUser != null) {
            Toast.makeText(this, "Email sudah terdaftar", Toast.LENGTH_SHORT).show();
            return;
        }

        // Ambil nama default dari email (sebelum @)
        String namaDefault = email.split("@")[0];

        // Simpan user baru ke Realm
        realm.executeTransaction(r -> {
            User user = r.createObject(User.class, email);
            user.setPhone(phone);
            user.setPassword(password);
            user.setNama(namaDefault); // simpan nama default
        });

        // Simpan session email ke SharedPreferences
        SharedPreferences prefs = getSharedPreferences("userSession", MODE_PRIVATE);
        prefs.edit().putString("email", email).apply();

        Toast.makeText(this, "Pendaftaran berhasil", Toast.LENGTH_SHORT).show();
        Log.d("DEBUG", "User tersimpan: " + email);
        Log.d("DEBUG", "Total user: " + realm.where(User.class).findAll().size());

        // Arahkan ke halaman verifikasi
        Intent intent = new Intent(SignupActivity.this, VerificationActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null && !realm.isClosed()) {
            realm.close();
        }
    }
}
