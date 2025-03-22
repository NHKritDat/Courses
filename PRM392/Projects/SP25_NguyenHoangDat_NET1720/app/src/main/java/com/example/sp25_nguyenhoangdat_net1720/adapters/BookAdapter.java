package com.example.sp25_nguyenhoangdat_net1720.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.sp25_nguyenhoangdat_net1720.R;
import com.example.sp25_nguyenhoangdat_net1720.activities.EditBookActivity;
import com.example.sp25_nguyenhoangdat_net1720.database.DatabaseManager;
import com.example.sp25_nguyenhoangdat_net1720.entities.Author;
import com.example.sp25_nguyenhoangdat_net1720.entities.Book;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {
    private Context context;
    private List<Book> bookList;
    private DatabaseManager dbManager;

    public BookAdapter(@NonNull Context context, List<Book> bookList, DatabaseManager dbManager) {
        super(context, 0, bookList);
        this.context = context;
        this.bookList = bookList;
        this.dbManager = dbManager;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);
        }

        Book book = bookList.get(position);

        // Get author information
        Author author = dbManager.getAuthor(book.getAuthorId());

        TextView tvBookTitle = listItem.findViewById(R.id.tv_book_title);
        TextView tvPublicationDate = listItem.findViewById(R.id.tv_publication_date);
        TextView tvType = listItem.findViewById(R.id.tv_type);
        TextView tvAuthor = listItem.findViewById(R.id.tv_author);

        tvBookTitle.setText(book.getBookTitle());
        tvPublicationDate.setText("Publication Date: " + book.getPublishDate());
        tvType.setText("Type: " + book.getType());
        tvAuthor.setText("Author: " + (author != null ? author.getName() : "Not specified"));

        // Set click listener for editing
        listItem.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditBookActivity.class);
            intent.putExtra("book_id", book.getBookId());
            context.startActivity(intent);
        });

        return listItem;
    }
}
