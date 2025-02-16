package com.example.lab_1_2_3;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.lab_1_2_3.Lab1.Lab1clActivity;
import com.example.lab_1_2_3.Lab1.Lab1llaActivity;
import com.example.lab_1_2_3.Lab1.Lab1llbActivity;
import com.example.lab_1_2_3.Lab1.Lab1rlActivity;
import com.example.lab_1_2_3.Lab2.Lab2CalculatorActivity;
import com.example.lab_1_2_3.Lab2.Lab2RandomNumberActivity;
import com.example.lab_1_2_3.Lab2.Lab2SignInActivity;
import com.example.lab_1_2_3.Lab3.Lab3CustomListViewActivity;
import com.example.lab_1_2_3.Lab3.Lab3ListViewActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnLab1lla).setOnClickListener(v -> {
            Intent intent = new Intent(this, Lab1llaActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.btnLab1llb).setOnClickListener(v -> {
            Intent intent = new Intent(this, Lab1llbActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.btnLab1rl).setOnClickListener(v -> {
            Intent intent = new Intent(this, Lab1rlActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.btnLab1cl).setOnClickListener(v -> {
            Intent intent = new Intent(this, Lab1clActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btnLab2Cal).setOnClickListener(v -> {
            Intent intent = new Intent(this, Lab2CalculatorActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.btnLab2Ran).setOnClickListener(v -> {
            Intent intent = new Intent(this, Lab2RandomNumberActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.btnLab2Sign).setOnClickListener(v -> {
            Intent intent = new Intent(this, Lab2SignInActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btnLab3ListView).setOnClickListener(v -> {
            Intent intent = new Intent(this, Lab3ListViewActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.btnLab3clv).setOnClickListener(v -> {
            Intent intent = new Intent(this, Lab3CustomListViewActivity.class);
            startActivity(intent);
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}