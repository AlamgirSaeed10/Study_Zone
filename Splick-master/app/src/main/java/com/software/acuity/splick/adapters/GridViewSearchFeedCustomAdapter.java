package com.software.acuity.splick.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.software.acuity.splick.R;
import com.software.acuity.splick.models.business_deals.Business;
import com.software.acuity.splick.utils.UtilsClass;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GridViewSearchFeedCustomAdapter extends RecyclerView.Adapter<GridViewSearchFeedCustomAdapter.SearchViewHolder> {
    private Context mContext;
    private List<Business> mDataSet;
    private LayoutInflater layoutInflater;

    public GridViewSearchFeedCustomAdapter(Context context, List<Business> dataList) {
        this.mDataSet = dataList;
        this.layoutInflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    @NonNull
    @Override
    public GridViewSearchFeedCustomAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate a view and pass it to the view holder class
        View rowView = layoutInflater.inflate(R.layout.grid_cell_portfolio, null);

        GridViewSearchFeedCustomAdapter.SearchViewHolder myViewHolder =
                new GridViewSearchFeedCustomAdapter.SearchViewHolder(rowView);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        if (!mDataSet.get(position).getProfile_path().trim().isEmpty()) {
            UtilsClass.setImageUsingPicasso(mContext, mDataSet.get(position).getProfile_path(), holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageViewGridPortoflio)
        ImageView imageView;


        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
