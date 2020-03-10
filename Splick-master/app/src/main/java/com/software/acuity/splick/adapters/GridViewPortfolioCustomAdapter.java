package com.software.acuity.splick.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.software.acuity.splick.R;

public class GridViewPortfolioCustomAdapter extends BaseAdapter {

    LayoutInflater layoutInflater;

    public GridViewPortfolioCustomAdapter(Context _context){
        layoutInflater = LayoutInflater.from(_context);
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View cellView  = layoutInflater.inflate(R.layout.grid_cell_portfolio, null);


        return cellView;
    }
}
