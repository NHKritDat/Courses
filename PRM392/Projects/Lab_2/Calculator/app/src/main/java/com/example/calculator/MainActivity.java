package com.example.calculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    TextView number1;
    TextView number2;
    TextView result;

    Button cong;
    Button tru;
    Button nhan;
    Button chia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        number1 = findViewById(R.id.num1);
        number2 = findViewById(R.id.num2);
        result = findViewById(R.id.txxtResult);

        cong = findViewById(R.id.btnAdd);
        tru =  findViewById(R.id.btnAbs);
        nhan = findViewById(R.id.btnMul);
        chia = findViewById(R.id.btnDiv);

        cong.setOnClickListener(v -> calculate("+"));
        tru.setOnClickListener(v -> calculate("-"));
        nhan.setOnClickListener(v -> calculate("*"));
        chia.setOnClickListener(v -> calculate("/"));

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private float operator (float a, float b, String o) {
        switch (o) {
            case "+":
                return a + b;

            case "-":
                return a - b;

            case "*":
                return a * b;
            case "/":
                return a / b;
            default: return 0;
        }
    }

    private void calculate(String operation) {
        try {
            String num1Str = number1.getText().toString();
            String num2Str = number2.getText().toString();

            float num1 = Float.parseFloat(num1Str);
            float num2 = Float.parseFloat(num2Str);

            if(num2 == 0 && operation.equals("/") ) {
                result.setText("Số bị chia phải != 0");
                return;
            }
            float calculateResult = operator(num1, num2, operation);

            result.setText(String.valueOf(calculateResult));
        }catch (NumberFormatException e) {
            result.setText("Vui lòng nhập số hợp lệ");
        }
    }
}