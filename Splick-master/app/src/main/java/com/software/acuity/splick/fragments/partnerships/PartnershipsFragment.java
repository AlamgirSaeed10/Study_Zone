package com.software.acuity.splick.fragments.partnerships;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.software.acuity.splick.R;
import com.software.acuity.splick.adapters.PartnershipsViewPagerAdapter;
import com.software.acuity.splick.fragments.HomeFragment;
import com.software.acuity.splick.interfaces.ILoadData;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PartnershipsFragment extends Fragment {

    @BindView(R.id.viewPagerPartnership)
    ViewPager viewPager;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;



    PartnershipsViewPagerAdapter partnershipsViewPagerAdapter;

    public PartnershipsFragment() {
        // Required empty public constructor
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser)
            getActivity().getSupportFragmentManager().popBackStackImmediate(HomeFragment.class.getName(), 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_partnerships, container, false);

        ButterKnife.bind(this, fragmentView);

        initViewPager();

        return fragmentView;
    }

    public void initViewPager() {
        partnershipsViewPagerAdapter = new PartnershipsViewPagerAdapter(getActivity(), getActivity().getSupportFragmentManager());
        viewPager.setAdapter(partnershipsViewPagerAdapter);

        viewPager.setOffscreenPageLimit(3);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabLayout.setupWithViewPager(viewPager);
    }
}
