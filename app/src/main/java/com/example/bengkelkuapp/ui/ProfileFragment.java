package com.example.bengkelkuapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.bengkelkuapp.LoginActivity;
import com.example.bengkelkuapp.R;
import com.example.bengkelkuapp.SignupActivity;
import com.example.bengkelkuapp.model.User;

import io.realm.Realm;

public class ProfileFragment extends Fragment {

    private Realm realm;

    public ProfileFragment() {
        super(R.layout.fragment_profile); // Sesuaikan dengan layout XML kamu
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Realm.init(requireContext());
        realm = Realm.getDefaultInstance();

        SharedPreferences prefs = requireActivity().getSharedPreferences("userSession", Context.MODE_PRIVATE);
        String email = prefs.getString("email", null);

        TextView txtNama = view.findViewById(R.id.Nama);
        TextView txtEmail = view.findViewById(R.id.email);
        LinearLayout btnLogout = view.findViewById(R.id.logout);
        LinearLayout btnDelete = view.findViewById(R.id.delacc);
        ImageView btnBack = view.findViewById(R.id.btnBack);
        ImageView arrowToProfile = view.findViewById(R.id.arw1);

        if (email != null) {
            User user = realm.where(User.class).equalTo("email", email).findFirst();
            if (user != null) {
                String nama = user.getNama();
                if (nama == null || nama.isEmpty()) {
                    nama = email.split("@")[0];
                }
                txtNama.setText(capitalizeWords(nama));
                txtEmail.setText(user.getEmail());
            }
        }

        btnLogout.setOnClickListener(v -> {
            prefs.edit().clear().apply();
            Toast.makeText(getContext(), "Logout berhasil", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        btnDelete.setOnClickListener(v -> {
            if (email != null) {
                realm.executeTransaction(r -> {
                    User userToDelete = r.where(User.class).equalTo("email", email).findFirst();
                    if (userToDelete != null) userToDelete.deleteFromRealm();
                });
                prefs.edit().clear().apply();
                Toast.makeText(getContext(), "Akun berhasil dihapus", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), SignupActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        NavController navController = Navigation.findNavController(view);
        btnBack.setOnClickListener(v -> navController.navigate(R.id.action_profile_to_home));
        arrowToProfile.setOnClickListener(v -> navController.navigate(R.id.action_profile_to_profileUser));
    }

    private String capitalizeWords(String input) {
        String[] parts = input.trim().replace("_", " ").replace(".", " ").split(" ");
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            if (!part.isEmpty()) {
                sb.append(Character.toUpperCase(part.charAt(0)));
                if (part.length() > 1) {
                    sb.append(part.substring(1).toLowerCase());
                }
                sb.append(" ");
            }
        }
        return sb.toString().trim();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (realm != null && !realm.isClosed()) {
            realm.close();
        }
    }
}
