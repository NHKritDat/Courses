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

public class EditSachActivity extends AppCompatActivity {

    private TextInputEditText etTenSach, etNgayXB, etTheLoai;
    private Spinner spinnerTacGia;
    private Button btnUpdateSach;
    private DatabaseManager dbManager;

    private List<TacGia> tacGiaList;
    private Map<String, Integer> tacGiaMap; // Map to store tacGia name -> ID
    private int selectedTacGiaId = -1;
    private int sachId = -1;
    private Sach currentSach;
    private Button btnDeleteSach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sach);

        btnDeleteSach = findViewById(R.id.btn_delete_sach);

        // Initialize database
        dbManager = new DatabaseManager(this);
        dbManager.open();

        // Initialize views
        etTenSach = findViewById(R.id.et_ten_sach);
        etNgayXB = findViewById(R.id.et_ngay_xb);
        etTheLoai = findViewById(R.id.et_the_loai);
        spinnerTacGia = findViewById(R.id.spinner_tac_gia);
        btnUpdateSach = findViewById(R.id.btn_update_sach);

        // Get book ID from intent
        sachId = getIntent().getIntExtra("sach_id", -1);
        if (sachId == -1) {
            Toast.makeText(this, "Lỗi: Không tìm thấy thông tin sách", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Load book data
        currentSach = dbManager.getSach(sachId);
        if (currentSach == null) {
            Toast.makeText(this, "Lỗi: Không tìm thấy thông tin sách", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Fill form with current data
        etTenSach.setText(currentSach.getTenSach());
        etNgayXB.setText(currentSach.getNgayXB());
        etTheLoai.setText(currentSach.getTheLoai());
        selectedTacGiaId = currentSach.getIdTacGia();

        // Load authors into spinner
        loadTacGiaSpinner();

        // Update button click handler
        btnUpdateSach.setOnClickListener(view -> updateSach());
        btnDeleteSach.setOnClickListener(view -> deleteSach());
    }

    private void loadTacGiaSpinner() {
        tacGiaList = dbManager.getAllTacGia();
        tacGiaMap = new HashMap<>();

        List<String> tacGiaNames = new ArrayList<>();
        int selectedIndex = 0;

        for (int i = 0; i < tacGiaList.size(); i++) {
            TacGia tacGia = tacGiaList.get(i);
            String name = tacGia.getTenTacGia();
            tacGiaNames.add(name);
            tacGiaMap.put(name, tacGia.getId());

            // Find the index of the current author
            if (tacGia.getId() == selectedTacGiaId) {
                selectedIndex = i;
            }
        }

        // Create adapter for spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, tacGiaNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerTacGia.setAdapter(adapter);

        // Set the current author as selected
        if (tacGiaNames.size() > 0) {
            spinnerTacGia.setSelection(selectedIndex);
        }

        spinnerTacGia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedName = parent.getItemAtPosition(position).toString();
                selectedTacGiaId = tacGiaMap.get(selectedName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Keep the current selected author ID
            }
        });
    }

    private void updateSach() {
        String tenSach = etTenSach.getText().toString().trim();
        String ngayXB = etNgayXB.getText().toString().trim();
        String theLoai = etTheLoai.getText().toString().trim();

        // Validate input
        if (tenSach.isEmpty() || ngayXB.isEmpty() || theLoai.isEmpty() || selectedTacGiaId == -1) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update Sach object
        currentSach.setTenSach(tenSach);
        currentSach.setNgayXB(ngayXB);
        currentSach.setTheLoai(theLoai);
        currentSach.setIdTacGia(selectedTacGiaId);

        // Save to database
        int result = dbManager.updateSach(currentSach);

        if (result > 0) {
            Toast.makeText(this, "Cập nhật sách thành công", Toast.LENGTH_SHORT).show();
            finish(); // Close activity and go back
        } else {
            Toast.makeText(this, "Lỗi khi cập nhật sách", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteSach() {
        // Show confirmation dialog
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Xóa sách")
                .setMessage("Bạn có chắc chắn muốn xóa sách này?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    // Delete the book
                    dbManager.deleteSach(sachId);
                    Toast.makeText(this, "Đã xóa sách", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbManager != null) {
            dbManager.close();
        }
    }
}