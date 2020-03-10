package com.software.acuity.splick.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.software.acuity.splick.fragments.partnerships.FilterCriteriaFragmentOneDay;
import com.software.acuity.splick.fragments.partnerships.FilterCriteriaFragmentOneMonth;
import com.software.acuity.splick.fragments.partnerships.FilterCriteriaFragmentOneWeek;
import com.software.acuity.splick.fragments.partnerships.FilterCriteriaFragmentOneYear;
import com.software.acuity.splick.fragments.partnerships.FilterCriteriaFragmentSixMonths;

public class PartnershipDealsDetailsStatisticsAdapter extends FragmentStatePagerAdapter {
    public PartnershipDealsDetailsStatisticsAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new FilterCriteriaFragmentOneDay();
        } else if (position == 1) {
            return new FilterCriteriaFragmentOneWeek();
        } else if (position == 2) {
            return new FilterCriteriaFragmentOneMonth();
        } else if (position == 3) {
            return new FilterCriteriaFragmentSixMonths();
        } else if (position == 4) {
            return new FilterCriteriaFragmentOneYear();
        }
        return new FilterCriteriaFragmentOneDay();
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "1D";
        } else if (position == 1) {
            return "1W";
        } else if (position == 2) {
            return "1M";
        } else if (position == 3) {
            return "6M";
        } else if (position == 4) {
            return "1Y";
        }
        return "1D";
    }
}
