package com.software.acuity.splick.fragments.discover;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.software.acuity.splick.R;
import com.software.acuity.splick.models.business_deals.Business;

/**
 * A simple {@link Fragment} subclass.
 */
public class BusinessProfileUpperSocialDealsFragment extends Fragment {


    public BusinessProfileUpperSocialDealsFragment(Business businessItem) {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_upper_social_deals, container, false);
    }

}
