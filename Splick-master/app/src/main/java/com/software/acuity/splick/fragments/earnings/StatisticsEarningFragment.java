package com.software.acuity.splick.fragments.earnings;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.software.acuity.splick.R;
import com.software.acuity.splick.adapters.PartnershipDealsDetailsStatisticsAdapter;
import com.software.acuity.splick.interfaces.ILoadData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticsEarningFragment extends Fragment {

  //  @BindView(R.id.viewPagerPartnershipDetailsStatistics)
 //   ViewPager viewPager;

    @Nullable
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    PieChartView pieChartView;
    ListView listView;

   // PartnershipDealsDetailsStatisticsAdapter partnershipDealsDetailsStatisticsAdapter;

    ILoadData iLoadData;

    //TODO: Issue in loading

    public StatisticsEarningFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.earning_statistics, container, false);

        ButterKnife.bind(this, fragmentView);

        pieChartView =fragmentView.findViewById(R.id.chart);
        listView = fragmentView.findViewById(R.id.list_view);

        List pieData = new ArrayList<>();
        pieData.add(new SliceValue(10, ContextCompat.getColor(getApplicationContext(),R.color.red)).setLabel(""));
        pieData.add(new SliceValue(80, ContextCompat.getColor(getApplicationContext(),R.color.yellow)).setLabel(""));
        pieData.add(new SliceValue(50, ContextCompat.getColor(getApplicationContext(),R.color.green)).setLabel(""));
        pieData.add(new SliceValue(30, ContextCompat.getColor(getApplicationContext(),R.color.sky_blue)).setLabel(""));

        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(14);
        pieChartData.setHasCenterCircle(true).setCenterText1("50%").setCenterText1FontSize(40).setCenterText1Color(Color.parseColor("#0097A7"));

        pieChartView.setPieChartData(pieChartData);


        ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,pieData);
        listView.setAdapter(adapter);








     //   initViewPager();
        return fragmentView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
     //   partnershipDealsDetailsStatisticsAdapter = null;
    }
/*
    public void initViewPager() {
        partnershipDealsDetailsStatisticsAdapter = new PartnershipDealsDetailsStatisticsAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(partnershipDealsDetailsStatisticsAdapter);

        viewPager.setOffscreenPageLimit(0);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
   }
 */
}
