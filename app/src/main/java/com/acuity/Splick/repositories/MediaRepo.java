package com.acuity.Splick.repositories;

import androidx.lifecycle.MutableLiveData;

import com.acuity.Splick.apiConfiguration.ApiInterfaces.ApiInterface;
import com.acuity.Splick.apiConfiguration.retofit.ApiClient;
import com.acuity.Splick.models.Register;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MediaRepo {
    private static MediaRepo mediaRepo=new MediaRepo();
    private ApiInterface apiInterface;
    public static MediaRepo getInstance(){
        return mediaRepo;
    }

    public MutableLiveData<Register> addMedia(String userID, File file){
        MutableLiveData<Register> registerMutableLiveData=new MutableLiveData<>();
        apiInterface= ApiClient.createService(ApiInterface.class);
        apiInterface.addPortfolio(userID,"xyz",file).enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                if(response.isSuccessful()){
                    registerMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
              registerMutableLiveData.postValue(null);
            }
        });

        return registerMutableLiveData;
    }

}
