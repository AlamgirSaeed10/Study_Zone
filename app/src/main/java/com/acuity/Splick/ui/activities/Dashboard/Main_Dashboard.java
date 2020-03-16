package com.acuity.Splick.ui.activities.Dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.acuity.Splick.R;
import com.acuity.Splick.ui.fragments.Affiliate_Dashboard.Discover.Affiliate_Discover_Page;
import com.acuity.Splick.ui.fragments.Affiliate_Dashboard.Home.Affiliate_Home_Page;
import com.acuity.Splick.ui.activities.MainActivity;
import com.acuity.Splick.ui.fragments.Affiliate_Dashboard.Partnership.Affiliate_Partnership;
import com.acuity.Splick.util.Constant;
import com.acuity.Splick.util.PrefUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class Main_Dashboard extends AppCompatActivity {


    @BindView(R.id.toolbar_image_1)
    ImageView toolbar_image_1;
    @BindView(R.id.toolbar_image_2)
    ImageView toolbar_image_2;
    @BindView(R.id.toolbar_center_tv)
    TextView toolbar_center_tv;
    boolean doubleBackToExitPressedOnce = false;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main__dashboard);
        ButterKnife.bind(this);
        BottomNavigationView navigationView = findViewById(R.id.db_bottom_navigation);
        if(PrefUtil.getUserInformation(getApplicationContext()).getUserRole().equalsIgnoreCase("affiliate")) {
            navigationView.setOnNavigationItemSelectedListener(affiliate_listener);
            toolbar_center_tv.setText(""+PrefUtil.getUserInformation(getApplicationContext()).getUserRole());
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.bottom_navigation_menu_affiliate);
        }else if(PrefUtil.getUserInformation(getApplicationContext()).getUserRole().equalsIgnoreCase("business")){
            navigationView.setOnNavigationItemSelectedListener(business_listener);
            toolbar_center_tv.setText(""+PrefUtil.getUserInformation(getApplicationContext()).getUserRole());
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.bottom_navigation_menu_business);
        }

    }
    BottomNavigationView.OnNavigationItemSelectedListener affiliate_listener
            = item -> {
        switch (item.getItemId()) {
            case R.id.affiliate_home:
                Toasty.success(getApplicationContext(),"Affiliate Home",Toasty.LENGTH_LONG,true).show();
                loadFragment(new Affiliate_Home_Page());
                return true;
            case R.id.affiliate_search:
                Toasty.success(getApplicationContext(),"Affiliate Search",Toasty.LENGTH_LONG,true).show();
                loadFragment(new Affiliate_Discover_Page());
                return true;
            case R.id.affiliate_partnership:
                Toasty.success(getApplicationContext(),"Affiliate Partnership",Toasty.LENGTH_LONG,true).show();
                loadFragment(new Affiliate_Partnership());
                return true;
            case R.id.affiliate_earning:
                Toasty.success(getApplicationContext(),"Affiliate Earning",Toasty.LENGTH_LONG,true).show();
                return true;
            case R.id.affiliate_profile:
                Toasty.success(getApplicationContext(),"Affiliate Profile",Toasty.LENGTH_LONG,true).show();
                return true;
        }
        return false;
    };

    BottomNavigationView.OnNavigationItemSelectedListener business_listener
            = item -> {
        switch (item.getItemId()) {
            case R.id.business_home:
                Toasty.success(getApplicationContext(), "Home", Toasty.LENGTH_LONG, true).show();
                return true;
            case R.id.business_search:
                Toasty.success(getApplicationContext(), "Search", Toasty.LENGTH_LONG, true).show();
                return true;
            case R.id.business_partnership:
                Toasty.success(getApplicationContext(), "Partnership", Toasty.LENGTH_LONG, true).show();
                return true;
            case R.id.business_add_post:
                Toasty.success(getApplicationContext(), "Add post", Toasty.LENGTH_LONG, true).show();
                return true;
            case R.id.business_profile:
                Toasty.success(getApplicationContext(), "Profile", Toasty.LENGTH_LONG, true).show();
                return true;
            default:
                Toasty.error(getApplicationContext(), "no found", Toasty.LENGTH_SHORT, true).show();
        }
                return false;
    };
    private void loadFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }



    @Override
    protected void onResume() {
        super.onResume();
        toolbar_image_1.setOnClickListener(v -> {
            //TODO dialog box for conformation
            SharedPreferences sharedPreferences=getSharedPreferences(Constant.DATA_BASE_Pref,0);
            sharedPreferences.edit().remove(Constant.USER_DATA);
            startActivity(new Intent(Main_Dashboard.this, MainActivity.class));
        });
        this.doubleBackToExitPressedOnce=false;
    }

    @Override
    public void onBackPressed()
    {
        if (doubleBackToExitPressedOnce) {

        }
        this.doubleBackToExitPressedOnce = true;
        //Todo:show dialog box to conform exit from application
        Toast.makeText(this,"Exit from application" , Toast.LENGTH_SHORT).show();
    }
    }

