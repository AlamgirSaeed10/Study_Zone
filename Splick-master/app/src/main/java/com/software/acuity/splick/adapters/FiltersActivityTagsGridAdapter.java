package com.software.acuity.splick.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.software.acuity.splick.R;
import com.software.acuity.splick.interfaces.IChipsChange;
import com.software.acuity.splick.models.TagsModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FiltersActivityTagsGridAdapter extends RecyclerView.Adapter<FiltersActivityTagsGridAdapter.MyViewHolder> {

    List<TagsModel> mTagsList;
    LayoutInflater layoutInflater;
    Context mContext;
    IChipsChange iChipsChange;

    public FiltersActivityTagsGridAdapter(Context context, List<TagsModel> tagsList,
                                          IChipsChange iChipsChange) {
        mTagsList = tagsList;
        mContext = context;
        layoutInflater = LayoutInflater.from(context);
        this.iChipsChange = iChipsChange;
    }

    public FiltersActivityTagsGridAdapter(Context context, List<TagsModel> tagsList) {
        mTagsList = tagsList;
        mContext = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myViewHolder = layoutInflater.inflate(R.layout.row_tags_chip_filter, null);

        MyViewHolder dealsViewHolder = new MyViewHolder(myViewHolder);

        return dealsViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.chip.setText(mTagsList.get(position).getTagName() + "");
        holder.chip.setCloseIcon(null);
        holder.chip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iChipsChange.chipsRemoved(v, position);
            }
        });

        holder.chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                iChipsChange.chipsChanged(buttonView, position);
            }
        });
    }

    public void dataChanged(List<TagsModel> mTagsList) {
        this.mTagsList = mTagsList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mTagsList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tagChip)
        Chip chip;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
