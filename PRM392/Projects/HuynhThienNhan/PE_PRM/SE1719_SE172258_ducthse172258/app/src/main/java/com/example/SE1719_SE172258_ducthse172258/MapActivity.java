package com.example.SE1719_SE172258_ducthse172258;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_map);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_id);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        // set location
        //TP Ho Chi Minh
        LatLng tpHoChiMinh_location = new LatLng(10.762622, 106.660172);
        googleMap.addMarker(new MarkerOptions().position(tpHoChiMinh_location).title("Student 1"));
        // Thuan An Binh Duong
        LatLng ThuanAn_BinhDuong_location = new LatLng(10.924067, 106.713028);
        googleMap.addMarker(new MarkerOptions().position(ThuanAn_BinhDuong_location).title("Student 2").flat(true));
        // Binh Thanh , TP HoChiMinh
        LatLng BinhThanh_HoCHiMinh_location = new LatLng(10.810583, 106.709145);
        googleMap.addMarker(new MarkerOptions().position(BinhThanh_HoCHiMinh_location).title("Student 3").flat(true));
        // Binh Thanh , TP HoChiMinh
        LatLng ThuDuc_TPHoCHiMinh_location = new LatLng(10.85142, 106.74727);
        googleMap.addMarker(new MarkerOptions().position(ThuDuc_TPHoCHiMinh_location).title("Student 4").flat(true));

        // Quan 1 , TP HoChiMinh
        LatLng quan1_TPHoCHiMinh_location = new LatLng(10.7757, 106.7004);
        googleMap.addMarker(new MarkerOptions().position(quan1_TPHoCHiMinh_location).title("Student 5").flat(true));

        // set up camera zoom
        //******************
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tpHoChiMinh_location, 12));


        // set up button zoom in zoom out
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);
    }
}