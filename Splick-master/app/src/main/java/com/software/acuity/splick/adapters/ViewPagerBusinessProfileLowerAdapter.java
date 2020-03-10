package com.software.acuity.splick.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.software.acuity.splick.fragments.discover.profile.DiscoverProfileAboutFragment;
import com.software.acuity.splick.fragments.discover.profile.DiscoverProfileDealsFragment;
import com.software.acuity.splick.fragments.discover.profile.DiscoverProfileNewsFeedFragment;
import com.software.acuity.splick.models.business_deals.Business;
import com.software.acuity.splick.utils.Constants;
import com.software.acuity.splick.utils.SharedPreferenceClass;

import org.json.JSONException;
import org.json.JSONObject;

public class ViewPagerBusinessProfileLowerAdapter extends FragmentStatePagerAdapter {

    Business businessItem;

    SharedPreferenceClass sharedPreferenceClass;
    JSONObject jsonObject =
            null;

    public ViewPagerBusinessProfileLowerAdapter(Context context, @NonNull FragmentManager fm, Business businessItem) {
        super(fm);

        this.businessItem = businessItem;
        sharedPreferenceClass = SharedPreferenceClass.getInstance(context);

        try {
            jsonObject = new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public ViewPagerBusinessProfileLowerAdapter(@NonNull FragmentManager fm,
                                                Business businessItem) {
        super(fm);

        this.businessItem = businessItem;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        try {

            if (jsonObject.getString("user_role").trim().equalsIgnoreCase("business")) {
                if (position == 0) {
                    return new DiscoverProfileNewsFeedFragment(this.businessItem);
                } else if (position == 1) {
                    return new DiscoverProfileAboutFragment(this.businessItem);
                }
            } else if (jsonObject.getString("user_role").trim().equalsIgnoreCase("affiliate")) {
                if (position == 0) {
                    return new DiscoverProfileNewsFeedFragment(this.businessItem);
                } else if (position == 1) {
                    return new DiscoverProfileAboutFragment(this.businessItem);
                } else if (position == 2) {
                    return new DiscoverProfileDealsFragment(this.businessItem);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new DiscoverProfileNewsFeedFragment(this.businessItem);
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
                    return "News Feed";
                } else if (position == 1) {
                    return "About";
                }

            } else if (jsonObject.getString("user_role").trim().equalsIgnoreCase("affiliate")) {
                if (position == 0) {
                    return "News Feed";
                } else if (position == 1) {
                    return "About";
                } else if (position == 2) {
                    return "Deals";
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "News Feed";
    }
}

