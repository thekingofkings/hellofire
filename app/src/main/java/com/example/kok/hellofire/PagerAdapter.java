package com.example.kok.hellofire;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

/**
 * PagerAdapter sets up the correct Fragment to show with a given Tab in {@link HelloFireActivity}.
 *
 * Currently, there are three Tabs defined: Info, Camera, and Log. This adapter maps these views:
 * {@link InfoFragment}, {@link CameraFragment}, and {@link LogFragment} to each of the tab.
 *
 * The above is achieved by taking the tab position as input, and render corresponding Fragment in
 * the {@link androidx.viewpager2.widget.ViewPager2} section of main activity.
 */
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
                return new InfoFragment();
            case 1:
                return new CameraFragment();
            case 2:
                return new LogFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return numTabs;
    }
}
