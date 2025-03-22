package com.example.pesu24.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.pesu24.R;
import com.example.pesu24.activities.AddSachActivity;
import com.example.pesu24.adapters.SachAdapter;
import com.example.pesu24.database.DatabaseManager;
import com.example.pesu24.entities.Sach;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class SachFragment extends Fragment {
    private DatabaseManager dbManager;
    private ListView listView;
    private SachAdapter adapter;
    private FloatingActionButton fabAddSach;

    public SachFragment(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sach_list, container, false);
        listView = view.findViewById(R.id.list_sach);
        fabAddSach = view.findViewById(R.id.fab_add_sach);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadSachList();
        fabAddSach.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddSachActivity.class);
            startActivity(intent);
        });
    }

    private void loadSachList() {
        List<Sach> sachList = dbManager.getAllSach();
        adapter = new SachAdapter(requireContext(), sachList, dbManager);
        listView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadSachList(); // Refresh the list when returning to this fragment
    }
}