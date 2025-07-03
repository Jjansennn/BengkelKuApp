package com.example.bengkelkuapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.bengkelkuapp.LandingActivity;
import com.example.bengkelkuapp.R;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Navigasi ke ProfileUserFragment saat arw1 diklik
        ImageView arrowToUser = view.findViewById(R.id.arw1);
        if (arrowToUser != null) {
            arrowToUser.setOnClickListener(v ->
                    Navigation.findNavController(view).navigate(R.id.action_profile_to_profileUser)
            );
        }

        // Navigasi ke HomeFragment saat tombol back diklik
        ImageView backArrow = view.findViewById(R.id.btnBack);
        if (backArrow != null) {
            backArrow.setOnClickListener(v ->
                    Navigation.findNavController(view).navigate(R.id.navigation_home)
            );
        }

        // Logout kembali ke LandingActivity
        LinearLayout logout = view.findViewById(R.id.logout);
        if (logout != null) {
            logout.setOnClickListener(v -> {
                Intent intent = new Intent(requireActivity(), LandingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            });
        }
    }
}
