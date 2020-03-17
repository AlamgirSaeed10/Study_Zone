package com.acuity.Splick.ui.fragments.authentication.signup.userInfo;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.acuity.Splick.models.Auth;
import com.acuity.Splick.models.Register;
import com.acuity.Splick.models.Tag;
import com.acuity.Splick.repositories.TagRepo;
import com.acuity.Splick.repositories.UserRepo;

import java.util.HashMap;

public class SignUpInfoFragmentViewModel extends ViewModel {
    private static final String TAG = "SignUpInfoFragmentViewM";
     MutableLiveData<Register> signUp=new MutableLiveData<>();
     MutableLiveData<Tag> tagMutableLiveData=new MutableLiveData<>();
     MutableLiveData<Register> updateUserLiveData=new MutableLiveData<>();
     UserRepo userRepo;
     TagRepo tagRepo;
     public void setSignUp(String mail,String user_pass,String user_role){
         Log.d(TAG, "setSignUp: ");
         userRepo=UserRepo.getInstance();
         signUp=userRepo.signUp(mail,user_pass,user_role);
     }
     public void updateUser(HashMap<String,Object> updateMap){
         userRepo=UserRepo.getInstance();
         updateUserLiveData=userRepo.updateUser(updateMap);

     }
    public MutableLiveData<Register> setSignUPLiveData(){
        Log.d(TAG, "setSignUPLiveData:");
         return signUp;
    }
    public MutableLiveData<Register> getUpdateUserLiveData(){
        Log.d(TAG, "setSignUPLiveData:");
         return updateUserLiveData;
    }
    public void getTag(){
         tagRepo=TagRepo.getInstance();
         tagMutableLiveData=tagRepo.getTags();
    }
    public MutableLiveData<Tag> getTagMutableLiveData(){
         return tagMutableLiveData;
    }
}
