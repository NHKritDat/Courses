package com.example.pesu24.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pesu24.R;
import com.example.pesu24.database.DatabaseManager;
import com.example.pesu24.entities.TacGia;
import com.google.android.material.textfield.TextInputEditText;

public class AddTacGiaActivity extends AppCompatActivity {

    private TextInputEditText etTenTacGia, etEmail, etDiaChi, etDienThoai;
    private Button btnSaveTacGia;
    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tacgia);

        // Initialize database
        dbManager = new DatabaseManager(this);
        dbManager.open();

        // Initialize views
        etTenTacGia = findViewById(R.id.et_ten_tacgia);
        etEmail = findViewById(R.id.et_email);
        etDiaChi = findViewById(R.id.et_dia_chi);
        etDienThoai = findViewById(R.id.et_dien_thoai);
        btnSaveTacGia = findViewById(R.id.btn_save_tacgia);

        // Save button click handler
        btnSaveTacGia.setOnClickListener(view -> saveTacGia());
    }

    private void saveTacGia() {
        String tenTacGia = etTenTacGia.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String diaChi = etDiaChi.getText().toString().trim();
        String dienThoai = etDienThoai.getText().toString().trim();

        // Validate input
        if (tenTacGia.isEmpty() || email.isEmpty() || diaChi.isEmpty() || dienThoai.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create new TacGia object
        TacGia tacGia = new TacGia(tenTacGia, email, diaChi, dienThoai);

        // Save to database
        long result = dbManager.createTacGia(tacGia);

        if (result > 0) {
            Toast.makeText(this, "Thêm tác giả thành công", Toast.LENGTH_SHORT).show();
            finish(); // Close activity and go back
        } else {
            Toast.makeText(this, "Lỗi khi thêm tác giả", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbManager != null) {
            dbManager.close();
        }
    }
}
