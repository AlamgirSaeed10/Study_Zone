package com.acuity.Splick.repositories;

import android.net.Uri;
import android.os.FileUtils;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.acuity.Splick.apiConfiguration.ApiInterfaces.ApiInterface;
import com.acuity.Splick.apiConfiguration.retofit.ApiClient;
import com.acuity.Splick.models.Register;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MediaRepo {
    private static final String TAG = "MediaRepo";
    private static MediaRepo mediaRepo=new MediaRepo();
    private ApiInterface apiInterface;
    public static MediaRepo getInstance(){
        return mediaRepo;
    }

    public MutableLiveData<Register> addMedia(int userID, Uri imagPath){
        MutableLiveData<Register> registerMutableLiveData=new MutableLiveData<>();
        apiInterface= ApiClient.createService(ApiInterface.class);
        RequestBody descriptionPart=RequestBody.create(MultipartBody.FORM,"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.");
       //Todo convert to file
        File file=new File(imagPath.getPath());
        RequestBody filepart=RequestBody.create(MediaType.parse("image/*"),file);

        MultipartBody.Part part= MultipartBody.Part.createFormData("media_item",file.getName(),filepart);

        apiInterface.addPortfolio(userID,descriptionPart,part).enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: ");
                    registerMutableLiveData.postValue(response.body());
                }
                else {
                    Log.d(TAG, "onResponse: ");
                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t);
              registerMutableLiveData.postValue(null);
            }
        });

        return registerMutableLiveData;
    }

}
