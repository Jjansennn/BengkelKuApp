package com.example.bengkelkuapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.bengkelkuapp.R;
import com.example.bengkelkuapp.model.CarItem;

import java.util.List;

public class GarageDetailFragment extends Fragment {

    private List<CarItem> carList;
    private int currentPosition;

    public GarageDetailFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_garage_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            carList = args.getParcelableArrayList("carList");
            currentPosition = args.getInt("position", 0);
            displayCarDetails(view);
        }

        view.findViewById(R.id.btn_back).setOnClickListener(v ->
                Navigation.findNavController(v).navigateUp());

        view.findViewById(R.id.previous).setOnClickListener(v -> {
            if (currentPosition > 0) {
                currentPosition--;
                displayCarDetails(view);
            }
        });

        view.findViewById(R.id.next).setOnClickListener(v -> {
            if (carList != null && currentPosition < carList.size() - 1) {
                currentPosition++;
                displayCarDetails(view);
            }
        });
    }

    private void displayCarDetails(View view) {
        if (carList == null || carList.isEmpty()) return;

        CarItem car = carList.get(currentPosition);

        ((TextView) view.findViewById(R.id.tv_brand_detail)).setText(car.getBrand());
        ((TextView) view.findViewById(R.id.tv_model_detail)).setText(car.getModel());
        ((TextView) view.findViewById(R.id.tv_year_detail)).setText(car.getYear());
        ((TextView) view.findViewById(R.id.tv_plate_detail)).setText(car.getPlate());
        ((ImageView) view.findViewById(R.id.iv_car_detail)).setImageResource(car.getImageRes());
    }
}
