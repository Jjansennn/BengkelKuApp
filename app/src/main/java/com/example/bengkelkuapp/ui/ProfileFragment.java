package com.example.bengkelkuapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bengkelkuapp.LoginActivity;
import com.example.bengkelkuapp.R;
import com.example.bengkelkuapp.SignupActivity;
import com.example.bengkelkuapp.model.User;

import io.realm.Realm;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        super(R.layout.fragment_profile); // pastikan layout kamu ini benar
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Realm.init(requireContext());
        Realm realm = Realm.getDefaultInstance();

        // Ambil email user yang sedang login dari SharedPreferences
        SharedPreferences prefs = requireActivity().getSharedPreferences("userSession", Context.MODE_PRIVATE);
        String email = prefs.getString("email", null);

        // Inisialisasi komponen tampilan
        TextView txtNama = view.findViewById(R.id.Nama);
        TextView txtEmail = view.findViewById(R.id.email);
        LinearLayout btnLogout = view.findViewById(R.id.logout);
        LinearLayout btnDelete = view.findViewById(R.id.delacc);

        // Tampilkan informasi user di UI
        if (email != null) {
            User user = realm.where(User.class).equalTo("email", email).findFirst();
            if (user != null) {
                txtNama.setText(user.getEmail().split("@")[0]); // misal ambil dari email
                txtEmail.setText(user.getEmail());
            }
        }

        // Tombol Logout
        btnLogout.setOnClickListener(v -> {
            prefs.edit().clear().apply(); // hapus sesi login
            Toast.makeText(getContext(), "Logout berhasil", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        // Tombol Delete Account
        btnDelete.setOnClickListener(v -> {
            if (email != null) {
                realm.executeTransaction(r -> {
                    User user = r.where(User.class).equalTo("email", email).findFirst();
                    if (user != null) {
                        user.deleteFromRealm();
                    }
                });
                prefs.edit().clear().apply();
                Toast.makeText(getContext(), "Akun berhasil dihapus", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), SignupActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Realm.getDefaultInstance().close();
    }
}
