package com.example.pesu24.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pesu24.R;
import com.example.pesu24.database.DatabaseManager;
import com.example.pesu24.entities.TacGia;
import com.google.android.material.textfield.TextInputEditText;

public class EditTacGiaActivity extends AppCompatActivity {

    private TextInputEditText etTenTacGia, etEmail, etDiaChi, etDienThoai;
    private Button btnUpdateTacGia, btnDeleteTacGia;
    private DatabaseManager dbManager;
    private TacGia currentTacGia;
    private int tacGiaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tacgia);

        // Initialize database
        dbManager = new DatabaseManager(this);
        dbManager.open();

        // Initialize views
        etTenTacGia = findViewById(R.id.et_ten_tacgia);
        etEmail = findViewById(R.id.et_email);
        etDiaChi = findViewById(R.id.et_dia_chi);
        etDienThoai = findViewById(R.id.et_dien_thoai);
        btnUpdateTacGia = findViewById(R.id.btn_update_tacgia);
        btnDeleteTacGia = findViewById(R.id.btn_delete_tacgia);

        // Get tacgia id from intent
        tacGiaId = getIntent().getIntExtra("tacgia_id", -1);

        if (tacGiaId == -1) {
            Toast.makeText(this, "Lỗi: Không tìm thấy tác giả", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Load tacgia data
        loadTacGiaData();

        // Set button click listeners
        btnUpdateTacGia.setOnClickListener(view -> updateTacGia());
        btnDeleteTacGia.setOnClickListener(view -> deleteTacGia());
    }

    private void loadTacGiaData() {
        currentTacGia = dbManager.getTacGia(tacGiaId);

        if (currentTacGia != null) {
            etTenTacGia.setText(currentTacGia.getTenTacGia());
            etEmail.setText(currentTacGia.getEmail());
            etDiaChi.setText(currentTacGia.getDiaChi());
            etDienThoai.setText(currentTacGia.getDienThoai());
        } else {
            Toast.makeText(this, "Không thể tải thông tin tác giả", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void updateTacGia() {
        String tenTacGia = etTenTacGia.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String diaChi = etDiaChi.getText().toString().trim();
        String dienThoai = etDienThoai.getText().toString().trim();

        // Validate input
        if (tenTacGia.isEmpty() || email.isEmpty() || diaChi.isEmpty() || dienThoai.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update TacGia object
        currentTacGia.setTenTacGia(tenTacGia);
        currentTacGia.setEmail(email);
        currentTacGia.setDiaChi(diaChi);
        currentTacGia.setDienThoai(dienThoai);

        // Save to database
        int result = dbManager.updateTacGia(currentTacGia);

        if (result > 0) {
            Toast.makeText(this, "Cập nhật tác giả thành công", Toast.LENGTH_SHORT).show();
            finish(); // Close activity and go back
        } else {
            Toast.makeText(this, "Lỗi khi cập nhật tác giả", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteTacGia() {
        // Show confirmation dialog
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Xóa tác giả")
                .setMessage("Bạn có chắc chắn muốn xóa tác giả này? Lưu ý: Tất cả sách của tác giả này cũng sẽ bị xóa.")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    // Check if any books are associated with this author
                    if (dbManager.getSachByTacGiaId(tacGiaId).size() > 0) {
                        // Show another confirmation for deleting books
                        new androidx.appcompat.app.AlertDialog.Builder(this)
                                .setTitle("Cảnh báo")
                                .setMessage("Tác giả này có sách liên kết. Xóa tác giả sẽ xóa tất cả sách của họ. Bạn có muốn tiếp tục?")
                                .setPositiveButton("Tiếp tục xóa", (innerDialog, innerWhich) -> {
                                    performDelete();
                                })
                                .setNegativeButton("Hủy", null)
                                .show();
                    } else {
                        // No books, proceed with delete
                        performDelete();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void performDelete() {
        // Delete all books by this author first
        dbManager.deleteSachByTacGiaId(tacGiaId);

        // Then delete the author
        dbManager.deleteTacGia(tacGiaId);
        Toast.makeText(this, "Đã xóa tác giả", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbManager != null) {
            dbManager.close();
        }
    }
}