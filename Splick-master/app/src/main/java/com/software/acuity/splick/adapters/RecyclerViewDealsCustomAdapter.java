package com.software.acuity.splick.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.software.acuity.splick.R;
import com.software.acuity.splick.models.partnerships.AffiliateDeals;
import com.software.acuity.splick.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.facebook.FacebookSdk.getApplicationContext;

public class RecyclerViewDealsCustomAdapter extends RecyclerView.Adapter<RecyclerViewDealsCustomAdapter.DealsViewHolder> {

    public Context mContext;
    List<AffiliateDeals> mDataList;
    LayoutInflater layoutInflater;



    public RecyclerViewDealsCustomAdapter(Context context, List<AffiliateDeals> dataList) {
        mDataList = dataList;
        mContext = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public DealsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myViewHolder = layoutInflater.inflate(R.layout.row_item_deals, null);

        DealsViewHolder dealsViewHolder = new DealsViewHolder(myViewHolder, mContext, mDataList);

        return dealsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DealsViewHolder holder, int position) {

        try {
            JSONObject userObject = new JSONObject(Constants.jsonObject);

            if (userObject.getString("user_role").equalsIgnoreCase("business")) {
               AffiliateDeals affiliateDeals = new AffiliateDeals();

                holder.businessName.setText(mDataList.get(position).getAff_name());
                holder.commissionTextView.setText("$" + mDataList.get(position).getCommission() + "");
                holder.dealClicks.setText("" + mDataList.get(position).getViews() + "");
                holder.dealSales.setText("$" + mDataList.get(position).getRevenue() + "");

                holder.copyLink.setVisibility(View.INVISIBLE);
            } else if (userObject.getString("user_role").equalsIgnoreCase("affiliate")) {
                holder.businessName.setText(mDataList.get(position).getDeal_name());
                holder.commissionTextView.setText("$" + mDataList.get(position).getCommission() + "");
                holder.dealClicks.setText("" + mDataList.get(position).getViews() + "");
                holder.dealSales.setText("$" + mDataList.get(position).getRevenue() + "");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        holder.copyLink.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setClipboard(mDataList.get(position).getDeal_url() + "");
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public static class DealsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.dealPartnershipDealName)
        TextView businessName;

        @BindView(R.id.dealPartnershipImage)
        CircleImageView circleImageView;

        @BindView(R.id.commissionmot)
        TextView commissionTextView;

        @BindView(R.id.numberOfClicks)
        TextView dealClicks;

        @BindView(R.id.noOfSales)
        TextView dealSales;

        @Nullable
        @BindView(R.id.dealPartnershipCopyLink)
        MaterialButton copyLink;

        Context mContext;
        List<AffiliateDeals> mDataList;

        public DealsViewHolder(@NonNull View itemView, Context context, List<AffiliateDeals> mDataList) {
            super(itemView);

            ButterKnife.bind(this, itemView);

            mContext = context;
            this.mDataList = mDataList;

            copyLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setClipboard(mDataList.get(getAdapterPosition()).getDeal_url().trim() + " ");
                }
            });
        }

        public void setClipboard(String text) {
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(text);

                Toast.makeText(mContext, "Link copied to clipboard " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
                clipboard.setPrimaryClip(clip);

                Toast.makeText(mContext, "Link copied to clipboard " + getAdapterPosition(), Toast.LENGTH_SHORT).show();
            }
        }
    }


}
