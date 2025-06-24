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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Ganti teks dengan HTML berwarna
        String html = "<font color='#000000'>Find and book </font>" +
                "<font color='#F5A623'>trusted workshops</font>" +
                "<font color='#000000'> near you</font>";
        binding.textTrusted.setText(Html.fromHtml(html));

        // Jika ingin tetap menggunakan LiveData ViewModel
        // homeViewModel.getText().observe(getViewLifecycleOwner(), binding.textTrusted::setText);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
