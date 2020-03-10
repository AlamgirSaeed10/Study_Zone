package com.software.acuity.splick.adapters;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.software.acuity.splick.R;
import com.software.acuity.splick.interfaces.IRecyclerClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MyViewHolder> {

    private List<String> mDataSet;
    private LayoutInflater layoutInflater;

    public MessagesAdapter(Context context, List<String> dataList) {
        this.mDataSet = dataList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate a view and pass it to the view holder class
        View rowView = layoutInflater.inflate(R.layout.row_messages, null);

        MyViewHolder myViewHolder = new MyViewHolder(rowView);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return this.mDataSet.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
