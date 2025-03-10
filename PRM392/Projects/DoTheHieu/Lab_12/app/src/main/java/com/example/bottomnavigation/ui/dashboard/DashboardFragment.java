package com.example.bottomnavigation.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bottomnavigation.databinding.FragmentDashboardBinding;
import com.example.bottomnavigation.viewmodel.SharedViewModel;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    private SharedViewModel sharedViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        binding.btnUpNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedViewModel.incrementData();
            }
        });

        sharedViewModel.getSharedData().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer data) {
                binding.textDashboard.setText(String.valueOf(data));
            }
        });

        return root;
    }
}