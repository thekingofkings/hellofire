package com.example.kok.hellofire;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PagerAdapter extends FragmentStateAdapter {
    int numTabs;

    public PagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, int numTabs) {
        super(fragmentManager, lifecycle);
        this.numTabs = numTabs;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                InfoFragment tab0 = new InfoFragment();
                return tab0;
            case 1:
                CameraFragment tab1 = new CameraFragment();
                return tab1;
            case 2:
                LogFragment tab2 = new LogFragment();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return numTabs;
    }
}
