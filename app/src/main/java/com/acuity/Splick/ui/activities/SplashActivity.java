package com.acuity.Splick.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.acuity.Splick.R;
import com.acuity.Splick.ui.activities.Dashboard.Main_Dashboard;
import com.acuity.Splick.ui.activities.onBoardScreen.IntroActivity;
import com.acuity.Splick.util.PrefUtil;

import butterknife.internal.Utils;
import es.dmoral.toasty.Toasty;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
                splashHandler();
    }
    private void splashHandler() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              SharedPreferences prefs = getSharedPreferences("checkInstallation", MODE_PRIVATE);
                if (prefs.getBoolean("firstrun", true)) {
                    Intent intent = new Intent(SplashActivity.this, IntroActivity.class);
                    startActivity(intent);
                    prefs.edit().putBoolean("firstrun", false).apply();
                }
               else if(String.valueOf(PrefUtil.getUserInformation(getApplicationContext()).getId())=="")
               {
                   Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                   startActivity(intent);
               }
                else {
                    startActivity(new Intent(SplashActivity.this,Main_Dashboard.class));
                }

                finish();
            }
        }, 3000);
    }

}
