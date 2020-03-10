package com.software.acuity.splick.fragments.discover;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.software.acuity.splick.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverProfileFragment extends Fragment {


    public DiscoverProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_discover_profile, container, false);
    }

}
