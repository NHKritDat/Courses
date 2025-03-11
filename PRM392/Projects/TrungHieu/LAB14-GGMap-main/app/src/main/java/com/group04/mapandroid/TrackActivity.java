package com.group04.mapandroid;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TrackActivity extends AppCompatActivity {
    private EditText etFromLocation;
    private EditText etToLocation;
    private Button btnTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_track);

        etFromLocation = findViewById(R.id.etFromLocation);
        etToLocation = findViewById(R.id.etToLocation);
        btnTrack = findViewById(R.id.btnGetDirection);

        btnTrack.setOnClickListener(view -> {
            String userLocation = etFromLocation.getText().toString();
            String userDestination = etToLocation.getText().toString();

            if (userLocation.equals("") || userDestination.equals("")) {
                Toast.makeText(this,"Hãy nhập nơi xuất phát và nơi đến", Toast.LENGTH_SHORT).show();
            } else {
                getDirections(userLocation, userDestination);
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void getDirections(String from, String to) {
        try{
            Uri uri = Uri.parse("https://www.google.com/maps/dir/" + from + "/" + to);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }catch (ActivityNotFoundException e){
            Uri uri = Uri.parse("https://www.play.google.com/store/apps/details?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}