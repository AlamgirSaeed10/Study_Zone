package com.software.acuity.splick.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.software.acuity.splick.R;
import com.software.acuity.splick.activities.EditProfileActivity;
import com.software.acuity.splick.activities.UpdateProfileActivity;
import com.software.acuity.splick.adapters.ViewPagerAboutLowerAdapter;
import com.software.acuity.splick.adapters.ViewPagerAboutUpperAdapter;
import com.software.acuity.splick.utils.AppController;
import com.software.acuity.splick.utils.Constants;
import com.software.acuity.splick.utils.SharedPreferenceClass;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardAboutFragment extends Fragment {

    @BindView(R.id.viewPagerAboutUpperContainer)
    ViewPager viewPager;

    @BindView(R.id.viewPagerAboutLowerContainer)
    ViewPager viewPagerLowerContainer;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.updateProfile)
    ImageButton updateProfile;

    ViewPagerAboutUpperAdapter viewPagerAboutUpperAdapter;
    ViewPagerAboutLowerAdapter viewPagerAboutLowerAdapter;

    SharedPreferenceClass sharedPreferenceClass;

    public DashboardAboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_dashboard_about, container, false);

        ButterKnife.bind(this, fragmentView);

        sharedPreferenceClass = SharedPreferenceClass.getInstance(AppController.getContext());

        initViewPagerUpper();
        initViewPagerLower();

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT)).getString("user_role").equalsIgnoreCase("business")) {

                        Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getActivity(), UpdateProfileActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return fragmentView;
    }

    public void initViewPagerUpper() {
        viewPagerAboutUpperAdapter = new ViewPagerAboutUpperAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAboutUpperAdapter);

        viewPager.setOffscreenPageLimit(0);
    }

    public void initViewPagerLower() {
        viewPagerAboutLowerAdapter = new ViewPagerAboutLowerAdapter(getActivity().getSupportFragmentManager());
        viewPagerLowerContainer.setAdapter(viewPagerAboutLowerAdapter);

        viewPagerLowerContainer.setOffscreenPageLimit(0);

        viewPagerLowerContainer.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                try {
                    if (!(new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT)).getString("user_role").equalsIgnoreCase("business"))) {
                        viewPagerAboutLowerAdapter.notifyDataSetChanged();
                        viewPagerAboutUpperAdapter.notifyDataSetChanged();

                        viewPager.setCurrentItem(position);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabLayout.setupWithViewPager(viewPagerLowerContainer);
    }

}


