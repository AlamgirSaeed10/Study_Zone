package com.software.acuity.splick.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.software.acuity.splick.R;
import com.software.acuity.splick.activities.SignInActivity;
import com.software.acuity.splick.activities.SignUpActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompleteProfileFragment extends Fragment {


    @BindView(R.id.btnLetsGo)
    Button letsGoBtn;

    public CompleteProfileFragment(Context context, SignUpActivity activity) {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_complete_profile, container, false);

        ButterKnife.bind(this, fragmentView);

        letsGoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SignInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });

        return fragmentView;
    }

}
