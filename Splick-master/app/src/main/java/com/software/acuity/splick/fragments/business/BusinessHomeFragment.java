package com.software.acuity.splick.fragments.business;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.software.acuity.splick.R;
import com.software.acuity.splick.activities.SelectUserType;
import com.software.acuity.splick.activities.home.AllMessagesActivity;
import com.software.acuity.splick.adapters.PartnershipDealsDetailsStatisticsAdapter;
import com.software.acuity.splick.interfaces.ILoadData;
import com.software.acuity.splick.utils.Constants;
import com.software.acuity.splick.utils.SharedPreferenceClass;

import butterknife.BindView;
import butterknife.ButterKnife;
public class BusinessHomeFragment extends Fragment {

    @BindView(R.id.viewPagerPartnershipDetailsStatistics)
    ViewPager viewPager;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.logout_btn)
    ImageButton logoutBtn;

    PartnershipDealsDetailsStatisticsAdapter partnershipDealsDetailsStatisticsAdapter;

    SharedPreferenceClass sharedPreferenceClass;

    ILoadData iLoadData;
    private String requestTag = "click_data";
    public static final int CLICK_VIEW_REQUEST = 10001;

    public BusinessHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, fragmentView);

        sharedPreferenceClass = SharedPreferenceClass.getInstance(getActivity());
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AllMessagesActivity.class);
                startActivity(intent);
                /*
                new AlertDialog.Builder(getActivity())
                        .setIcon(R.drawable.caution_icon)
                        .setTitle("Logout")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton("Yes", (dialog, which) -> {

                            sharedPreferenceClass.setValues(Constants.USER_JSON_OBJECT, "");
                            sharedPreferenceClass.setValues(Constants.USER_ID, "");
                            sharedPreferenceClass.setValues(Constants.LOGIN_STATUS, "");

                            Intent intent = new Intent(getActivity(), SelectUserType.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                            startActivity(intent);
                            getActivity().finish();
                        }).
                        setNegativeButton("No", (dialog, which) -> {
                        }).show();

                 */
            }
        });

        initViewPager();
        return fragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void initViewPager() {
        partnershipDealsDetailsStatisticsAdapter = new PartnershipDealsDetailsStatisticsAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(partnershipDealsDetailsStatisticsAdapter);

        viewPager.setOffscreenPageLimit(5);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
//                iLoadData = FilterCriteriaFragment.filterCriteriaFragmentContext;
//                iLoadData.loadData(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
