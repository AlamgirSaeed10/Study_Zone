package com.software.acuity.splick.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SignUpViewPagerAdapter extends FragmentStatePagerAdapter {

    List<Fragment> fragmentsList = new ArrayList<Fragment>();
    String[] objectKey = {"signup", "about", "tags", "bio", "portfolio", "complete"};
//    String[] objectKey = {"signup","about","tags","bio","social","portfolio","complete"};


    public SignUpViewPagerAdapter(FragmentManager fm, List<Fragment> _fragmentsList) {
        super(fm);

        this.fragmentsList = _fragmentsList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentsList.get(position);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        for (int i = 0; i < objectKey.length; i++) {
            if (((String) object).equalsIgnoreCase(objectKey[i])) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public int getCount() {
        return this.fragmentsList.size();
    }
}
