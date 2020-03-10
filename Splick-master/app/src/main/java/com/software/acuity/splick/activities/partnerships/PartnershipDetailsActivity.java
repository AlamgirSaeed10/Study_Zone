package com.software.acuity.splick.activities.partnerships;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.software.acuity.splick.R;
import com.software.acuity.splick.adapters.PartnershipDetailsViewPagerAdapter;
import com.software.acuity.splick.models.partnerships.AffiliateDeals;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PartnershipDetailsActivity extends AppCompatActivity {
    @BindView(R.id.viewPagerPartnershipDetails)
    ViewPager viewPager;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.btnPartnershipDetailsBack)
    ImageButton backBtn;

    public static Context activityContext;

    private PartnershipDetailsViewPagerAdapter partnershipDetailsViewPagerAdapter;

    //TODO: FInd a better way
    public static AffiliateDeals affiliateDeals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partnership_details);

        affiliateDeals = (AffiliateDeals) getIntent().getSerializableExtra("deal_obj");

        ButterKnife.bind(this);

        activityContext = this;

        initViewPager();

        backBtn.setOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void initViewPager() {
        partnershipDetailsViewPagerAdapter = new PartnershipDetailsViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(partnershipDetailsViewPagerAdapter);

        viewPager.setOffscreenPageLimit(0);
        tabLayout.setupWithViewPager(viewPager);
    }
}
