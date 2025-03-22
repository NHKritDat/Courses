package com.example.sp25_nguyenhoangdat_net1720.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.sp25_nguyenhoangdat_net1720.database.DatabaseManager;
import com.example.sp25_nguyenhoangdat_net1720.fragments.AuthorFragment;
import com.example.sp25_nguyenhoangdat_net1720.fragments.BookFragment;

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
                return new BookFragment(dbManager);
            case 1:
                return new AuthorFragment(dbManager);
            default:
                return new BookFragment(dbManager);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
