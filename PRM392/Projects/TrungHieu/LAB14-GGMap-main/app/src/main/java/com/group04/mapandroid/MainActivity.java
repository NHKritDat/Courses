package com.group04.mapandroid;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    Button btnShowMap, btnLocation, btnChangeMarker, btnSearchView, btnChangeType, btnTrack, btnZoomAndCompass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnShowMap = findViewById(R.id.btnShowMap);
        btnLocation = findViewById(R.id.btnLocation);
        btnChangeMarker = findViewById(R.id.btnChangeMarker);
        btnSearchView = findViewById(R.id.btnSearchView);
        btnChangeType = findViewById(R.id.btnChangeType);
        btnTrack = findViewById(R.id.btnTrack);
        btnZoomAndCompass = findViewById(R.id.btnZoomAndCompass);

        btnShowMap.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MapActivity.class);
            startActivity(intent);
        });

        btnLocation.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LocationActivity.class);
            startActivity(intent);
        });

        btnChangeMarker.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ChangeMarkerActivity.class);
            startActivity(intent);
        });

        btnSearchView.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SearchViewActivity.class);
            startActivity(intent);
        });

        btnChangeType.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ChangeMapTypeActivity.class);
            startActivity(intent);
        });

        btnTrack.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, TrackActivity.class);
            startActivity(intent);
        });

        btnZoomAndCompass.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,ZoomAndCompassActivity.class);
            startActivity(intent);
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}