package com.example.sp25_nguyenhoangdat_net1720.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sp25_nguyenhoangdat_net1720.R;
import com.example.sp25_nguyenhoangdat_net1720.activities.AddBookActivity;
import com.example.sp25_nguyenhoangdat_net1720.adapters.BookAdapter;
import com.example.sp25_nguyenhoangdat_net1720.database.DatabaseManager;
import com.example.sp25_nguyenhoangdat_net1720.entities.Book;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class BookFragment extends Fragment {
    private DatabaseManager dbManager;
    private ListView listView;
    private BookAdapter adapter;
    private FloatingActionButton fabAddBook;

    public BookFragment(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book, container, false);
        listView = view.findViewById(R.id.list_book);
        fabAddBook = view.findViewById(R.id.fab_add_book);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadSachList();
        fabAddBook.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddBookActivity.class);
            startActivity(intent);
        });
    }

    private void loadSachList() {
        List<Book> bookList = dbManager.getAllBook();
        adapter = new BookAdapter(requireContext(), bookList, dbManager);
        listView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadSachList();
    }
}