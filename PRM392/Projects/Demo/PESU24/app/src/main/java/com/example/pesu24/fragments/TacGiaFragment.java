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
import com.example.pesu24.activities.AddTacGiaActivity;
import com.example.pesu24.adapters.TacGiaAdapter;
import com.example.pesu24.database.DatabaseManager;
import com.example.pesu24.entities.TacGia;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TacGiaFragment extends Fragment {
    private DatabaseManager dbManager;
    private ListView listView;
    private TacGiaAdapter adapter;
    private FloatingActionButton fabAddTacGia;

    public TacGiaFragment(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tacgia_list, container, false);
        listView = view.findViewById(R.id.list_tacgia);
        fabAddTacGia = view.findViewById(R.id.fab_add_tacgia);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadTacGiaList();
        fabAddTacGia.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddTacGiaActivity.class);
            startActivity(intent);
        });
    }

    private void loadTacGiaList() {
        List<TacGia> tacGiaList = dbManager.getAllTacGia();
        adapter = new TacGiaAdapter(requireContext(), tacGiaList);
        listView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadTacGiaList(); // Refresh the list when returning to this fragment
    }
}