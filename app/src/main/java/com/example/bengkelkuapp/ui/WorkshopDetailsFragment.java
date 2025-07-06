package com.example.bengkelkuapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.bengkelkuapp.R;

public class WorkshopDetailsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public WorkshopDetailsFragment() {
        // Required empty public constructor
    }

    public static WorkshopDetailsFragment newInstance(String param1, String param2) {
        WorkshopDetailsFragment fragment = new WorkshopDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_workshop_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);

        // Tombol Book
        Button bookButton = view.findViewById(R.id.btn_book_now);
        bookButton.setOnClickListener(v -> {
            navController.navigate(R.id.action_workshopDetailsFragment_to_bookingFragment);
        });

        // Tombol Back ke WorkshopFragment
        ImageView btnBack = view.findViewById(R.id.btnBack);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> {
                navController.navigate(R.id.navigation_workshop);
            });
        }

        // Tombol Message ke MessageFragment
        Button btnMessage = view.findViewById(R.id.btnMessage);
        if (btnMessage != null) {
            btnMessage.setOnClickListener(v -> {
                navController.navigate(R.id.navigation_message);
            });
        }
    }

}
