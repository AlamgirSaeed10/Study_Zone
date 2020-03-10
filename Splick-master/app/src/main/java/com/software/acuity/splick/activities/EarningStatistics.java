package com.software.acuity.splick.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.software.acuity.splick.R;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class EarningStatistics extends AppCompatActivity {

    PieChartView pieChartView;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earning_statistics);

        pieChartView = findViewById(R.id.chart);
        listView = findViewById(R.id.list_view);

        List pieData = new ArrayList<>();
        pieData.add(new SliceValue(10, ContextCompat.getColor(getApplicationContext(),R.color.red)).setLabel(""));
        pieData.add(new SliceValue(80, ContextCompat.getColor(getApplicationContext(),R.color.yellow)).setLabel(""));
        pieData.add(new SliceValue(50, ContextCompat.getColor(getApplicationContext(),R.color.green)).setLabel(""));
        pieData.add(new SliceValue(30, ContextCompat.getColor(getApplicationContext(),R.color.sky_blue)).setLabel(""));

        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(14);
        pieChartData.setHasCenterCircle(true).setCenterText1("50%").setCenterText1FontSize(40).setCenterText1Color(Color.parseColor("#0097A7"));

        pieChartView.setPieChartData(pieChartData);


        ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,pieData);
        listView.setAdapter(adapter);
    }
}
