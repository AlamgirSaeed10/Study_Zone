package com.software.acuity.splick.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.software.acuity.splick.fragments.OnBoardingFragment;

public class OnBoardingPagerAdapter extends FragmentStatePagerAdapter {

    public static final int NUM_PAGES = 4;

    public OnBoardingPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return new OnBoardingFragment(position);
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}