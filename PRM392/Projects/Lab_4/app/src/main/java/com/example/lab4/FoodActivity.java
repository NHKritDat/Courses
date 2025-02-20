package com.example.lab4;

import static android.widget.Toast.LENGTH_SHORT;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class FoodActivity extends AppCompatActivity {
    private int selectedItemPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_food);
        Log.v("FOOD_ACTIVITY", "Food Activity onCreate Called");

        ArrayList<FoodAndDrink> foodAndDrinks = new ArrayList<>();
        foodAndDrinks.add(new FoodAndDrink("Phở Hà Nội", "Món ăn nổi tiếng ở Hà Nội và toàn quốc", 50000, R.drawable.img));
        foodAndDrinks.add(new FoodAndDrink("Bún Bò Huế", "Món ăn nổi tiếng ở Huế và toàn quốc", 50000, R.drawable.img_1));
        foodAndDrinks.add(new FoodAndDrink("Mì Quảng", "Món ăn nổi tiếng toàn quốc", 50000, R.drawable.img_2));
        foodAndDrinks.add(new FoodAndDrink("Hủ Tíu Sài Gòn", "Món ăn nổi tiếng ở Sài Gòn và toàn quốc", 50000, R.drawable.img_3));
        FoodAndDrinkAdapter foodAndDrinkAdapter = new FoodAndDrinkAdapter(this, R.layout.food_drink_list, foodAndDrinks);
        ListView listView = findViewById(R.id.lvFood);
        listView.setAdapter(foodAndDrinkAdapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            selectedItemPosition = position;
            Toast.makeText(this, "Chọn: " + foodAndDrinkAdapter.getItem(position).getName(), LENGTH_SHORT).show();
        });

        findViewById(R.id.btnOrderFood).setOnClickListener(v -> {
            if (selectedItemPosition == -1) {
                Toast.makeText(this, "Vui lòng chọn một mục!", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent();
                intent.putExtra("Food", foodAndDrinkAdapter.getItem(selectedItemPosition).getName());
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v("FOOD_ACTIVITY", "Food Activity onStart Called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("FOOD_ACTIVITY", "Food Activity onDestroy Called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v("FOOD_ACTIVITY", "Food Activity onStop Called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("FOOD_ACTIVITY", "Food Activity onResume Called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v("FOOD_ACTIVITY", "Food Activity onRestart Called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v("FOOD_ACTIVITY", "Food Activity onPause Called");
    }
}