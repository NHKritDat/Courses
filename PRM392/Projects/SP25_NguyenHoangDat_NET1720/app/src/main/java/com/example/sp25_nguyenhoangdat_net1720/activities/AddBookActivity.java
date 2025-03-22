package com.example.sp25_nguyenhoangdat_net1720.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sp25_nguyenhoangdat_net1720.R;
import com.example.sp25_nguyenhoangdat_net1720.database.DatabaseManager;
import com.example.sp25_nguyenhoangdat_net1720.entities.Author;
import com.example.sp25_nguyenhoangdat_net1720.entities.Book;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddBookActivity extends AppCompatActivity {

    private TextInputEditText etBookTitle, etPublicationDate, etType;
    private Spinner spinnerAuthor;
    private Button btnSaveBook;
    private DatabaseManager dbManager;

    private List<Author> authorList;
    private Map<String, Integer> authorMap; // Map to store tacGia name -> ID
    private int selectedAuthorId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        // Initialize database
        dbManager = new DatabaseManager(this);
        dbManager.open();

        // Initialize views
        etBookTitle = findViewById(R.id.et_book_title);
        etPublicationDate = findViewById(R.id.et_publication_date);
        etType = findViewById(R.id.et_type);
        spinnerAuthor = findViewById(R.id.spinner_author);
        btnSaveBook = findViewById(R.id.btn_save_book);

        // Load authors into spinner
        loadAuthorSpinner();

        // Save button click handler
        btnSaveBook.setOnClickListener(view -> saveBook());
    }

    private void loadAuthorSpinner() {
        authorList = dbManager.getAllAuthor();
        authorMap = new HashMap<>();

        List<String> authorNames = new ArrayList<>();

        for (Author author : authorList) {
            String name = author.getName();
            authorNames.add(name);
            authorMap.put(name, author.getAuthorId());
        }

        // Create adapter for spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, authorNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerAuthor.setAdapter(adapter);

        spinnerAuthor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedName = parent.getItemAtPosition(position).toString();
                selectedAuthorId = authorMap.get(selectedName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedAuthorId = -1;
            }
        });
    }

    private void saveBook() {
        String bookTitle = etBookTitle.getText().toString().trim();
        String publicationDate = etPublicationDate.getText().toString().trim();
        String type = etType.getText().toString().trim();

        if (bookTitle.isEmpty() || publicationDate.isEmpty() || type.isEmpty() || selectedAuthorId == -1) {
            Toast.makeText(this, "Fill all information", Toast.LENGTH_SHORT).show();
            return;
        }

        Book book = new Book(bookTitle, publicationDate, type, selectedAuthorId);

        long result = dbManager.createBook(book);

        if (result > 0) {
            Toast.makeText(this, "Add book successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error add book", Toast.LENGTH_SHORT).show();
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