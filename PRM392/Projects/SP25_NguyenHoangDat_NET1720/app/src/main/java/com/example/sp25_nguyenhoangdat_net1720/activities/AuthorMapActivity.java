package com.example.sp25_nguyenhoangdat_net1720.activities;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sp25_nguyenhoangdat_net1720.R;
import com.example.sp25_nguyenhoangdat_net1720.database.DatabaseManager;
import com.example.sp25_nguyenhoangdat_net1720.entities.Author;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AuthorMapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private DatabaseManager dbManager;
    private int authorId;
    private Author author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_map);

        authorId = getIntent().getIntExtra("author_id", -1);

        if (authorId == -1) {
            Toast.makeText(this, "Error: author not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Initialize database
        dbManager = new DatabaseManager(this);
        dbManager.open();

        // Get author data
        author = dbManager.getAuthor(authorId);

        if (author == null) {
            Toast.makeText(this, "Author cannot be loaded", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Set title to author name
        setTitle("Vị trí của " + author.getName());

        // Initialize map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Convert address to coordinates
        showAuthorLocation();
    }

    private void showAuthorLocation() {
        String address = author.getAddress();
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocationName(address, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address location = addresses.get(0);
                LatLng position = new LatLng(location.getLatitude(), location.getLongitude());

                // Add marker for the author
                mMap.addMarker(new MarkerOptions()
                        .position(position)
                        .title(author.getName())
                        .snippet(author.getAddress()));

                // Move camera to the marker
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15));
            } else {
                Toast.makeText(this, "Cannot find location", Toast.LENGTH_SHORT).show();
                // Default to a fallback location (Vietnam)
                LatLng vietnam = new LatLng(14.058324, 108.277199);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(vietnam, 5));
            }
        } catch (IOException e) {
            Toast.makeText(this, "GPC error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbManager != null) {
            dbManager.close();
        }
    }
}