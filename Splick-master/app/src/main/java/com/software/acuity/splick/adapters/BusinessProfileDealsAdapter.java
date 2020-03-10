package com.software.acuity.splick.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.software.acuity.splick.R;
import com.software.acuity.splick.activities.partnerships.OfferOverviewActivity;
import com.software.acuity.splick.models.business_deals.BusinessDeal;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class BusinessProfileDealsAdapter extends RecyclerView.Adapter<BusinessProfileDealsAdapter.MyVIewHolder> {

    List<BusinessDeal> mDataList = new ArrayList<>();
    Context context;
    LayoutInflater layoutInflater;




    public BusinessProfileDealsAdapter(Context context, List<BusinessDeal> mDataList) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.mDataList = mDataList;
    }

    @NonNull
    @Override
    public MyVIewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = layoutInflater.inflate(R.layout.row_business_profile_deals, null);

        MyVIewHolder myViewHolder = new MyVIewHolder(rowView);

        return myViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull MyVIewHolder holder, int position) {
        holder.textView.setText(mDataList.get(position).getDeal_title());
        holder.dealCard.setOnClickListener(v -> {
            Toast.makeText(context, "Under Development.", Toast.LENGTH_SHORT).show();

        });



//        if(!mDataList.get(position).getDeal_url().trim().isEmpty()){
//            Picasso.with(getApplicationContext()).load(mDataSet.get(position).getUser_profile() + "")
//                    //download URL
//                    .placeholder(R.drawable.app_icon)//use defaul image//if failed
//                    .into(holder.postAvatar);
//        }
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public static class MyVIewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.businessProfileDealsImageView)
        CircleImageView imageView;

        @BindView(R.id.businessProfileDealsName)
        TextView textView;

        @BindView(R.id.dealCard)
        CardView dealCard;

        public MyVIewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
