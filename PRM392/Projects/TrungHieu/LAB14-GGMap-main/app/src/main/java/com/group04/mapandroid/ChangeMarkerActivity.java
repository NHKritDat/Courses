package com.group04.mapandroid;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ChangeMarkerActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap gMap;
    FrameLayout map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_marker);

        map = findViewById(R.id.map);

        if (savedInstanceState == null) {
            SupportMapFragment mapFragment = new SupportMapFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.map, mapFragment)
                    .commit();
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.gMap = googleMap;

        LatLng nhaToi = new LatLng(10.786316, 106.678339);
        this.gMap.moveCamera(CameraUpdateFactory.newLatLng(nhaToi));

        MarkerOptions options = new MarkerOptions().position(nhaToi).title("Nhà của tôi");
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        this.gMap.addMarker(options);
    }
}