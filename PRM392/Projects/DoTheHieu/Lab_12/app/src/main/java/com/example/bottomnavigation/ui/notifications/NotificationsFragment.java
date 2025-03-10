package com.example.bottomnavigation.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bottomnavigation.databinding.FragmentHomeBinding;
import com.example.bottomnavigation.databinding.FragmentNotificationsBinding;
import com.example.bottomnavigation.viewmodel.SharedViewModel;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    private SharedViewModel sharedViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        sharedViewModel.getSharedData().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer data) {
                binding.textNotifications.setText(String.valueOf(data));
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}