package com.software.acuity.splick.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.software.acuity.splick.R;
import com.software.acuity.splick.adapters.PaymentScreenViewPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentActivity extends AppCompatActivity {
    @BindView(R.id.paymentViewPager)
    ViewPager viewPager;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    PaymentScreenViewPagerAdapter paymentScreenViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);

        findViewById(R.id.backBtnFromPayment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        initViewPagerLower();
    }

    public void initViewPagerLower() {
        paymentScreenViewPagerAdapter = new PaymentScreenViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(paymentScreenViewPagerAdapter);

        viewPager.setOffscreenPageLimit(2);

        tabLayout.setupWithViewPager(viewPager);
    }

}
