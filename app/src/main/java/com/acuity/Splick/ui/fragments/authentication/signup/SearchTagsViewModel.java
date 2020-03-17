package com.acuity.Splick.ui.fragments.authentication.signup;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.acuity.Splick.models.Tag;
import com.acuity.Splick.repositories.TagRepo;

public class SearchTagsViewModel extends ViewModel {
    TagRepo tagRepo;
    MutableLiveData<Tag> tagMutableLiveData;
    public void getTag(){
        tagRepo= TagRepo.getInstance();
        tagMutableLiveData=tagRepo.getTags();
    }
    public MutableLiveData<Tag> getTagMutableLiveData(){
        return tagMutableLiveData;
    }
}
