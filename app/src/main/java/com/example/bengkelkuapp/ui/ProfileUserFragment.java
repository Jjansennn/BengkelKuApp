package com.example.bengkelkuapp.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.bengkelkuapp.R;
import com.example.bengkelkuapp.model.User;

import io.realm.Realm;

public class ProfileUserFragment extends Fragment {

    private TextView txvNama, txvEmail, txvNama2, txvGender, txvPassword, txvButton;
    private String originalNama, originalEmail, originalGender, originalPassword;
    private Realm realm;
    private SharedPreferences prefs;

    public ProfileUserFragment() {
        super(R.layout.fragment_profile_user);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Realm.init(requireContext());
        realm = Realm.getDefaultInstance();
        prefs = requireActivity().getSharedPreferences("userSession", Context.MODE_PRIVATE);

        String emailSession = prefs.getString("email", null);

        txvNama = view.findViewById(R.id.txvNama);
        txvNama2 = view.findViewById(R.id.txvNama2);
        txvEmail = view.findViewById(R.id.txvEmail);
        txvGender = view.findViewById(R.id.txvGender);
        txvPassword = view.findViewById(R.id.txvPassword);
        txvButton = view.findViewById(R.id.txvButton);
        ImageView btnBack = view.findViewById(R.id.btnBack);

        if (emailSession != null) {
            User user = realm.where(User.class).equalTo("email", emailSession).findFirst();
            if (user != null) {
                originalNama = user.getNama();
                originalEmail = user.getEmail();
                originalGender = user.getGender();
                originalPassword = user.getPassword();

                txvNama.setText(originalNama);
                txvNama2.setText(originalNama);
                txvEmail.setText(originalEmail);
                txvGender.setText(originalGender);
                txvPassword.setText(originalPassword);

                txvButton.setVisibility(View.GONE);
            }
        }

        view.findViewById(R.id.edt1).setOnClickListener(v -> showEditDialog("Edit Email", txvEmail));
        view.findViewById(R.id.edt2).setOnClickListener(v -> showEditDialog("Edit Nama", txvNama));
        view.findViewById(R.id.edt3).setOnClickListener(v -> showEditDialog("Edit Gender", txvGender));
        view.findViewById(R.id.edt4).setOnClickListener(v -> showEditDialog("Edit Password", txvPassword));

        txvButton.setOnClickListener(v -> saveChanges());

        btnBack.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_profileUser_to_profile);
        });
    }

    private void showEditDialog(String title, TextView targetView) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(title);

        final EditText input = new EditText(requireContext());
        input.setText(targetView.getText().toString());

        if (targetView.getId() == R.id.txvPassword) {
            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            input.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else if (targetView.getId() == R.id.txvEmail) {
            input.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        } else {
            input.setInputType(InputType.TYPE_CLASS_TEXT);
        }

        builder.setView(input);
        builder.setPositiveButton("OK", (dialog, which) -> {
            String newValue = input.getText().toString().trim();
            if (!newValue.equals(targetView.getText().toString().trim())) {
                targetView.setText(newValue);
                if (targetView.getId() == R.id.txvNama) {
                    txvNama2.setText(newValue);
                }
                checkForChanges();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void checkForChanges() {
        String nama = txvNama.getText().toString().trim();
        String emailNew = txvEmail.getText().toString().trim();
        String gender = txvGender.getText().toString().trim();
        String pass = txvPassword.getText().toString().trim();

        boolean changed = !nama.equals(originalNama)
                || !emailNew.equals(originalEmail)
                || !gender.equals(originalGender)
                || !pass.equals(originalPassword);

        txvButton.setVisibility(changed ? View.VISIBLE : View.GONE);
    }

    private void saveChanges() {
        String namaInput = txvNama.getText().toString().trim();
        String emailBaru = txvEmail.getText().toString().trim().toLowerCase();
        String genderInput = txvGender.getText().toString().trim();
        String password = txvPassword.getText().toString().trim();

        if (TextUtils.isEmpty(namaInput) || TextUtils.isEmpty(emailBaru) ||
                TextUtils.isEmpty(genderInput) || TextUtils.isEmpty(password)) {
            Toast.makeText(getContext(), "Semua data wajib diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailBaru).matches()) {
            Toast.makeText(getContext(), "Format email tidak valid", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(getContext(), "Password minimal 6 karakter", Toast.LENGTH_SHORT).show();
            return;
        }

        String capitalizedNama = capitalizeWords(namaInput);
        String capitalizedGender = capitalizeWords(genderInput);

        realm.executeTransaction(r -> {
            User user = r.where(User.class).equalTo("email", originalEmail).findFirst();
            if (user != null) {
                user.setNama(capitalizedNama);
                user.setEmail(emailBaru);
                user.setGender(capitalizedGender);
                user.setPassword(password);
            }
        });

        // Update SharedPreferences
        prefs.edit().putString("email", emailBaru).apply();

        // Update display & data original
        originalNama = capitalizedNama;
        originalEmail = emailBaru;
        originalGender = capitalizedGender;
        originalPassword = password;

        txvNama.setText(capitalizedNama);
        txvNama2.setText(capitalizedNama);
        txvGender.setText(capitalizedGender);

        txvButton.setVisibility(View.GONE);
        Toast.makeText(getContext(), "Data berhasil diperbarui", Toast.LENGTH_SHORT).show();
    }

    private String capitalizeWords(String input) {
        String[] words = input.trim().split(" ");
        StringBuilder sb = new StringBuilder();
        for (String w : words) {
            if (!w.isEmpty()) {
                sb.append(Character.toUpperCase(w.charAt(0)))
                        .append(w.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        return sb.toString().trim();
    }
}
