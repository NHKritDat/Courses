package com.example.randomnumber;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView min;
    TextView max;
    TextView result;
    Button random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        min = findViewById(R.id.minText);
        max = findViewById(R.id.maxText);
        result = findViewById(R.id.resultText);
        random = findViewById(R.id.btnRandom);

        random.setOnClickListener(v -> {
            try {
                int minValue = Integer.parseInt(min.getText().toString());
                int maxValue = Integer.parseInt(max.getText().toString());

                if (minValue > maxValue){
                    result.setText("Min nhỏ hơn Max");
                    return;
                }

                Random random = new Random();
                int number = random.nextInt((maxValue - minValue) +1) + minValue;
                result.setText(String.valueOf(number));
            }catch (NumberFormatException e){
                result.setText("Try again");
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}