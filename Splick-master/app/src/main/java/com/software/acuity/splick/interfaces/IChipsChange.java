package com.software.acuity.splick.interfaces;

import android.view.View;

import com.software.acuity.splick.models.TagsModel;

public interface IChipsChange {

    void chipsChanged(View view, int position);
    void chipsRemoved(View view, int position);
    void chipsAdded(TagsModel tagsModel);
}
