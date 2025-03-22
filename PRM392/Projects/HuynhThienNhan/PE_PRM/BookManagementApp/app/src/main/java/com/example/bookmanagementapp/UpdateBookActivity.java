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

public class UpdateBookActivity extends AppCompatActivity {
    private EditText titleEditText, dateEditText, typeEditText;
    private Spinner authorSpinner;
    private DatabaseHelper dbHelper;
    private int bookId;
    private List<Author> authorList;
    private ArrayAdapter<String> authorAdapter;
    private List<String> authorNames;
    private int selectedAuthorId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book);

        dbHelper = new DatabaseHelper(this);
        titleEditText = findViewById(R.id.titleEditText);
        dateEditText = findViewById(R.id.dateEditText);
        typeEditText = findViewById(R.id.typeEditText);
        authorSpinner = findViewById(R.id.authorSpinner);

        // Lấy bookId từ Intent
        bookId = getIntent().getIntExtra("book_id", -1);
        if (bookId == -1) {
            Toast.makeText(this, "Invalid book ID", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

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

        loadBookData();

        Button updateButton = findViewById(R.id.updateButton);
        updateButton.setOnClickListener(v -> updateBook());
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
    }

    private void loadBookData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Book WHERE Book_ID = ?", new String[]{String.valueOf(bookId)});
        if (cursor.moveToFirst()) {
            titleEditText.setText(cursor.getString(cursor.getColumnIndex("book_title")));
            dateEditText.setText(cursor.getString(cursor.getColumnIndex("publication_date")));
            typeEditText.setText(cursor.getString(cursor.getColumnIndex("type")));
            int authorId = cursor.getInt(cursor.getColumnIndex("Author_ID"));

            // Tìm vị trí của tác giả trong Spinner
            for (int i = 0; i < authorList.size(); i++) {
                if (authorList.get(i).getId() == authorId) {
                    authorSpinner.setSelection(i);
                    selectedAuthorId = authorId;
                    break;
                }
            }
        } else {
            Toast.makeText(this, "Book not found", Toast.LENGTH_SHORT).show();
            finish();
        }
        cursor.close();
    }

    private void updateBook() {
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

        int result = db.update("Book", values, "Book_ID = ?", new String[]{String.valueOf(bookId)});
        if (result > 0) {
            Toast.makeText(this, "Book updated successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to update book", Toast.LENGTH_SHORT).show();
        }
    }
}

