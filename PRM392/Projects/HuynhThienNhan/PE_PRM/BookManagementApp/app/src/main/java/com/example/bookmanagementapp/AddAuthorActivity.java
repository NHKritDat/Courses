package com.example.bookmanagementapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bookmanagementapp.database.DatabaseHelper;
import com.example.bookmanagementapp.model.Author;

public class AddAuthorActivity extends AppCompatActivity {
    private EditText nameEditText, emailEditText, phoneEditText, addressEditText;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_author);

        dbHelper = new DatabaseHelper(this);
        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        addressEditText = findViewById(R.id.addressEditText);

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> saveAuthor());
    }

    private void saveAuthor() {
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();

        // Kiểm tra dữ liệu đầu vào
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo đối tượng Author
        Author author = new Author();
        author.setName(name);
        author.setEmail(email);
        author.setPhone(phone);
        author.setAddress(address);

        // Lưu vào cơ sở dữ liệu
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", author.getName());
        values.put("email", author.getEmail());
        values.put("phone", author.getPhone());
        values.put("address", author.getAddress());

        long result = db.insert("Author", null, values);
        if (result != -1) {
            Toast.makeText(this, "Author added successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to add author", Toast.LENGTH_SHORT).show();
        }
    }
}