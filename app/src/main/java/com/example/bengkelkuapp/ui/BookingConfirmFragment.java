package com.example.bengkelkuapp.ui;

import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
            // Android 12+ pakai RenderEffect
            RenderEffect blur = RenderEffect.createBlurEffect(20f, 20f, Shader.TileMode.CLAMP);
            blurBackground.setRenderEffect(blur);
        } else {
            // Untuk Android < 12: pakai alpha saja sebagai fallback
            blurBackground.setAlpha(0.6f);
        }
    }
}
