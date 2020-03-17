package com.acuity.Splick.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.acuity.Splick.apiConfiguration.ApiInterfaces.ApiInterface;
import com.acuity.Splick.apiConfiguration.retofit.ApiClient;
import com.acuity.Splick.models.Tag;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TagRepo {
    private static final String TAG = "TagRepo";
    ApiInterface apiClient;
    private static TagRepo tagRepo=new TagRepo();
    public static TagRepo getInstance(){
        return tagRepo;
    }
    public MutableLiveData<Tag> getTags(){
        MutableLiveData<Tag> tagMutableLiveData=new MutableLiveData<>();
        apiClient= ApiClient.createService(ApiInterface.class);
        apiClient.getTags().enqueue(new Callback<Tag>() {
            @Override
            public void onResponse(Call<Tag> call, Response<Tag> response) {
                if(response.body().isSuccess()){
                    Log.d(TAG, "onResponse: ");
                    tagMutableLiveData.postValue(response.body());
                }

            }

            @Override
            public void onFailure(Call<Tag> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t);
                tagMutableLiveData.postValue(null);
            }
        });
        return tagMutableLiveData;
    }
}
