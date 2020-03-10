package com.software.acuity.splick.fragments.discover;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.software.acuity.splick.R;
import com.software.acuity.splick.models.business_deals.Business;
import com.software.acuity.splick.utils.UtilsClass;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class BusinessProfileUpperNameFragment extends Fragment {

    @BindView(R.id.circularImage)
    CircleImageView circleImageView;

    @BindView(R.id.businessName)
    TextView businessName;

    Business business;

    public BusinessProfileUpperNameFragment(Business businessItem) {
        // Required empty public constructor
        this.business = businessItem;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_upper_name, container, false);

        ButterKnife.bind(this, view);

        UtilsClass.setImageUsingPicasso(getActivity(), business.getProfile_path(), circleImageView);
        businessName.setText(business.getFull_name() + "");
        return view;
    }

}
