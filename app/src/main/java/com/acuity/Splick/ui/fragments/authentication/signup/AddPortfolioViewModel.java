package com.acuity.Splick.ui.fragments.authentication.signup;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.acuity.Splick.models.Register;
import com.acuity.Splick.repositories.MediaRepo;

import java.io.File;

public class AddPortfolioViewModel extends ViewModel {
    private MutableLiveData<Register>mutableLiveData=new MutableLiveData<>();
    private MediaRepo mediaRepo;
    public void addImage(Integer userID, String file){
        mediaRepo=MediaRepo.getInstance();
        mutableLiveData=mediaRepo.addMedia(userID,file);
    }

    public MutableLiveData<Register> getMutableLiveMedia() {
        return mutableLiveData;
    }

}
