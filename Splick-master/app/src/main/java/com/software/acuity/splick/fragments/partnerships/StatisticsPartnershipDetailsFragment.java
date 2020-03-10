package com.software.acuity.splick.fragments.partnerships;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.software.acuity.splick.R;
import com.software.acuity.splick.adapters.PartnershipDealsDetailsStatisticsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticsPartnershipDetailsFragment extends Fragment {

    @BindView(R.id.viewPagerPartnershipDetailsStatistics)
    ViewPager viewPager;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    PartnershipDealsDetailsStatisticsAdapter partnershipDealsDetailsStatisticsAdapter;

    public StatisticsPartnershipDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.partnership_details_statistics, container, false);

        ButterKnife.bind(this, fragmentView);

        initViewPager();

        return fragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        initViewPager();
    }

    @Override
    public void onPause() {
        super.onPause();
        partnershipDealsDetailsStatisticsAdapter = null;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            if (partnershipDealsDetailsStatisticsAdapter != null) {
                initViewPager();
            }
        }
    }

    public void initViewPager() {
        partnershipDealsDetailsStatisticsAdapter = new PartnershipDealsDetailsStatisticsAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(partnershipDealsDetailsStatisticsAdapter);

        viewPager.setOffscreenPageLimit(0);
        tabLayout.setupWithViewPager(viewPager);
    }
}
