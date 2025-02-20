package com.example.lab4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private String txtFood = null;
    private String txtDrink = null;
    private TextView result;

    private final ActivityResultLauncher<Intent> foodLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        txtFood = data.getStringExtra("Food");
                        updateResult();
                    }
                }
            });

    private final ActivityResultLauncher<Intent> drinkLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        txtDrink = data.getStringExtra("Drink");
                        updateResult();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Log.v("MAIN_ACTIVITY", "Main Activity onCreate Called");

        result = findViewById(R.id.tvResult);
        updateResult();

        findViewById(R.id.btnFood).setOnClickListener(v -> {
            Intent food = new Intent(this, FoodActivity.class);
            foodLauncher.launch(food);
        });

        findViewById(R.id.btnDrink).setOnClickListener(v -> {
            Intent drink = new Intent(this, DrinkActivity.class);
            drinkLauncher.launch(drink);
        });

        findViewById(R.id.btnOut).setOnClickListener(v -> System.exit(0));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void updateResult() {
        String txtResult = "";
        if (txtFood != null) txtResult += txtFood;
        if (txtDrink != null) {
            if (!txtResult.isEmpty()) txtResult += " - ";
            txtResult += txtDrink;
        }
        result.setText(txtResult);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v("MAIN_ACTIVITY", "Main Activity onStart Called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("MAIN_ACTIVITY", "Main Activity onDestroy Called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v("MAIN_ACTIVITY", "Main Activity onStop Called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("MAIN_ACTIVITY", "Main Activity onResume Called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v("MAIN_ACTIVITY", "Main Activity onRestart Called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v("MAIN_ACTIVITY", "Main Activity onPause Called");
    }
}