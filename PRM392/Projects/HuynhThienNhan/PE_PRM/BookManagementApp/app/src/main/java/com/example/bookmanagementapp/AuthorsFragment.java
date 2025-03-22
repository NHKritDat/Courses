package com.example.bookmanagementapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bookmanagementapp.adapter.AuthorAdapter;
import com.example.bookmanagementapp.database.DatabaseHelper;
import com.example.bookmanagementapp.model.Author;
import java.util.ArrayList;
import java.util.List;

public class AuthorsFragment extends Fragment {
    private RecyclerView authorRecyclerView;
    private AuthorAdapter authorAdapter;
    private List<Author> authorList;
    private DatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_authors, container, false);

        dbHelper = new DatabaseHelper(getContext());
        authorRecyclerView = view.findViewById(R.id.authorRecyclerView);
        authorList = new ArrayList<>();
        authorAdapter = new AuthorAdapter(authorList);

        authorRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        authorRecyclerView.setAdapter(authorAdapter);

        loadAuthors();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadAuthors();
    }

    private void loadAuthors() {
        authorList.clear();
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
        }
        cursor.close();
        authorAdapter.notifyDataSetChanged();
    }
}