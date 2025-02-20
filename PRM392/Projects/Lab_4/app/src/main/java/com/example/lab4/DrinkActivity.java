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

public class DrinkActivity extends AppCompatActivity {
    private int selectedItemPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_drink);
        Log.v("DRINK_ACTIVITY", "Drink Activity onCreate Called");

        ArrayList<FoodAndDrink> foodAndDrinks = new ArrayList<>();
        foodAndDrinks.add(new FoodAndDrink("Pepsi", "Thức uống có ga nổi tiếng thế giới", 50000, R.drawable.img_4));
        foodAndDrinks.add(new FoodAndDrink("Heineken", "Thức uống có cồn nổi tiếng", 50000, R.drawable.img_5));
        foodAndDrinks.add(new FoodAndDrink("Tiger", "Thức uống có cồn nổi tiếng", 50000, R.drawable.img_6));
        foodAndDrinks.add(new FoodAndDrink("Sài Gòn Đỏ", "Thức uống có cồn của Việt Nam", 50000, R.drawable.img_7));
        FoodAndDrinkAdapter foodAndDrinkAdapter = new FoodAndDrinkAdapter(this, R.layout.food_drink_list, foodAndDrinks);
        ListView listView = findViewById(R.id.lvDrink);
        listView.setAdapter(foodAndDrinkAdapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            selectedItemPosition = position;
            Toast.makeText(this, "Chọn: " + foodAndDrinkAdapter.getItem(position).getName(), LENGTH_SHORT).show();
        });

        findViewById(R.id.btnOrderDrink).setOnClickListener(v -> {
            if (selectedItemPosition == -1) {
                Toast.makeText(this, "Vui lòng chọn một mục!", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent();
                intent.putExtra("Drink", foodAndDrinkAdapter.getItem(selectedItemPosition).getName());
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
        Log.v("DRINK_ACTIVITY", "Drink Activity onStart Called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("DRINK_ACTIVITY", "Drink Activity onDestroy Called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v("DRINK_ACTIVITY", "Drink Activity onStop Called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("DRINK_ACTIVITY", "Drink Activity onResume Called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v("DRINK_ACTIVITY", "Drink Activity onRestart Called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v("DRINK_ACTIVITY", "Drink Activity onPause Called");
    }
}