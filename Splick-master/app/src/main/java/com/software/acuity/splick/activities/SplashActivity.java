package com.software.acuity.splick.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.software.acuity.splick.R;
import com.software.acuity.splick.utils.Constants;
import com.software.acuity.splick.utils.SharedPreferenceClass;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    SharedPreferenceClass sharedPreferenceClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sharedPreferenceClass = SharedPreferenceClass.getInstance(getApplicationContext());

//        printKeyHash();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

//                        String val = Integer.parseInt(sharedPreferenceClass.getValues("app_init")) + "";
                        if (sharedPreferenceClass.getValues(Constants.APP_TURN).trim().equalsIgnoreCase("")) {
                            startActivity(new Intent(SplashActivity.this,
                                    OnBoardingActivity.class));
                            sharedPreferenceClass.setValues(Constants.APP_TURN, "1");
                            finish();
                        } else {
                            try {
                                if (sharedPreferenceClass.getValues(Constants.USER_ID).trim().equalsIgnoreCase("")) {
                                    startActivity(new Intent(SplashActivity.this,
                                            SelectUserType.class));
                                    finish();
                                } else {
                                    if (sharedPreferenceClass.getValues(Constants.LOGIN_STATUS).trim().equalsIgnoreCase("")) {
                                        startActivity(new Intent(SplashActivity.this,
                                                SelectUserType.class));
                                        finish();
                                    } else {
                                        startActivity(new Intent(SplashActivity.this,
                                                DashBoardActivity.class));
                                        finish();
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });
            }
        }, 2000);
    }

    private void printKeyHash() {
        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.software.acuity.splick", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("KeyHash:", e.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("KeyHash:", e.toString());
        }
    }
}
