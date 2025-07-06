package com.example.bengkelkuapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bengkelkuapp.R;
import com.example.bengkelkuapp.model.CarItem;

import java.util.List;

public class GarageAdapter extends RecyclerView.Adapter<GarageAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(CarItem carItem, int position);
    }

    private List<CarItem> carList;
    private OnItemClickListener listener;

    public GarageAdapter(List<CarItem> carList, OnItemClickListener listener) {
        this.carList = carList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public GarageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_garage_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GarageAdapter.ViewHolder holder, int position) {
        CarItem car = carList.get(position);

        holder.tvBrand.setText(car.getBrand());
        holder.tvModel.setText(car.getModel());
        holder.tvYear.setText(car.getYear());
        holder.tvPlate.setText(car.getPlate());
        holder.ivCar.setImageResource(car.getImageRes());

        // Optional: listener untuk card item
        holder.itemView.setOnClickListener(v -> listener.onItemClick(car, position));
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvBrand, tvModel, tvYear, tvPlate;
        ImageView ivCar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBrand = itemView.findViewById(R.id.tv_brand);
            tvModel = itemView.findViewById(R.id.tv_model);
            tvYear = itemView.findViewById(R.id.tv_year);
            tvPlate = itemView.findViewById(R.id.tv_plate);
            ivCar = itemView.findViewById(R.id.iv_car);
        }
    }
}
