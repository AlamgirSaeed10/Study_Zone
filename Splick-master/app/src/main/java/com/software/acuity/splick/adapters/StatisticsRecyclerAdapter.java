package com.software.acuity.splick.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.software.acuity.splick.R;
import com.software.acuity.splick.models.StatisticsModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatisticsRecyclerAdapter extends RecyclerView.Adapter<StatisticsRecyclerAdapter.MyViewHolder> {

    Context context;
    List<StatisticsModel> mDataList;
    LayoutInflater layoutInflater;

    public StatisticsRecyclerAdapter(Context context, List<StatisticsModel> mDataList) {
        this.context = context;
        this.mDataList = mDataList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = layoutInflater.inflate(R.layout.statistics_row, null);

        MyViewHolder myViewHolder = new MyViewHolder(rowView);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.clickTv.setText(mDataList.get(position).getClicks() + "");
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.clicks)
        TextView clickTv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
