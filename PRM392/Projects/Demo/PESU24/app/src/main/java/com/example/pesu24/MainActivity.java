package com.example.pesu24;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.pesu24.activities.LoginActivity;
import com.example.pesu24.adapters.ViewPagerAdapter;
import com.example.pesu24.database.DatabaseManager;
import com.example.pesu24.entities.Sach;
import com.example.pesu24.entities.TacGia;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private DatabaseManager dbManager;
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize shared preferences
        sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE);
//
//        // Check login status
//        if (!isLoggedIn()) {
//            navigateToLoginActivity();
//            return;
//        }

        // Display user information from SharedPreferences
        TextView tvUserName = findViewById(R.id.tv_user_name);
        TextView tvUserEmail = findViewById(R.id.tv_user_email);
        ImageView ivUserPhoto = findViewById(R.id.iv_user_photo);

        String userName = sharedPreferences.getString("user_name", "User");
        String userEmail = sharedPreferences.getString("user_email", "");
        String userPhoto = sharedPreferences.getString("user_photo", "");

        tvUserName.setText(userName);
        tvUserEmail.setText(userEmail);

        // Load user profile photo if available
        if (!userPhoto.isEmpty()) {
            Glide.with(this)
                    .load(userPhoto)
                    .circleCrop()
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(ivUserPhoto);
        }

        // Initialize and open database
        dbManager = new DatabaseManager(this);
        dbManager.open();

        // Initialize sample data if needed
        initSampleData();

        // Initialize UI components
        setupViewPager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
//            signOut();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // In MainActivity.java
    private void signOut() {
        FirebaseAuth.getInstance().signOut();

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        navigateToLoginActivity();
    }

    private boolean isLoggedIn() {
        return sharedPreferences.getBoolean("is_logged_in", false);
    }

    private void navigateToLoginActivity() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void setupViewPager() {
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);

        ViewPagerAdapter adapter = new ViewPagerAdapter(this, dbManager);
        viewPager.setAdapter(adapter);

        // Connect TabLayout with ViewPager2
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    if (position == 0) {
                        tab.setText("Sách");
                    } else {
                        tab.setText("Tác giả");
                    }
                }
        ).attach();
    }

    private void initSampleData() {
        // Check if database is empty
        if (dbManager.getAllTacGia().size() == 0) {
            // Create some sample data
            // Author 1
            TacGia tacGia1 = new TacGia("Nguyen Van A", "nguyenvana@example.com", "Ha Noi", "0123456789");
            long tacGiaId1 = dbManager.createTacGia(tacGia1);

            // Author 2
            TacGia tacGia2 = new TacGia("Tran Thi B", "tranthib@example.com", "Ho Chi Minh", "0987654321");
            long tacGiaId2 = dbManager.createTacGia(tacGia2);

            // Books by author 1
            Sach sach1 = new Sach("Lap Trinh Android", "2023-01-01", "Công nghệ", (int) tacGiaId1);
            dbManager.createSach(sach1);

            Sach sach2 = new Sach("Java Programming", "2022-05-15", "Lập trình", (int) tacGiaId1);
            dbManager.createSach(sach2);

            // Books by author 2
            Sach sach3 = new Sach("Thiết kế Web", "2023-03-20", "Công nghệ", (int) tacGiaId2);
            dbManager.createSach(sach3);

            Sach sach4 = new Sach("Học Machine Learning", "2022-10-10", "AI", (int) tacGiaId2);
            dbManager.createSach(sach4);

            Log.d(TAG, "Sample data initialized");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close database when activity is destroyed
        if (dbManager != null) {
            dbManager.close();
        }
    }
}