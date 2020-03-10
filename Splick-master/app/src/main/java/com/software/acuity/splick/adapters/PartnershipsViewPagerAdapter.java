package com.software.acuity.splick.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.software.acuity.splick.fragments.earnings.StatisticsEarningFragment;
import com.software.acuity.splick.fragments.partnerships.DealsFragment;
import com.software.acuity.splick.fragments.partnerships.OffersFragment;
import com.software.acuity.splick.utils.Constants;
import com.software.acuity.splick.utils.SharedPreferenceClass;

import org.json.JSONException;
import org.json.JSONObject;

public class PartnershipsViewPagerAdapter extends FragmentStatePagerAdapter {

    SharedPreferenceClass sharedPreferenceClass;
    JSONObject jsonObject =
            null;

    public PartnershipsViewPagerAdapter(Context context, @NonNull FragmentManager fm) {
        super(fm);

        sharedPreferenceClass = SharedPreferenceClass.getInstance(context);

        try {
            jsonObject = new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public PartnershipsViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {


        try {

            if (jsonObject.getString("user_role").trim().equalsIgnoreCase("business")) {
                if (position == 0) {
                    return new DealsFragment();
                } else if (position == 1) {
                    return new OffersFragment();
                }
//                else if (position == 2) {
//                    return new StatisticsEarningFragment();
//                }
                return new DealsFragment();
            } else if (jsonObject.getString("user_role").trim().equalsIgnoreCase("affiliate")) {
                if (position == 0) {
                    return new DealsFragment();
                } else if (position == 1) {
                    return new OffersFragment();
                } else if (position == 2) {
                    return new StatisticsEarningFragment();
                }
                return new DealsFragment();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new DealsFragment();
    }

    @Override
    public int getCount() {

        try {
            if (jsonObject.getString("user_role").trim().equalsIgnoreCase("business")) {
                return 2;
            } else if (jsonObject.getString("user_role").trim().equalsIgnoreCase("affiliate")) {
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
            if (jsonObject.getString("user_role").trim().equalsIgnoreCase("business")) {
                if (position == 0) {
                    return "Affiliates";
                } else if (position == 1) {
                    return "Offers";
                }
            } else if (jsonObject.getString("user_role").trim().equalsIgnoreCase("affiliate")) {
                if (position == 0) {
                    return "Deals";
                } else if (position == 1) {
                    return "Offers";
                } else if (position == 2) {
                    return "Statistics";
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "Deals";
    }
}
