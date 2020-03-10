package com.software.acuity.splick.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.software.acuity.splick.fragments.earnings.StatisticsEarningFragment;
import com.software.acuity.splick.fragments.payment.InvoiceFragment;
import com.software.acuity.splick.models.business_deals.Business;
import com.software.acuity.splick.utils.AppController;
import com.software.acuity.splick.utils.Constants;
import com.software.acuity.splick.utils.SharedPreferenceClass;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentScreenViewPagerAdapter extends FragmentStatePagerAdapter {

    Business businessItem;

    SharedPreferenceClass sharedPreferenceClass;
    JSONObject jsonObject =
            null;

    public PaymentScreenViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);

//        this.businessItem = businessItem;
        sharedPreferenceClass = SharedPreferenceClass.getInstance(AppController.getContext());

        try {
            jsonObject = new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new InvoiceFragment();
        } else if (position == 1) {
            return new StatisticsEarningFragment();
        }
        return new InvoiceFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Invoice";
        } else if (position == 1) {
            return "Statistics";
        }
        return "Invoice";
    }
}

