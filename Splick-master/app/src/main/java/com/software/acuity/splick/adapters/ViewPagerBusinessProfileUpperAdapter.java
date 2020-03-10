package com.software.acuity.splick.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.software.acuity.splick.fragments.about.AboutUpperNameFragment;
import com.software.acuity.splick.fragments.about.AboutUpperSocialInfoFragment;
import com.software.acuity.splick.models.business_deals.Business;
import com.software.acuity.splick.utils.AppController;
import com.software.acuity.splick.utils.Constants;
import com.software.acuity.splick.utils.SharedPreferenceClass;

import org.json.JSONException;
import org.json.JSONObject;

public class ViewPagerBusinessProfileUpperAdapter extends FragmentStatePagerAdapter {

    Business businessItem;

    SharedPreferenceClass sharedPreferenceClass;

    public ViewPagerBusinessProfileUpperAdapter(@NonNull FragmentManager fm,
                                                Business businessItem) {
        super(fm);

        this.businessItem = businessItem;

        sharedPreferenceClass = SharedPreferenceClass.getInstance(AppController.getContext());
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        try {
            if (!new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT)).getString("user_role").equalsIgnoreCase("business")) {
                if (position == 0) {
                    return new AboutUpperNameFragment(this.businessItem);
                }
            } else {
                if (position == 0) {
                    return new AboutUpperSocialInfoFragment(this.businessItem);
                } else if (position == 1) {
                    return new AboutUpperNameFragment(this.businessItem);
                }
                return new AboutUpperNameFragment(this.businessItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

//
//        if (position == 0) {
//        } else if (position == 1) {
//            return new BusinessProfileUpperNameFragment(this.businessItem);
//        }
        return new AboutUpperNameFragment();
    }

    @Override
    public int getCount() {
        try {
            if (!new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT)).getString("user_role").equalsIgnoreCase("business")) {
                return 1;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 2;
    }
}

