package com.software.acuity.splick.activities.discover.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.software.acuity.splick.R;
import com.software.acuity.splick.activities.DashBoardActivity;
import com.software.acuity.splick.adapters.ViewPagerBusinessProfileLowerAdapter;
import com.software.acuity.splick.adapters.ViewPagerBusinessProfileUpperAdapter;
import com.software.acuity.splick.models.business_deals.Business;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiscoverProfileActivity extends AppCompatActivity {

    @BindView(R.id.viewPagerBusinessProfileUpperContainer)
    ViewPager viewPager;

    @BindView(R.id.viewPagerBusinessProfileLowerContainer)
    ViewPager viewPagerLowerContainer;

    @BindView(R.id.tabLayoutBusinessProfile)
    TabLayout tabLayout;

    @BindView(R.id.discoverProfileToolbar)
    Toolbar dicoverProfileToolbar;

    public static DiscoverProfileActivity activityContext;

    ViewPagerBusinessProfileLowerAdapter viewPagerBusinessProfileLowerAdapter;
    ViewPagerBusinessProfileUpperAdapter viewPagerBusinessProfileUpperAdapter;

    Business businessItemObject = new Business();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover_profile);

        ButterKnife.bind(this);

        setSupportActionBar(dicoverProfileToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile");

        businessItemObject = (Business) getIntent().getSerializableExtra("business_item_object");

        activityContext = this;

        initViewPagerUpper();
        initViewPagerLower();
    }

    public void initViewPagerUpper() {
        viewPagerBusinessProfileUpperAdapter =
                new ViewPagerBusinessProfileUpperAdapter(getSupportFragmentManager(), this.businessItemObject);
        viewPager.setAdapter(viewPagerBusinessProfileUpperAdapter);

        viewPager.setOffscreenPageLimit(0);
    }

    public void initViewPagerLower() {
        viewPagerBusinessProfileLowerAdapter =
                new ViewPagerBusinessProfileLowerAdapter(getApplicationContext(), getSupportFragmentManager(), this.businessItemObject);
        viewPagerLowerContainer.setAdapter(viewPagerBusinessProfileLowerAdapter);

        viewPagerLowerContainer.setOffscreenPageLimit(0);
        viewPagerLowerContainer.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent= new Intent(this, DashBoardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent= new Intent(this, DashBoardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
        return true;
    }
}

