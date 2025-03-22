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

public class EditBookActivity extends AppCompatActivity {

    private TextInputEditText etBookTitle, etPublicationDate, etType;
    private Spinner spinnerAuthor;
    private Button btnUpdateBook;
    private DatabaseManager dbManager;

    private List<Author> authorList;
    private Map<String, Integer> authorMap; // Map to store tacGia name -> ID
    private int selectedAuthorId = -1;
    private int bookId = -1;
    private Book currentBook;
    private Button btnDeleteBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);
        btnDeleteBook = findViewById(R.id.btn_delete_book);

        // Initialize database
        dbManager = new DatabaseManager(this);
        dbManager.open();

        // Initialize views
        etBookTitle = findViewById(R.id.et_book_title);
        etPublicationDate = findViewById(R.id.et_publication_date);
        etType = findViewById(R.id.et_type);
        spinnerAuthor = findViewById(R.id.spinner_author);
        btnUpdateBook = findViewById(R.id.btn_update_book);

        // Get book ID from intent
        bookId = getIntent().getIntExtra("book_id", -1);
        if (bookId == -1) {
            Toast.makeText(this, "error: Book Not Found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Load book data
        currentBook = dbManager.getBook(bookId);
        if (currentBook == null) {
            Toast.makeText(this, "Error: Book's information not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Fill form with current data
        etBookTitle.setText(currentBook.getBookTitle());
        etPublicationDate.setText(currentBook.getPublishDate());
        etType.setText(currentBook.getType());
        selectedAuthorId = currentBook.getAuthorId();

        // Load authors into spinner
        loadAuthorSpinner();

        // Update button click handler
        btnUpdateBook.setOnClickListener(view -> updateBook());
        btnDeleteBook.setOnClickListener(view -> deleteBook());
    }

    private void loadAuthorSpinner() {
        authorList = dbManager.getAllAuthor();
        authorMap = new HashMap<>();

        List<String> authorNames = new ArrayList<>();
        int selectedIndex = 0;

        for (int i = 0; i < authorList.size(); i++) {
            Author author = authorList.get(i);
            String name = author.getName();
            authorNames.add(name);
            authorMap.put(name, author.getAuthorId());

            // Find the index of the current author
            if (author.getAuthorId() == selectedAuthorId) {
                selectedIndex = i;
            }
        }

        // Create adapter for spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, authorNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerAuthor.setAdapter(adapter);

        // Set the current author as selected
        if (authorNames.size() > 0) {
            spinnerAuthor.setSelection(selectedIndex);
        }

        spinnerAuthor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedName = parent.getItemAtPosition(position).toString();
                selectedAuthorId = authorMap.get(selectedName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Keep the current selected author ID
            }
        });
    }

    private void updateBook() {
        String bookTitle = etBookTitle.getText().toString().trim();
        String publicationDate = etPublicationDate.getText().toString().trim();
        String type = etType.getText().toString().trim();

        // Validate input
        if (bookTitle.isEmpty() || publicationDate.isEmpty() || type.isEmpty() || selectedAuthorId == -1) {
            Toast.makeText(this, "Fill all information", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update Sach object
        currentBook.setBookTitle(bookTitle);
        currentBook.setPublishDate(publicationDate);
        currentBook.setType(type);
        currentBook.setAuthorId(selectedAuthorId);

        // Save to database
        int result = dbManager.updateBook(currentBook);

        if (result > 0) {
            Toast.makeText(this, "Update success", Toast.LENGTH_SHORT).show();
            finish(); // Close activity and go back
        } else {
            Toast.makeText(this, "update error", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteBook() {
        // Show confirmation dialog
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Remove book")
                .setMessage("Are you sure you want to delete this book?")
                .setPositiveButton("Remove", (dialog, which) -> {
                    // Delete the book
                    dbManager.deleteBook(bookId);
                    Toast.makeText(this, "Remove success", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("Cancel", null)
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