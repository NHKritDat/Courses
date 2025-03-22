package com.example.pesu24.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pesu24.R;
import com.example.pesu24.database.DatabaseManager;
import com.example.pesu24.entities.Sach;
import com.example.pesu24.entities.TacGia;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddSachActivity extends AppCompatActivity {

    private TextInputEditText etTenSach, etNgayXB, etTheLoai;
    private Spinner spinnerTacGia;
    private Button btnSaveSach;
    private DatabaseManager dbManager;

    private List<TacGia> tacGiaList;
    private Map<String, Integer> tacGiaMap; // Map to store tacGia name -> ID
    private int selectedTacGiaId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sach);

        // Initialize database
        dbManager = new DatabaseManager(this);
        dbManager.open();

        // Initialize views
        etTenSach = findViewById(R.id.et_ten_sach);
        etNgayXB = findViewById(R.id.et_ngay_xb);
        etTheLoai = findViewById(R.id.et_the_loai);
        spinnerTacGia = findViewById(R.id.spinner_tac_gia);
        btnSaveSach = findViewById(R.id.btn_save_sach);

        // Load authors into spinner
        loadTacGiaSpinner();

        // Save button click handler
        btnSaveSach.setOnClickListener(view -> saveSach());
    }

    private void loadTacGiaSpinner() {
        tacGiaList = dbManager.getAllTacGia();
        tacGiaMap = new HashMap<>();

        List<String> tacGiaNames = new ArrayList<>();

        for (TacGia tacGia : tacGiaList) {
            String name = tacGia.getTenTacGia();
            tacGiaNames.add(name);
            tacGiaMap.put(name, tacGia.getId());
        }

        // Create adapter for spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, tacGiaNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerTacGia.setAdapter(adapter);

        spinnerTacGia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedName = parent.getItemAtPosition(position).toString();
                selectedTacGiaId = tacGiaMap.get(selectedName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedTacGiaId = -1;
            }
        });
    }

    private void saveSach() {
        String tenSach = etTenSach.getText().toString().trim();
        String ngayXB = etNgayXB.getText().toString().trim();
        String theLoai = etTheLoai.getText().toString().trim();

        // Validate input
        if (tenSach.isEmpty() || ngayXB.isEmpty() || theLoai.isEmpty() || selectedTacGiaId == -1) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create new Sach object
        Sach sach = new Sach(tenSach, ngayXB, theLoai, selectedTacGiaId);

        // Save to database
        long result = dbManager.createSach(sach);

        if (result > 0) {
            Toast.makeText(this, "Thêm sách thành công", Toast.LENGTH_SHORT).show();
            finish(); // Close activity and go back
        } else {
            Toast.makeText(this, "Lỗi khi thêm sách", Toast.LENGTH_SHORT).show();
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