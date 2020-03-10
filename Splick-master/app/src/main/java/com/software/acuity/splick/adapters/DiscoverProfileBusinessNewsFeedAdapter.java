package com.software.acuity.splick.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.software.acuity.splick.R;
import com.software.acuity.splick.models.home.UsersPost;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.facebook.FacebookSdk.getApplicationContext;

public class DiscoverProfileBusinessNewsFeedAdapter extends RecyclerView.Adapter<DiscoverProfileBusinessNewsFeedAdapter.MyVIewHolder> {

    List<UsersPost> mDataList = new ArrayList<>();
    Context context;
    LayoutInflater layoutInflater;

    public DiscoverProfileBusinessNewsFeedAdapter(Context context, List<UsersPost> mDataList) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.mDataList = mDataList;
    }

    @NonNull
    @Override
    public MyVIewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = layoutInflater.inflate(R.layout.portfolio_row, null);

        MyVIewHolder myViewHolder = new MyVIewHolder(rowView);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyVIewHolder holder, int position) {
        if (!mDataList.get(position).getFile_url().trim().isEmpty()) {
            Picasso.with(getApplicationContext()).load(mDataList.get(position).getFile_url() + "")
                    //download URL
                    .placeholder(R.drawable.app_icon)//use defaul image//if failed
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public static class MyVIewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.portfolioImageView)
        ImageView imageView;


        public MyVIewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
