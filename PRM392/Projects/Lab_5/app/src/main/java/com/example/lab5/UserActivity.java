package com.example.lab5;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {
    ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user);

        RecyclerView rvUser = findViewById(R.id.rvUser);

        users = new ArrayList<>();
        users.add(new User("NguyenTT", "Tran Thanh Nguyen", "Nguyentt@ftp.edu.vn"));
        users.add(new User("DatNH", "Nguyen Hoang Dat", "datnhse170330@fpt.edu.vn"));
        users.add(new User("HieuDT", "Do The Hieu", "hieu@fpt.edu.vn"));
        users.add(new User("ThucHN", "Hoang Ngoc Thuc", "thuc@fpt.edu.vn"));

        UserAdapter adapter = new UserAdapter(users);

        rvUser.setAdapter(adapter);

        rvUser.setLayoutManager(new LinearLayoutManager(this));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}