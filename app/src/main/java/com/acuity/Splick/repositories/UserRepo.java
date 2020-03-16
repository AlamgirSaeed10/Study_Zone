package com.acuity.Splick.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.acuity.Splick.apiConfiguration.ApiInterfaces.ApiInterface;
import com.acuity.Splick.apiConfiguration.retofit.ApiClient;
import com.acuity.Splick.models.Auth;
import com.acuity.Splick.models.Register;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepo {
    private static final String TAG = "UserRepo";
   private static final UserRepo userRepo=new UserRepo();
   private ApiInterface apiClient;
    private UserRepo(){}
    public static UserRepo getInstance(){
        return userRepo;
    }
    public void UserRepository(){
    }
  public  MutableLiveData<Auth> getAuthentication(String mail, String pass){
      Log.d(TAG, "getAuthentication: ");
      MutableLiveData authLiveData=new MutableLiveData<>();
      apiClient = ApiClient.createService(ApiInterface.class);
      apiClient.getAuth(mail,pass).enqueue(new Callback<Auth>() {
          @Override
          public void onResponse(Call<Auth> call, Response<Auth> response) {
             if(response.isSuccessful()){
                 Log.d(TAG, "onResponse: ");
                 authLiveData.setValue(response.body());
             }
             else {
                 Log.d(TAG, "onResponse: "+response.message());
             }
          }

          @Override
          public void onFailure(Call<Auth> call, Throwable t) {
              authLiveData.setValue(null);
          }
      });
      return authLiveData;
  }
  public MutableLiveData<Register> signUp(String mail,String user_pass,String user_role){
      Log.d(TAG, "signUp: ");
      MutableLiveData<Register> signUpLiveData=new MutableLiveData<>();
      apiClient=ApiClient.createService(ApiInterface.class);
      apiClient.signUp("abc",mail,user_pass,user_role).enqueue(new Callback<Register>() {
          @Override
          public void onResponse(Call<Register> call, Response<Register> response) {
              if(response.isSuccessful()){
                  Log.d(TAG, "onResponse: "+response.body().getSuccess());
                  signUpLiveData.postValue(response.body());
              }
          }

          @Override
          public void onFailure(Call<Register> call, Throwable t) {
              Log.d(TAG, "onFailure: "+t);
              signUpLiveData.postValue(null);
          }
      });

    return signUpLiveData;
  }
  public MutableLiveData<Register> updateUser(HashMap<String,Object> userUpdate){
        MutableLiveData<Register> updateUserLiveData=new MutableLiveData<>();
        apiClient=ApiClient.createService(ApiInterface.class);
        apiClient.update(userUpdate).enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                if(response.isSuccessful()){
                    Log.d(TAG, "onResponse: "+response.body().getSuccess());
                    updateUserLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t);
                updateUserLiveData.postValue(null);
            }
        });
        return updateUserLiveData;
  }
}
