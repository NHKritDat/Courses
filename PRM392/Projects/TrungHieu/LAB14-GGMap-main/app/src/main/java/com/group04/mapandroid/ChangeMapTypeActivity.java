package com.group04.mapandroid;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import androidx.appcompat.widget.Toolbar;

public class ChangeMapTypeActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap gMap;
    FrameLayout map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_map_type);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        LatLng mapChanh = new LatLng(12.580153, 107.860202);
        gMap.moveCamera(CameraUpdateFactory.newLatLng(mapChanh));
        MarkerOptions options = new MarkerOptions().position(mapChanh).title("Marker in nhà Chánh");
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        gMap.addMarker(options);

        gMap.getUiSettings().setZoomControlsEnabled(true);
        gMap.getUiSettings().setCompassEnabled(true);

        gMap.getUiSettings().setZoomGesturesEnabled(false);
        gMap.getUiSettings().setScrollGesturesEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mapNone){
            gMap.setMapType(GoogleMap.MAP_TYPE_NONE);
        }

        if (id == R.id.mapNormal){
            gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }

        if (id == R.id.mapSatellite){
            gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }

        if (id == R.id.mapHybrid){
            gMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }

        if (id == R.id.mapTerrain){
            gMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        }
        return super.onOptionsItemSelected(item);
    }
}