package com.acuity.Splick.ui.fragments.Affiliate_Dashboard.Earnings.EarningStats;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acuity.Splick.R;

public class Earning_Stats_For_1D extends Fragment {

    private EarningStatsFor1DViewModel mViewModel;

    public static Earning_Stats_For_1D newInstance() {
        return new Earning_Stats_For_1D();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.earning__stats__for_1_d_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EarningStatsFor1DViewModel.class);
        // TODO: Use the ViewModel
    }

}
