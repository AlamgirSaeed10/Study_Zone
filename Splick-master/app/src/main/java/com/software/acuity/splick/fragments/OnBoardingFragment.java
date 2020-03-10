package com.software.acuity.splick.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.software.acuity.splick.R;
import com.software.acuity.splick.activities.SelectUserType;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OnBoardingFragment extends Fragment {

    int[] onBoardingImages = {R.drawable.onboarding_1, R.drawable.onboarding_2,
            R.drawable.onboarding_3, R.drawable.onboarding_4};

    private int CURRENT_IMAGE = 0;

    //Butterknife
    @BindView(R.id.imageViewOnboardingScreen)
    RelativeLayout imageViewOnBoardingScreen;

    @BindView(R.id.btnGetStarted)
    ImageButton btnGetStarted;

    public OnBoardingFragment(int position) {
        // Required empty public constructor
        CURRENT_IMAGE = position;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_on_boarding, container, false);

        //Init butterknife with the current fragment view
        ButterKnife.bind(this, fragmentView);


        if (CURRENT_IMAGE == 3) {
            btnGetStarted.setVisibility(View.VISIBLE);
        } else {
            btnGetStarted.setVisibility(View.INVISIBLE);
        }

        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SelectUserType.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                getActivity().finish();
            }
        });

        //Set imageview as per current position of the fragment in a viewpager
        imageViewOnBoardingScreen.setBackgroundResource(onBoardingImages[CURRENT_IMAGE]);

        return fragmentView;
    }
}
