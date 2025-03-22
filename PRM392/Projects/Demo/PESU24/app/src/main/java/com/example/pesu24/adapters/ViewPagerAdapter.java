package com.example.pesu24.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.pesu24.database.DatabaseManager;
import com.example.pesu24.fragments.SachFragment;
import com.example.pesu24.fragments.TacGiaFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private final DatabaseManager dbManager;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, DatabaseManager dbManager) {
        super(fragmentActivity);
        this.dbManager = dbManager;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new SachFragment(dbManager);
            case 1:
                return new TacGiaFragment(dbManager);
            default:
                return new SachFragment(dbManager);
        }
    }

    @Override
    public int getItemCount() {
        return 2; // We have 2 tabs: Sach and TacGia
    }
}
