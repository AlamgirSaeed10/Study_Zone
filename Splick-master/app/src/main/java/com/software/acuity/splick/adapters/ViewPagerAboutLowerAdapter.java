package com.software.acuity.splick.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.software.acuity.splick.fragments.about.AboutTabAboutFragment;
import com.software.acuity.splick.fragments.about.AboutTabPortfolioFragment;
import com.software.acuity.splick.utils.AppController;
import com.software.acuity.splick.utils.Constants;
import com.software.acuity.splick.utils.SharedPreferenceClass;

import org.json.JSONException;
import org.json.JSONObject;

public class ViewPagerAboutLowerAdapter extends FragmentStatePagerAdapter {

    SharedPreferenceClass sharedPreferenceClass;

    public ViewPagerAboutLowerAdapter(@NonNull FragmentManager fm) {
        super(fm);

        sharedPreferenceClass = SharedPreferenceClass.getInstance(AppController.getContext());
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new AboutTabPortfolioFragment();
        } else if (position == 1) {
            return new AboutTabAboutFragment();
        }
        return new AboutTabPortfolioFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        try {
            if (new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT)).getString("user_role").equalsIgnoreCase("business")) {
                if (position == 0) {
                    return "Portfolio";
                } else if (position == 1) {
                    return "About";
                }
            } else {
                if (position == 0) {
                    return "Portfolio";
                } else if (position == 1) {
                    return "About";
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return "Portfolio";
    }
}

