package com.example.bengkelkuapp.ui;

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

import com.example.bengkelkuapp.R;

public class StatusDetailFragment extends Fragment {

    public StatusDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout
        return inflater.inflate(R.layout.fragment_status_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout repairLayout = view.findViewById(R.id.repair);
        if (repairLayout != null) {
            repairLayout.setOnClickListener(v ->
                    Navigation.findNavController(view).navigate(R.id.action_statusDetailFragment_to_statusFragment)
            );
        }

        ImageView btnBack = view.findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v ->
                    Navigation.findNavController(view).navigate(R.id.action_statusDetailFragment_to_homeFragment)
            );
        }
    }

}
