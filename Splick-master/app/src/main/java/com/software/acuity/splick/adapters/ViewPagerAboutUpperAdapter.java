package com.software.acuity.splick.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.software.acuity.splick.fragments.about.AboutUpperNameFragment;
import com.software.acuity.splick.fragments.about.AboutUpperSocialInfoFragment;
import com.software.acuity.splick.utils.AppController;
import com.software.acuity.splick.utils.Constants;
import com.software.acuity.splick.utils.SharedPreferenceClass;

import org.json.JSONException;
import org.json.JSONObject;

public class ViewPagerAboutUpperAdapter extends FragmentStatePagerAdapter {

    SharedPreferenceClass sharedPreferenceClass;

    public ViewPagerAboutUpperAdapter(@NonNull FragmentManager fm) {
        super(fm);

        sharedPreferenceClass = SharedPreferenceClass.getInstance(AppController.getContext());
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        try {
            if (new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT)).getString("user_role").equalsIgnoreCase("business")) {
                if (position == 0) {
                    return new AboutUpperNameFragment();
                }
            } else {
                if (position == 0) {
                    return new AboutUpperSocialInfoFragment();
                } else if (position == 1) {
                    return new AboutUpperNameFragment();
                }
                return new AboutUpperNameFragment();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        if (position == 0) {
//            return new AboutUpperNameFragment();
//        } else if (position == 1) {
//            return new AboutUpperSocialInfoFragment();
//        }
        return new AboutUpperNameFragment();
    }

    @Override
    public int getCount() {
        try {
            if (new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT)).getString("user_role").equalsIgnoreCase("business")) {
                return 1;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 2;
    }
}

