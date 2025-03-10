package com.example.lap13_authen_otp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView welcomeText;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Get the current user
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // Initialize UI elements
        welcomeText = findViewById(R.id.welcomeText); // Add an ID to your TextView in the layout
        logoutButton = findViewById(R.id.logoutButton); // Add a logout button in the layout

        // Check if user is logged in
        if (currentUser != null) {
            // Display a personalized welcome message using the user's phone number
            String phoneNumber = currentUser.getPhoneNumber();
            welcomeText.setText("Welcome, " + phoneNumber + "!\nYou are now on the Home Screen");
        } else {
            // If no user is logged in, redirect to MainActivity (this shouldn't happen normally)
            Toast.makeText(this, "User not authenticated!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
            finish();
        }

        // Set onClick listener for logout button
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sign out the user
                mAuth.signOut();
                Toast.makeText(HomeActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();

                // Redirect to MainActivity
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Close HomeActivity
            }
        });
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        // Optional: Prevent going back to MainActivity by closing the app instead
        finishAffinity();
    }
}