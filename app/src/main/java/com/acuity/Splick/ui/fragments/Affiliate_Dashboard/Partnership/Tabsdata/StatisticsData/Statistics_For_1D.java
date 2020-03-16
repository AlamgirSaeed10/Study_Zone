package com.acuity.Splick.ui.fragments.Affiliate_Dashboard.Partnership.Tabsdata.StatisticsData;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acuity.Splick.R;

public class Statistics_For_1D extends Fragment {

    private StatisticsFor1DViewModel mViewModel;

    public static Statistics_For_1D newInstance() {
        return new Statistics_For_1D();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.statistics__for_1d_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(StatisticsFor1DViewModel.class);
        // TODO: Use the ViewModel
    }

}
