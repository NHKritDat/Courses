package com.example.sp25_nguyenhoangdat_net1720.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sp25_nguyenhoangdat_net1720.R;
import com.example.sp25_nguyenhoangdat_net1720.database.DatabaseManager;
import com.example.sp25_nguyenhoangdat_net1720.entities.Author;
import com.google.android.material.textfield.TextInputEditText;

public class EditAuthorActivity extends AppCompatActivity {
    private TextInputEditText etName, etEmail, etAddress, etPhone;
    private Button btnUpdateAuthor, btnDeleteAuthor;
    private DatabaseManager dbManager;
    private Author currentAuthor;
    private int authorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_author);
        // Initialize database
        dbManager = new DatabaseManager(this);
        dbManager.open();

        // Initialize views
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etAddress = findViewById(R.id.et_address);
        etPhone = findViewById(R.id.et_phone);
        btnUpdateAuthor = findViewById(R.id.btn_update_author);
        btnDeleteAuthor = findViewById(R.id.btn_delete_author);

        authorId = getIntent().getIntExtra("author_id", -1);

        if (authorId == -1) {
            Toast.makeText(this, "Error: Author not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadAuthorData();

        // Set button click listeners
        btnUpdateAuthor.setOnClickListener(view -> updateAuthor());
        btnDeleteAuthor.setOnClickListener(view -> deleteAuthor());
    }

    private void loadAuthorData() {
        currentAuthor = dbManager.getAuthor(authorId);

        if (currentAuthor != null) {
            etName.setText(currentAuthor.getName());
            etEmail.setText(currentAuthor.getEmail());
            etAddress.setText(currentAuthor.getAddress());
            etPhone.setText(currentAuthor.getPhone());
        } else {
            Toast.makeText(this, "Author information not found", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void updateAuthor() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        // Validate input
        if (name.isEmpty() || email.isEmpty() || address.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Fill all information", Toast.LENGTH_SHORT).show();
            return;
        }

        currentAuthor.setName(name);
        currentAuthor.setEmail(email);
        currentAuthor.setAddress(address);
        currentAuthor.setPhone(phone);

        // Save to database
        int result = dbManager.updateAuthor(currentAuthor);

        if (result > 0) {
            Toast.makeText(this, "Update successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "error update", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteAuthor() {
        // Show confirmation dialog
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Remove author")
                .setMessage("Are you sure you want to delete this author?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    // Check if any books are associated with this author
                    if (dbManager.getBooksByAuthor(authorId).size() > 0) {
                        // Show another confirmation for deleting books
                        new androidx.appcompat.app.AlertDialog.Builder(this)
                                .setTitle("Warning")
                                .setMessage("Author has books associated with them. Deleting the author will also delete all associated books. Do you want to continue?")
                                .setPositiveButton("Continue remove?", (innerDialog, innerWhich) -> {
                                    performDelete();
                                })
                                .setNegativeButton("Cancel", null)
                                .show();
                    } else {
                        // No books, proceed with delete
                        performDelete();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void performDelete() {
        // Delete all books by this author first
        dbManager.deleteBookByAuthorId(authorId);

        // Then delete the author
        dbManager.deleteAuthor(authorId);
        Toast.makeText(this, "Remove author success", Toast.LENGTH_SHORT).show();
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