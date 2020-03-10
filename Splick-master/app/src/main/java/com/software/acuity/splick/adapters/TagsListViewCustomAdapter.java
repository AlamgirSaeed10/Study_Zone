package com.software.acuity.splick.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.software.acuity.splick.R;
import com.software.acuity.splick.interfaces.IChipsChange;
import com.software.acuity.splick.models.TagsModel;

import java.util.ArrayList;
import java.util.List;

public class TagsListViewCustomAdapter extends BaseAdapter implements Filterable {

    Context mContext;
    List<TagsModel> tagsList = new ArrayList<>();
    LayoutInflater layoutInflater;
    IChipsChange iChipsChange;

    public TagsListViewCustomAdapter(Context context, List<TagsModel> tags,
                                     IChipsChange iChipsChange) {
        mContext = context;
        tagsList = tags;
        layoutInflater = LayoutInflater.from(context);
        this.iChipsChange = iChipsChange;
    }

    @Override
    public int getCount() {
        return tagsList.size();
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
        View rowView = layoutInflater.inflate(R.layout.tag_list_row, null);

        TextView tagNameTv = rowView.findViewById(R.id.listRowTagName);
        tagNameTv.setText(tagsList.get(position).getTagName() + "");
        MaterialButton materialButton = rowView.findViewById(R.id.addTag);

        if (tagsList.get(position).getTagStatus()) {
            materialButton.setText("");
            materialButton.setIconResource(R.drawable.green_tick);
            materialButton.setClickable(false);
        }
        else
        {
            materialButton.setText("Add");
            materialButton.setIconResource(0);
        }

        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TagsModel tagsModel = tagsList.get(position);
                tagsModel.setTagStatus(true);
                materialButton.setText("");
                iChipsChange.chipsAdded(tagsModel);
                v.setClickable(false);
            }
        });
        return rowView;
    }

    @Override
    public Filter getFilter() {
        return null;
    }
}
