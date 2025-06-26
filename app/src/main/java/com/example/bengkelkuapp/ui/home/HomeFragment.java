package com.example.bengkelkuapp.ui.home;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.bengkelkuapp.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inisialisasi ViewModel (jika ada data dinamis)
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        // Gunakan ViewBinding untuk inflate layout
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Menampilkan teks dengan kombinasi warna HTML
        String html = "<font color='#000000'>Find and book </font>" +
                "<font color='#F5A623'>trusted workshops</font>" +
                "<font color='#000000'> near you</font>";
        binding.textTrusted.setText(Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT));

        // Jika ingin ambil teks dari LiveData:
        // homeViewModel.getText().observe(getViewLifecycleOwner(), binding.textTrusted::setText);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // untuk menghindari memory leak
    }
}
