package com.dmitrysukhov.thirdhomework;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.dmitrysukhov.thirdhomework.fragments.WeatherDetailsFragment;
import com.dmitrysukhov.thirdhomework.fragments.WeatherMainFragment;

public class ScreenSlidePagerAdapter extends FragmentStateAdapter {

    private static final int NUM_PAGES = 2;

    public ScreenSlidePagerAdapter(FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment;
        switch (position) {
            case 0: {
                fragment = new WeatherMainFragment();
                break;
            }
            case 1: {
                fragment = new WeatherDetailsFragment();
                break;
            }
            default: {
                fragment = new WeatherMainFragment();
            }
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }
}