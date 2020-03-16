package com.acuity.Splick.ui.fragments.Affiliate_Dashboard.Partnership.Tabsdata;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acuity.Splick.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Affiliate_Partnership_Offers extends Fragment {

    @BindView(R.id.offers_recycler_view)
    RecyclerView offers_recycler_view;

    private AffiliatePartnershipOffersViewModel mViewModel;

    public static Affiliate_Partnership_Offers newInstance() {
        return new Affiliate_Partnership_Offers();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.affiliate__partnership__offers_fragment, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AffiliatePartnershipOffersViewModel.class);
        // TODO: Use the ViewModel
    }

}
