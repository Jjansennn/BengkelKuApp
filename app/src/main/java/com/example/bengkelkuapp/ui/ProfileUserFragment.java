package com.example.bengkelkuapp.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.bengkelkuapp.R;
import com.example.bengkelkuapp.model.User;

import io.realm.Realm;

public class ProfileUserFragment extends Fragment {

    private TextView txvNama, txvEmail, txvNama2, txvGender, txvPassword, txvButton;
    private ImageView btnBack, editNama, editGender, editPassword;
    private Realm realm;
    private User user;
    private String oldNama, oldGender, oldPassword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_user, container, false);

        Realm.init(requireContext());
        realm = Realm.getDefaultInstance();

        SharedPreferences prefs = requireContext().getSharedPreferences("userSession", Context.MODE_PRIVATE);
        String email = prefs.getString("email", null);

        if (email == null) {
            Toast.makeText(getContext(), "Email tidak ditemukan di session", Toast.LENGTH_SHORT).show();
            return view;
        }

        user = realm.where(User.class).equalTo("email", email).findFirst();
        if (user == null) {
            Toast.makeText(getContext(), "User tidak ditemukan di database", Toast.LENGTH_SHORT).show();
            return view;
        }

        // Inisialisasi view
        txvNama = view.findViewById(R.id.txvNama);
        txvEmail = view.findViewById(R.id.txvEmail);
        txvNama2 = view.findViewById(R.id.txvNama2);
        txvGender = view.findViewById(R.id.txvGender);
        txvPassword = view.findViewById(R.id.txvPassword);
        txvButton = view.findViewById(R.id.txvButton);

        btnBack = view.findViewById(R.id.btnBack);
        editNama = view.findViewById(R.id.edt2);
        editGender = view.findViewById(R.id.edt3);
        editPassword = view.findViewById(R.id.edt4);

        // Tampilkan data user
        oldNama = user.getNama();
        oldGender = user.getGender();
        oldPassword = user.getPassword();

        txvNama.setText(oldNama);
        txvEmail.setText(user.getEmail());
        txvNama2.setText(oldNama);
        txvGender.setText(oldGender);
        txvPassword.setText(oldPassword);
        txvButton.setVisibility(View.GONE);

        // Tombol Back
        btnBack.setOnClickListener(v -> {
            NavController navController = NavHostFragment.findNavController(ProfileUserFragment.this);
            navController.navigate(R.id.action_profileUser_to_profile);
        });

        // Edit Nama
        editNama.setOnClickListener(v -> showEditDialog("Edit Nama", oldNama, value -> {
            String formatted = capitalizeWords(value);
            txvNama.setText(formatted);
            txvNama2.setText(formatted);
            checkChanges();
        }));

        // Edit Gender
        editGender.setOnClickListener(v -> showEditDialog("Edit Gender", oldGender, value -> {
            String formatted = capitalizeWords(value);
            txvGender.setText(formatted);
            checkChanges();
        }));

        // Edit Password
        editPassword.setOnClickListener(v -> showEditDialog("Edit Password", oldPassword, value -> {
            txvPassword.setText(value);
            checkChanges();
        }));

        // Save Changes
        txvButton.setOnClickListener(v -> {
            realm.executeTransaction(r -> {
                user.setNama(txvNama.getText().toString().trim());
                user.setGender(txvGender.getText().toString().trim());
                user.setPassword(txvPassword.getText().toString().trim());
            });

            oldNama = user.getNama();
            oldGender = user.getGender();
            oldPassword = user.getPassword();

            txvButton.setVisibility(View.GONE);
            Toast.makeText(getContext(), "Perubahan disimpan", Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    private void showEditDialog(String title, String currentValue, ValueCallback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(title);

        final EditText editText = new EditText(getContext());
        editText.setInputType(InputType.TYPE_CLASS_TEXT);
        editText.setText(currentValue);
        builder.setView(editText);

        builder.setPositiveButton("OK", (dialog, which) -> {
            String newValue = editText.getText().toString().trim();
            if (!newValue.isEmpty()) {
                callback.onValueEntered(newValue);
            }
        });

        builder.setNegativeButton("Batal", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void checkChanges() {
        String namaBaru = txvNama.getText().toString().trim();
        String genderBaru = txvGender.getText().toString().trim();
        String passwordBaru = txvPassword.getText().toString().trim();

        boolean adaPerubahan = !namaBaru.equals(oldNama)
                || !genderBaru.equals(oldGender)
                || !passwordBaru.equals(oldPassword);

        txvButton.setVisibility(adaPerubahan ? View.VISIBLE : View.GONE);
    }

    private String capitalizeWords(String input) {
        String[] parts = input.trim().toLowerCase().split(" ");
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            if (!part.isEmpty()) {
                sb.append(Character.toUpperCase(part.charAt(0)))
                        .append(part.substring(1)).append(" ");
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

    interface ValueCallback {
        void onValueEntered(String value);
    }
}
