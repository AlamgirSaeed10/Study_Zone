package com.acuity.Splick.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.acuity.Splick.R;

public class MainActivity extends AppCompatActivity  {
    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        navController = Navigation.findNavController(this, R.id.fragment);
    }

    @Override
    public void onBackPressed() {

    }
}
