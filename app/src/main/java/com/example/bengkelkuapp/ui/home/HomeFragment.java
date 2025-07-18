package com.example.bengkelkuapp.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.bengkelkuapp.R;
import com.example.bengkelkuapp.databinding.FragmentHomeBinding;
import com.example.bengkelkuapp.model.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import io.realm.Realm;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private FragmentHomeBinding binding;
    private GoogleMap mMap;
    private Realm realm;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        // Inflate layout with ViewBinding
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Inisialisasi Realm
        Realm.init(requireContext());
        realm = Realm.getDefaultInstance();

        // Tampilkan teks promosi HTML
        String html = "<font color='#000000'>Find and book </font>" +
                "<font color='#F5A623'>trusted workshops</font>" +
                "<font color='#000000'> near you</font>";
        binding.textTrusted.setText(Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT));

        // Ambil email dari SharedPreferences
        SharedPreferences prefs = requireActivity().getSharedPreferences("userSession", Context.MODE_PRIVATE);
        String email = prefs.getString("email", null);

        // Ambil nama user dari Realm dan tampilkan
        if (email != null) {
            User user = realm.where(User.class).equalTo("email", email).findFirst();
            if (user != null && user.getNama() != null) {
                String namaUser = capitalizeWords(user.getNama());
                binding.txvNama.setText(namaUser); // Pastikan txvNama ada di XML
            }
        }

        // Navigasi ke Garage
        TextView yourGarageButton = root.findViewById(R.id.your_garage_button);
        yourGarageButton.setOnClickListener(v ->
                Navigation.findNavController(root).navigate(R.id.action_home_to_garage)
        );

        // Navigasi ke SearchFragment
        binding.search.setFocusable(false);
        binding.search.setOnClickListener(v ->
                Navigation.findNavController(root).navigate(R.id.navigation_search)
        );

        // Load Google Map (harus dipanggil setelah onCreateView, setelah root tersedia)
        SupportMapFragment mapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Toast.makeText(requireContext(), "Map gagal dimuat", Toast.LENGTH_SHORT).show();
        }

        return root;
    }

    // Untuk memformat kapitalisasi nama user
    private String capitalizeWords(String input) {
        String[] parts = input.trim().split(" ");
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            if (!part.isEmpty()) {
                sb.append(Character.toUpperCase(part.charAt(0)));
                if (part.length() > 1) {
                    sb.append(part.substring(1).toLowerCase());
                }
                sb.append(" ");
            }
        }
        return sb.toString().trim();
    }

    // Dipanggil saat Map sudah siap
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Lokasi Medan
        LatLng medan = new LatLng(3.5952, 98.6722); // Koordinat Medan
        mMap.addMarker(new MarkerOptions().position(medan).title("Marker in Medan"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(medan, 12));
    }

    // Tutup Realm saat Fragment dihancurkan
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (realm != null && !realm.isClosed()) {
            realm.close();
        }
        binding = null;
    }
}
