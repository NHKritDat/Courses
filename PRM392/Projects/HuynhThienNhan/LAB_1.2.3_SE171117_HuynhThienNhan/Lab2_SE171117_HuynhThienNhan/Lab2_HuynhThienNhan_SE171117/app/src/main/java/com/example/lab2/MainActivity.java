package com.example.lab2;

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
    TextView randomNumber;
    Button btnRandom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        min = (TextView) findViewById(R.id.editTextTextMin);
        max = (TextView) findViewById(R.id.editTextTextMax);
        randomNumber = (TextView) findViewById(R.id.textViewRandomNumber);
        btnRandom = (Button) findViewById(R.id.buttonButtonRandom);

        btnRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int minValue = Integer.parseInt(min.getText().toString());
                    int maxValue = Integer.parseInt(max.getText().toString());

                    if (minValue > maxValue){
                        randomNumber.setText("Min nhỏ hơn Max");
                        return;
                    }

                    Random random = new Random();
                    int number = random.nextInt((maxValue - minValue) +1) + minValue;
                    randomNumber.setText(String.valueOf(number));
                }catch (NumberFormatException e){
                    randomNumber.setText("Try again");
                }
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}