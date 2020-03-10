package com.software.acuity.splick.fragments.earnings;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.software.acuity.splick.R;
import com.software.acuity.splick.adapters.EarningsViewPagerAdapter;
import com.software.acuity.splick.fragments.HomeFragment;
import com.software.acuity.splick.interfaces.ILoadData;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EarningsFragment extends Fragment {
    @BindView(R.id.viewPagerPartnership)
    ViewPager viewPager;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.edit_earnings)
    ImageView edit_earnings;

    @Nullable
    @BindView(R.id.edit_earning_amount)
    TextView edit_earning_amount;

    private EarningsViewPagerAdapter earningsViewPagerAdapter;

    ILoadData iLoadData;

    public EarningsFragment() {
        // Required empty public constructor
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser)
            getActivity().getSupportFragmentManager().popBackStackImmediate(HomeFragment.class.getName(), 0);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_earnings, container, false);

        ButterKnife.bind(this, fragmentView);


        initViewPager();
        return fragmentView;
    }

    public void initViewPager() {
        earningsViewPagerAdapter = new EarningsViewPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(earningsViewPagerAdapter);

        viewPager.setOffscreenPageLimit(0);

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
        edit_earnings.setOnClickListener(v->{
            Toast.makeText(getContext(), "Can not edit Earnings.",Toast.LENGTH_SHORT).show();
        });
    }
}
