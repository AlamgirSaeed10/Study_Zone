package com.acuity.Splick.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.acuity.Splick.models.User;
import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

public class PrefUtil {
    private static final String TAG = "PrefUtil";


    public static void saveUser(User user, Context context) {
        SharedPreferences preferences = context.getSharedPreferences(Constant.DATA_BASE_Pref, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson=new Gson();
        String userObj=gson.toJson(user);
        editor.putString(Constant.USER_DATA,userObj);
        editor.apply();
        Log.d(TAG, "saveUser: ");
    }

    public static User getUserInformation(Context context){
        SharedPreferences preferences = context.getSharedPreferences(Constant.DATA_BASE_Pref, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString(Constant.USER_DATA, "");
        User obj = gson.fromJson(json, User.class);
        return obj;
    }
    

}
