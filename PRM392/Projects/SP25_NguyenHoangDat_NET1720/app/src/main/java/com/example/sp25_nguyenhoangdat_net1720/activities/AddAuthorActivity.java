package com.example.sp25_nguyenhoangdat_net1720.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sp25_nguyenhoangdat_net1720.R;
import com.example.sp25_nguyenhoangdat_net1720.database.DatabaseManager;
import com.example.sp25_nguyenhoangdat_net1720.entities.Author;
import com.google.android.material.textfield.TextInputEditText;

public class AddAuthorActivity extends AppCompatActivity {

    private TextInputEditText etName, etEmail, etAddress, etPhone;
    private Button btnSaveAuthor;
    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_author);
        // Initialize database
        dbManager = new DatabaseManager(this);
        dbManager.open();

        // Initialize views
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etAddress = findViewById(R.id.et_address);
        etPhone = findViewById(R.id.et_phone);
        btnSaveAuthor = findViewById(R.id.btn_save_author);

        // Save button click handler
        btnSaveAuthor.setOnClickListener(view -> saveTacGia());
    }

    private void saveTacGia() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        // Validate input
        if (name.isEmpty() || email.isEmpty() || address.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Fill all information", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create new TacGia object
        Author tacGia = new Author(name, email, address, phone);

        // Save to database
        long result = dbManager.createAuthor(tacGia);

        if (result > 0) {
            Toast.makeText(this, "Add Success", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error add", Toast.LENGTH_SHORT).show();
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