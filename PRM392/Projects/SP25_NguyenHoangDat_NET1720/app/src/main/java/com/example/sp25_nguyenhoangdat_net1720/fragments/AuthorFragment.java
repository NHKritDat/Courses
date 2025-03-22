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
import com.example.sp25_nguyenhoangdat_net1720.activities.AddAuthorActivity;
import com.example.sp25_nguyenhoangdat_net1720.adapters.AuthorAdapter;
import com.example.sp25_nguyenhoangdat_net1720.database.DatabaseManager;
import com.example.sp25_nguyenhoangdat_net1720.entities.Author;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AuthorFragment extends Fragment {
    private DatabaseManager dbManager;
    private ListView listView;
    private AuthorAdapter adapter;
    private FloatingActionButton fabAddAuthor;

    public AuthorFragment(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_author, container, false);
        listView = view.findViewById(R.id.list_author);
        fabAddAuthor = view.findViewById(R.id.fab_add_author);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadAuthorList();
        fabAddAuthor.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddAuthorActivity.class);
            startActivity(intent);
        });
    }

    private void loadAuthorList() {
        List<Author> authorList = dbManager.getAllAuthor();
        adapter = new AuthorAdapter(requireContext(), authorList);
        listView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadAuthorList();
    }
}