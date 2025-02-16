package com.example.lab4;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static String orderedFood = null;
    private static String orderedDrink = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnOrderFood = findViewById(R.id.btnOrderFood);
        Button btnOrderDrink = findViewById(R.id.btnOrderDrink);
        Button btnQuit = findViewById(R.id.btnQuit);
        TextView tvOrderedFood = findViewById(R.id.tvOrderedFood);
        TextView tvOrderedDrink = findViewById(R.id.tvOrderedDrink);

        String newOrderedFood = getIntent().getStringExtra("orderedFood");
        String newOrderedDrink = getIntent().getStringExtra("orderedDrink");
        if (newOrderedFood != null) {
            orderedFood = newOrderedFood;
        }
        if (orderedFood != null) {
            tvOrderedFood.setText("Món ăn đã chọn: " + orderedFood);
        } else {
            tvOrderedFood.setText("Món ăn đã chọn: Chưa có");
        }

        if (newOrderedDrink != null) {
            orderedDrink = newOrderedDrink;
        }
        if (orderedDrink != null) {
            tvOrderedDrink.setText("Đồ uống đã chọn: " + orderedDrink);
        } else {
            tvOrderedDrink.setText("Đồ uống đã chọn: Chưa có");
        }

        btnOrderFood.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FoodActivity.class);
            startActivity(intent);
        });

        btnOrderDrink.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DrinkActivity.class);
            startActivity(intent);
        });

        btnQuit.setOnClickListener(v -> {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Thoát")
                    .setMessage("Bạn có chắc chắn muốn thoát ứng dụng?")
                    .setPositiveButton("Thoát", (d, w) -> {
                        finishAffinity();
                        System.exit(0);
                    })
                    .setNegativeButton("Huỷ", null)
                    .show();
        });
    }
}