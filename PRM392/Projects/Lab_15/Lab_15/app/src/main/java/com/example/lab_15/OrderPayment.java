package com.example.lab_15;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab_15.Api.CreateOrder;

import org.json.JSONObject;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class OrderPayment extends AppCompatActivity {
    TextView tvSoluong;
    TextView tong;
    Button thanhtoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_payment);

        tvSoluong = findViewById(R.id.textViewSoluong);
        tong = findViewById(R.id.textViewTongTien);
        thanhtoan = findViewById(R.id.buttonThanhToan);

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // ZaloPay SDK Init
        ZaloPaySDK.init(2553, Environment.SANDBOX);

        Intent intent = getIntent();
        tvSoluong.setText(intent.getStringExtra("soluong"));
        double total = intent.getDoubleExtra("total", 0);
        String totalString = String.format("%.0f", total);
        tong.setText(Double.toString(total));

        thanhtoan.setOnClickListener(v -> {
            CreateOrder orderApi = new CreateOrder();

            try {
                JSONObject data = orderApi.createOrder(totalString);
                String code = data.getString("return_code");
                if (code.equals("1")) {
                    String token = data.getString("zp_trans_token");
                    ZaloPaySDK.getInstance().payOrder(OrderPayment.this, token, "demozpdk://app", new PayOrderListener() {
                        @Override
                        public void onPaymentSucceeded(String s, String s1, String s2) {
                            Intent intent1 = new Intent(OrderPayment.this, PaymentNotification.class);
                            intent1.putExtra("result", "thanh toan thanh cong");
                            startActivity(intent1);
                        }

                        @Override
                        public void onPaymentCanceled(String s, String s1) {
                            Intent intent1 = new Intent(OrderPayment.this, PaymentNotification.class);
                            intent1.putExtra("result", "Huy thanh toan");
                            startActivity(intent1);
                        }

                        @Override
                        public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {
                            Intent intent1 = new Intent(OrderPayment.this, PaymentNotification.class);
                            intent1.putExtra("result", "Loi thanh toan");
                            startActivity(intent1);
                        }
                    });
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }
}