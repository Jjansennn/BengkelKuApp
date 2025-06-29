package com.example.bengkelkuapp.ui.home;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.bengkelkuapp.R;
import com.example.bengkelkuapp.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Gunakan ViewBinding
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Set teks "Find and book trusted workshops near you"
        String html = "<font color='#000000'>Find and book </font>" +
                "<font color='#F5A623'>trusted workshops</font>" +
                "<font color='#000000'> near you</font>";
        binding.textTrusted.setText(Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT));

        // Temukan tombol 'Your Garage' dari layout include
        TextView yourGarageButton = root.findViewById(R.id.your_garage_button);
        yourGarageButton.setOnClickListener(v ->
                Navigation.findNavController(root).navigate(R.id.action_home_to_garage)
        );

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
