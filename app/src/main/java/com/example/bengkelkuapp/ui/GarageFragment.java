package com.example.bengkelkuapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bengkelkuapp.R;
import com.example.bengkelkuapp.adapter.GarageAdapter;
import com.example.bengkelkuapp.model.CarItem;

import java.util.ArrayList;
import java.util.List;

public class GarageFragment extends Fragment {

    public GarageFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_garage, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView btnBack = view.findViewById(R.id.btn_back);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());
        }

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewGarage);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        List<CarItem> carList = new ArrayList<>();
        carList.add(new CarItem("Hyundai", "Creta N Line", "2025", "BK 1457 AEK", R.drawable.creta));
        carList.add(new CarItem("Hyundai", "Kona Electric", "2024", "BK 2468 ZY", R.drawable.konaelectric));
        carList.add(new CarItem("Hyundai", "Cayman Turbo", "2023", "BK 1947 JZ", R.drawable.cayman));

        GarageAdapter adapter = new GarageAdapter(carList, (carItem, position) -> {
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("carList", new ArrayList<>(carList));
            bundle.putInt("position", position);
            Navigation.findNavController(view).navigate(R.id.action_garageFragment_to_garageDetailFragment, bundle);
        });

        recyclerView.setAdapter(adapter);
    }
}
