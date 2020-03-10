package com.software.acuity.splick.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.software.acuity.splick.R;
import com.software.acuity.splick.adapters.OnBoardingPagerAdapter;
import com.software.acuity.splick.transformers.DepthPageTransformer;
import com.software.acuity.splick.transformers.ZoomOutPageTransformer;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OnBoardingActivity extends AppCompatActivity {

    @BindView(R.id.viewPagerOnBoardingScreen)
    ViewPager viewPagerOnBoardingScreen;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter pagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        //Init butterknife
        ButterKnife.bind(this);

        pagerAdapter = new OnBoardingPagerAdapter(getSupportFragmentManager());
        viewPagerOnBoardingScreen.setAdapter(pagerAdapter);

//        viewPagerOnBoardingScreen.setPageTransformer(true, new DepthPageTransformer());
    }
}
