package com.example.bookmanagementapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bookmanagementapp.database.DatabaseHelper;
import com.example.bookmanagementapp.model.Author;
import java.util.ArrayList;
import java.util.List;


public class AddBookActivity extends AppCompatActivity {
    private EditText titleEditText, dateEditText, typeEditText;
    private Spinner authorSpinner;
    private DatabaseHelper dbHelper;
    private List<Author> authorList;
    private ArrayAdapter<String> authorAdapter;
    private List<String> authorNames;
    private int selectedAuthorId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        dbHelper = new DatabaseHelper(this);
        titleEditText = findViewById(R.id.titleEditText);
        dateEditText = findViewById(R.id.dateEditText);
        typeEditText = findViewById(R.id.typeEditText);
        authorSpinner = findViewById(R.id.authorSpinner);

        // Khởi tạo danh sách tác giả
        authorList = new ArrayList<>();
        authorNames = new ArrayList<>();
        loadAuthors();

        // Thiết lập Spinner
        authorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, authorNames);
        authorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        authorSpinner.setAdapter(authorAdapter);

        // Xử lý sự kiện khi chọn tác giả
        authorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedAuthorId = authorList.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedAuthorId = -1;
            }
        });

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> saveBook());
    }

    private void loadAuthors() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Author", null);
        while (cursor.moveToNext()) {
            Author author = new Author();
            author.setId(cursor.getInt(cursor.getColumnIndex("Author_ID")));
            author.setName(cursor.getString(cursor.getColumnIndex("name")));
            author.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            author.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
            author.setAddress(cursor.getString(cursor.getColumnIndex("address")));
            authorList.add(author);
            authorNames.add(author.getName());
        }
        cursor.close();
        if (authorList.isEmpty()) {
            Toast.makeText(this, "No authors found. Please add an author first.", Toast.LENGTH_LONG).show();
        }
    }

    private void saveBook() {
        String title = titleEditText.getText().toString().trim();
        String date = dateEditText.getText().toString().trim();
        String type = typeEditText.getText().toString().trim();

        // Kiểm tra dữ liệu đầu vào
        if (title.isEmpty() || date.isEmpty() || type.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedAuthorId == -1) {
            Toast.makeText(this, "Please select an author", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("book_title", title);
        values.put("publication_date", date);
        values.put("type", type);
        values.put("Author_ID", selectedAuthorId);

        long result = db.insert("Book", null, values);
        if (result != -1) {
            Toast.makeText(this, "Book added successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to add book", Toast.LENGTH_SHORT).show();
        }
    }
}