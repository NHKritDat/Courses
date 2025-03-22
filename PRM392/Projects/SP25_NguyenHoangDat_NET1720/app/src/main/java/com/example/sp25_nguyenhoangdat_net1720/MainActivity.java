package com.example.sp25_nguyenhoangdat_net1720;

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
import com.example.sp25_nguyenhoangdat_net1720.activities.LoginActivity;
import com.example.sp25_nguyenhoangdat_net1720.adapters.ViewPagerAdapter;
import com.example.sp25_nguyenhoangdat_net1720.database.DatabaseManager;
import com.example.sp25_nguyenhoangdat_net1720.entities.Author;
import com.example.sp25_nguyenhoangdat_net1720.entities.Book;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
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
        setSupportActionBar(findViewById(R.id.toolbar));
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sharedPreferences = getSharedPreferences("login_prefs", MODE_PRIVATE);

        TextView tvUserName = findViewById(R.id.tv_user_name);
        TextView tvUserEmail = findViewById(R.id.tv_user_email);
        ImageView ivUserPhoto = findViewById(R.id.iv_user_photo);

        String userName = sharedPreferences.getString("user_name", "User");
        String userEmail = sharedPreferences.getString("user_email", "");
        String userPhoto = sharedPreferences.getString("user_photo", "");

        tvUserName.setText(userName);
        tvUserEmail.setText(userEmail);

        if (!userPhoto.isEmpty()) {
            Glide.with(this)
                    .load(userPhoto)
                    .circleCrop()
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(ivUserPhoto);
        }

        dbManager = new DatabaseManager(this);
        dbManager.open();
        // Initialize sample data if needed
        initSampleData();

        // Initialize UI components
        setupViewPager();
    }

    // Add this method to MainActivity.java
    private void signOut() {
        // Get Firebase auth instance
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Sign out from Firebase
        mAuth.signOut();

        // Sign out from Google
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                task -> {
                    // Clear SharedPreferences
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();

                    // Redirect to LoginActivity
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                });
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
            signOut();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
                        tab.setText("Book");
                    } else {
                        tab.setText("Author");
                    }
                }
        ).attach();
    }

    private void initSampleData() {
        // Check if database is empty
        if (dbManager.getAllAuthor().size() == 0) {
            // Create some sample data
            Author author = new Author("Nguyen Hoang Dat", "datnhse170330@fpt.edu.vn", "0966548257", "Buon Ma Thuot, Dak Lak");
            long authorId = dbManager.createAuthor(author);

            // Books by author 1
            Book book1 = new Book("Lap Trinh Android", "2023-01-01", "Công nghệ", (int) authorId);
            dbManager.createBook(book1);

            Book book2 = new Book("Java Programming", "2022-05-15", "Lập trình", (int) authorId);
            dbManager.createBook(book2);

            // Books by author 2
            Book book3 = new Book("Thiết kế Web", "2023-03-20", "Công nghệ", (int) authorId);
            dbManager.createBook(book3);

            Book book4 = new Book("Học Machine Learning", "2022-10-10", "AI", (int) authorId);
            dbManager.createBook(book4);

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