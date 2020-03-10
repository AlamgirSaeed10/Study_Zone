package com.software.acuity.splick.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.software.acuity.splick.fragments.partnerships.MarketingFragment;
import com.software.acuity.splick.fragments.partnerships.PartnershipDetailsDealsFragment;
import com.software.acuity.splick.fragments.partnerships.StatisticsPartnershipDetailsFragment;
import com.software.acuity.splick.utils.AppController;
import com.software.acuity.splick.utils.Constants;
import com.software.acuity.splick.utils.SharedPreferenceClass;

import org.json.JSONException;
import org.json.JSONObject;

public class PartnershipDetailsViewPagerAdapter extends FragmentStatePagerAdapter {

    SharedPreferenceClass sharedPreferenceClass;
    JSONObject userObject;

    public PartnershipDetailsViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);

        sharedPreferenceClass = SharedPreferenceClass.getInstance(AppController.getContext());
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        try {
            userObject = new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT));
            if (userObject.getString("user_role").trim().equalsIgnoreCase("business")) {
                if (position == 0) {
                    return new StatisticsPartnershipDetailsFragment();
                }

            } else {
                if (position == 0) {
                    return new StatisticsPartnershipDetailsFragment();
                } else if (position == 1) {
                    return new PartnershipDetailsDealsFragment();
                } else if (position == 2) {
                    return new MarketingFragment();
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new StatisticsPartnershipDetailsFragment();
    }

    @Override
    public int getCount() {

        try {
            userObject = new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT));
            if (userObject.getString("user_role").trim().equalsIgnoreCase("business")) {

                return 1;


            } else {

                return 3;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 3;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        try {
            userObject = new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT));
            if (userObject.getString("user_role").trim().equalsIgnoreCase("business")) {
                if (position == 0) {
                    return "Statistics";
                }
            } else {
                if (position == 0) {
                    return "Statistics";
                } else if (position == 1) {
                    return "Deals";
                } else if (position == 2) {
                    return "Marketing";
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "Statistics";
    }
}
