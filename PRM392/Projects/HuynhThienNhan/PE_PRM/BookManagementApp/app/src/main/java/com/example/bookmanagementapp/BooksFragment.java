package com.example.bookmanagementapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bookmanagementapp.adapter.BookAdapter;
import com.example.bookmanagementapp.database.DatabaseHelper;
import com.example.bookmanagementapp.model.Book;
import java.util.ArrayList;
import java.util.List;

public class BooksFragment extends Fragment implements BookAdapter.OnBookActionListener {
    private RecyclerView bookRecyclerView;
    private BookAdapter bookAdapter;
    private List<Book> bookList;
    private DatabaseHelper dbHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_books, container, false);

        dbHelper = new DatabaseHelper(getContext());
        bookRecyclerView = view.findViewById(R.id.bookRecyclerView);
        bookList = new ArrayList<>();
        bookAdapter = new BookAdapter(bookList, this, dbHelper);

        bookRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        bookRecyclerView.setAdapter(bookAdapter);

        loadBooks();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadBooks();
    }

    private void loadBooks() {
        bookList.clear();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Book", null);
        while (cursor.moveToNext()) {
            Book book = new Book();
            book.setId(cursor.getInt(cursor.getColumnIndex("Book_ID")));
            book.setTitle(cursor.getString(cursor.getColumnIndex("book_title")));
            book.setAuthorId(cursor.getInt(cursor.getColumnIndex("Author_ID")));
            bookList.add(book);
        }
        cursor.close();
        bookAdapter.notifyDataSetChanged();
    }
    @Override
    public void onUpdateBook(int bookId) {
        Intent intent = new Intent(getActivity(), UpdateBookActivity.class);
        intent.putExtra("book_id", bookId);
        startActivity(intent);
    }

    @Override
    public void onDeleteBook(int bookId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int result = db.delete("Book", "Book_ID = ?", new String[]{String.valueOf(bookId)});
        if (result > 0) {
            loadBooks();
        }
    }

    @Override
    public void onViewAuthorLocation(int authorId) {
        // Lấy địa chỉ của tác giả từ Author_ID
        String address = getAuthorAddress(authorId);
        if (address == null) {
            return;
        }

        // Tạo URI để tìm kiếm địa chỉ trên Google Maps
        String uri = "geo:0,0?q=" + Uri.encode(address);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        mapIntent.setPackage("com.google.android.apps.maps"); // Chỉ định ứng dụng Google Maps

        // Kiểm tra xem Google Maps có được cài đặt không
        if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            // Nếu không có Google Maps, mở trình duyệt
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/" + Uri.encode(address)));
            startActivity(browserIntent);
        }
    }

    private String getAuthorAddress(int authorId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT address FROM Author WHERE Author_ID = ?", new String[]{String.valueOf(authorId)});
        String address = null;
        if (cursor.moveToFirst()) {
            address = cursor.getString(cursor.getColumnIndex("address"));
        }
        cursor.close();
        if (address == null || address.isEmpty()) {
            // Hiển thị thông báo nếu không tìm thấy địa chỉ
            if (getActivity() != null) {
                android.widget.Toast.makeText(getActivity(), "Author address not found", android.widget.Toast.LENGTH_SHORT).show();
            }
            return null;
        }
        return address;
    }
}