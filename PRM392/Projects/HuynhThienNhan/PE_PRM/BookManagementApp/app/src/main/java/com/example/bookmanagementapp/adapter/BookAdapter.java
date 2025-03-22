package com.example.bookmanagementapp.adapter;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bookmanagementapp.MapActivity;
import com.example.bookmanagementapp.R;
import com.example.bookmanagementapp.UpdateBookActivity;
import com.example.bookmanagementapp.database.DatabaseHelper;
import com.example.bookmanagementapp.model.Book;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private List<Book> bookList;
    private OnBookActionListener listener;
    private DatabaseHelper dbHelper;

    public interface OnBookActionListener {
        void onUpdateBook(int bookId);
        void onDeleteBook(int bookId);
        void onViewAuthorLocation(int authorId);
    }

    public BookAdapter(List<Book> bookList, OnBookActionListener listener, DatabaseHelper dbHelper) {
        this.bookList = bookList;
        this.listener = listener;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.titleTextView.setText(book.getTitle());

        // Lấy tên tác giả từ Author_ID
        String authorName = getAuthorName(book.getAuthorId());
        holder.authorTextView.setText("Author: " + (authorName != null ? authorName : "Unknown"));

        // Xử lý sự kiện nhấn nút Update
        holder.updateButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onUpdateBook(book.getId());
            }
        });

        // Xử lý sự kiện nhấn nút Delete
        holder.deleteButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onDeleteBook(book.getId());
            }
        });

        // Xử lý sự kiện nhấn nút View Author Location
        holder.mapButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onViewAuthorLocation(book.getAuthorId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    private String getAuthorName(int authorId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name FROM Author WHERE Author_ID = ?", new String[]{String.valueOf(authorId)});
        String authorName = null;
        if (cursor.moveToFirst()) {
            authorName = cursor.getString(cursor.getColumnIndex("name"));
        }
        cursor.close();
        return authorName;
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, authorTextView;
        Button updateButton, deleteButton, mapButton;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            authorTextView = itemView.findViewById(R.id.authorTextView);
            updateButton = itemView.findViewById(R.id.updateButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            mapButton = itemView.findViewById(R.id.mapButton);
        }
    }
}