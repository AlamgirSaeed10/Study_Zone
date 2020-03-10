package com.software.acuity.splick.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.software.acuity.splick.R;
import com.software.acuity.splick.models.offers.AffiliateOffer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class OffersCustomAdapter extends RecyclerView.Adapter<OffersCustomAdapter.OffersViewHolder> {

    Context mContext;
    List<AffiliateOffer> mDataList;
    LayoutInflater layoutInflater;

    public OffersCustomAdapter(Context context, List<AffiliateOffer> dataList) {
        mDataList = dataList;
        mContext = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public OffersCustomAdapter.OffersViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                   int viewType) {
        View myViewHolder = layoutInflater.inflate(R.layout.row_offer_item, null);

        OffersViewHolder offersViewHolder = new OffersViewHolder(myViewHolder);

        return offersViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OffersViewHolder holder, int position) {
        holder.offerName.setText(mDataList.get(position).getDeal_title());
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public static class OffersViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.offerFragmentImageView)
        CircleImageView imageView;

        @BindView(R.id.offerFragmentName)
        TextView offerName;

        public OffersViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
