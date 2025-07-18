package com.example.bengkelkuapp.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.bengkelkuapp.R;

public class SearchFragment extends Fragment {

    private EditText edtSearch;
    private View layoutBintangAutomotive, layoutAuto7000, layoutRon;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // Inisialisasi tombol back
        ImageView btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            NavHostFragment.findNavController(SearchFragment.this)
                    .navigate(R.id.navigation_home); // navigasi langsung ke home
        });

        edtSearch = view.findViewById(R.id.editTextSearch);

        layoutBintangAutomotive = view.findViewById(R.id.layout_bintang_automotive);
        layoutAuto7000 = view.findViewById(R.id.layout_auto7000);
        layoutRon = view.findViewById(R.id.layout_ron);

        // Set tag untuk masing-masing layout (di lowercase)
        layoutBintangAutomotive.setTag("bintang automotive medan");
        layoutAuto7000.setTag("auto 7000 medan");
        layoutRon.setTag("ron garage medan");

        // Tampilkan semua saat awal
        showAllLayouts();

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterLayouts(s.toString().toLowerCase().trim());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return view;
    }

    private void filterLayouts(String keyword) {
        if (keyword.isEmpty()) {
            showAllLayouts();
            return;
        }

        filterItem(layoutBintangAutomotive, keyword);
        filterItem(layoutAuto7000, keyword);
        filterItem(layoutRon, keyword);
    }

    private void showAllLayouts() {
        layoutBintangAutomotive.setVisibility(View.VISIBLE);
        layoutAuto7000.setVisibility(View.VISIBLE);
        layoutRon.setVisibility(View.VISIBLE);
    }

    private void filterItem(View layoutItem, String keyword) {
        if (layoutItem.getTag() != null) {
            String tag = layoutItem.getTag().toString().toLowerCase();
            String[] tagWords = tag.split("\\s+"); // misal: bintang automotive medan

            String input = keyword.trim().toLowerCase();

            // hanya cocokkan dengan kata pertama di tag
            if (tagWords.length > 0 && tagWords[0].startsWith(input)) {
                layoutItem.setVisibility(View.VISIBLE);
            } else {
                layoutItem.setVisibility(View.GONE);
            }
        }
    }

}
