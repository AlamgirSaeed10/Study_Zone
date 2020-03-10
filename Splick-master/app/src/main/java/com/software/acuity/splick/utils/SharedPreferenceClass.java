package com.software.acuity.splick.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceClass {

    // static variable single_instance of type Singleton
    private static SharedPreferenceClass single_instance = null;

    //Shared Preference and Shared Preference Editor
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedPreferenceEditor;

    // private constructor restricted to this class itself
    private SharedPreferenceClass(Context _context) {
        sharedPreferences = _context.getSharedPreferences(Constants.PREFERENCE_NAME, _context.MODE_PRIVATE);
        sharedPreferenceEditor = sharedPreferences.edit();
    }

    public void setValues(String _key, String _value) {
        sharedPreferenceEditor.putString(_key, _value);
        sharedPreferenceEditor.commit();
    }

    public String getValues(String _key) {
        return sharedPreferences.getString(_key, "");
    }

    // static method to create instance of Singleton class
    public static SharedPreferenceClass getInstance(Context _context) {
        if (single_instance == null)
            single_instance = new SharedPreferenceClass(_context);

        return single_instance;
    }
}
