package com.acuity.Splick.ui.fragments.authentication.login;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.acuity.Splick.models.Auth;
import com.acuity.Splick.repositories.UserRepo;

public class SignInViewModel extends ViewModel {
    private static final String TAG = "SignInViewModel";
    private MutableLiveData<Auth> loginUser = new MutableLiveData<>();
    private UserRepo userRepo;

    public void checkLogin(String mail,String pass){
        Log.d(TAG, "checkLogin: ");
        userRepo=UserRepo.getInstance();
        loginUser=userRepo.getAuthentication(mail,pass);
    }
    public LiveData<Auth> getUserRepository() {
        return loginUser;
    }
}
