package com.example.bengkelkuapp.ui;

import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.bengkelkuapp.R;

public class BookingConfirmFragment extends Fragment {

    public BookingConfirmFragment() {
        super(R.layout.fragment_booking_confirm); // Pastikan nama file layout kamu benar
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView blurBackground = view.findViewById(R.id.blur_background);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            RenderEffect blur = RenderEffect.createBlurEffect(20f, 20f, Shader.TileMode.CLAMP);
            blurBackground.setRenderEffect(blur);
        } else {
            blurBackground.setAlpha(0.6f); // Fallback untuk Android < 12
        }

        // Tombol "Continue"
        Button btnContinue = view.findViewById(R.id.btn_continue);
        btnContinue.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.navigation_workshop); // Kembali ke WorkshopFragment
        });
    }
}
