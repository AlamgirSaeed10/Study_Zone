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

public class Payout_Fragment extends Fragment {

    private PayoutViewModel mViewModel;

    public static Payout_Fragment newInstance() {
        return new Payout_Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.payout__fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(PayoutViewModel.class);
        // TODO: Use the ViewModel
    }

}
