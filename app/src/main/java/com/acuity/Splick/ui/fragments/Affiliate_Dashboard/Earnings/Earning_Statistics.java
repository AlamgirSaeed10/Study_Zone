package com.acuity.Splick.ui.fragments.Affiliate_Dashboard.Earnings;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acuity.Splick.R;

public class Earning_Statistics extends Fragment {

    private EarningStatisticsViewModel mViewModel;

    public static Earning_Statistics newInstance() {
        return new Earning_Statistics();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.earning__statistics_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(EarningStatisticsViewModel.class);
        // TODO: Use the ViewModel
    }

}
