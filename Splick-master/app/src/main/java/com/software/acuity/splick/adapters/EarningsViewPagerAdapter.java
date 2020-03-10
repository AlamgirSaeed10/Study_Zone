package com.software.acuity.splick.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.software.acuity.splick.fragments.earnings.PayoutFragment;
import com.software.acuity.splick.fragments.earnings.StatisticsEarningFragment;
import com.software.acuity.splick.fragments.partnerships.DealsFragment;
import com.software.acuity.splick.fragments.partnerships.OffersFragment;
import com.software.acuity.splick.fragments.partnerships.StatisticsFragment;

public class EarningsViewPagerAdapter extends FragmentStatePagerAdapter {

    public EarningsViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new PayoutFragment();
        } else if (position == 1) {
            return new StatisticsEarningFragment();
        }
        return new PayoutFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Payout";
        } else if (position == 1) {
            return "Statistics";
        }
        return "Payout";
    }
}
