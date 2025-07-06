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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.bengkelkuapp.R;

public class WorkshopFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public WorkshopFragment() {
        // Required empty public constructor
    }

    public static WorkshopFragment newInstance(String param1, String param2) {
        WorkshopFragment fragment = new WorkshopFragment();
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
        return inflater.inflate(R.layout.fragment_workshop, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);

        // Navigasi ke detail workshop
        LinearLayout layoutBintang = view.findViewById(R.id.layout_bintang_automotive);
        layoutBintang.setOnClickListener(v ->
                navController.navigate(R.id.action_workshopFragment_to_workshopDetailFragment)
        );

        // Tombol back ke HomeFragment
        ImageView backArrow = view.findViewById(R.id.btnBack);
        if (backArrow != null) {
            backArrow.setOnClickListener(v ->
                    Navigation.findNavController(view).navigate(R.id.navigation_home)
            );
        }
    }
}
