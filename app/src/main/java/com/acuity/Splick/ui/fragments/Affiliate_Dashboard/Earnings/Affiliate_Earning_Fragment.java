package com.acuity.Splick.ui.fragments.Affiliate_Dashboard.Earnings;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acuity.Splick.R;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;

public class Affiliate_Earning_Fragment extends Fragment {

    private AffiliateEarningViewModel mViewModel;
    @BindView(R.id.earning_tabLayout)
    TabLayout earning_tabLayout;
    @BindView(R.id.earning_viewpager)
    ViewPager earning_viewpager;

    public static Affiliate_Earning_Fragment newInstance() {
        return new Affiliate_Earning_Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.affiliate__earning__fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AffiliateEarningViewModel.class);
        // TODO: Use the ViewModel
    }

}
