package com.example.bengkelkuapp.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.bengkelkuapp.R;
import com.example.bengkelkuapp.databinding.FragmentHomeBinding;
import com.example.bengkelkuapp.model.User;

import io.realm.Realm;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Realm realm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Inisialisasi Realm
        Realm.init(requireContext());
        realm = Realm.getDefaultInstance();

        // Set teks promosi dengan HTML
        String html = "<font color='#000000'>Find and book </font>" +
                "<font color='#F5A623'>trusted workshops</font>" +
                "<font color='#000000'> near you</font>";
        binding.textTrusted.setText(Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT));

        // Ambil email user dari SharedPreferences
        SharedPreferences prefs = requireActivity().getSharedPreferences("userSession", Context.MODE_PRIVATE);
        String email = prefs.getString("email", null);

        // Jika email tersedia, cari user di Realm dan tampilkan nama
        if (email != null) {
            User user = realm.where(User.class).equalTo("email", email).findFirst();
            if (user != null && user.getNama() != null) {
                String namaUser = capitalizeWords(user.getNama());
                binding.txvNama.setText(namaUser); // Pastikan TextView id: txvNama
            }
        }

        // Navigasi ke Garage saat tombol "Your Garage" ditekan
        TextView yourGarageButton = root.findViewById(R.id.your_garage_button);
        yourGarageButton.setOnClickListener(v ->
                Navigation.findNavController(root).navigate(R.id.action_home_to_garage)
        );

        // Navigasi ke SearchFragment saat search bar diklik
        binding.search.setFocusable(false);
        binding.search.setOnClickListener(v ->
                Navigation.findNavController(root).navigate(R.id.navigation_search)
        );

        return root;
    }

    // Kapitalisasi setiap awal kata pada nama user
    private String capitalizeWords(String input) {
        String[] parts = input.trim().split(" ");
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
    public void onDestroyView() {
        super.onDestroyView();
        if (realm != null && !realm.isClosed()) {
            realm.close();
        }
        binding = null;
    }
}
