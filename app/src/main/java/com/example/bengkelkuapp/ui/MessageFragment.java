package com.example.bengkelkuapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.bengkelkuapp.R;

public class MessageFragment extends Fragment {

    public MessageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Navigasi ke ChatFragment saat idRonaldo diklik
        LinearLayout idRonaldo = view.findViewById(R.id.idRonaldo);
        idRonaldo.setOnClickListener(v -> {
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_messageFragment_to_chatFragment);
        });

        // Tombol back
        View btnBack = view.findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> {
                NavHostFragment.findNavController(this)
                        .navigate(R.id.navigation_status);
            });
        }
    }

}
