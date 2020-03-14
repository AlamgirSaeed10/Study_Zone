package com.software.acuity.splick.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.software.acuity.splick.R;
import com.software.acuity.splick.fragments.AddPostFragment;
import com.software.acuity.splick.fragments.DashboardAboutFragment;
import com.software.acuity.splick.fragments.HomeFragment;
import com.software.acuity.splick.fragments.business.BusinessHomeFragment;
import com.software.acuity.splick.fragments.discover.SearchFragment;
import com.software.acuity.splick.fragments.earnings.EarningsFragment;
import com.software.acuity.splick.fragments.partnerships.PartnershipsFragment;
import com.software.acuity.splick.utils.Constants;
import com.software.acuity.splick.utils.SharedPreferenceClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import butterknife.ButterKnife;

public class DashBoardActivity extends AppCompatActivity {

    SharedPreferenceClass sharedPreferenceClass;
    SelectedBundle selectedBundle;
    public static Context dashboardActvityContext;

    public interface SelectedBundle {
        void onBundleSelect(Bundle bundle);
    }
    public void setOnBundleSelected(SelectedBundle selectedBundle) {
        this.selectedBundle = selectedBundle;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        ButterKnife.bind(this);
        dashboardActvityContext = this;
        sharedPreferenceClass = SharedPreferenceClass.getInstance(getApplicationContext());
        Constants.jsonObject = sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottomNavDashboard);
        try {
                JSONObject jsonObject = new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT));
                if (jsonObject.getString("user_role").trim().equalsIgnoreCase("affiliate")) {
                navigation.getMenu().clear();
                navigation.inflateMenu(R.menu.bottom_navigation_menu_affiliate);
            }
                else {
                navigation.getMenu().clear();
                navigation.inflateMenu(R.menu.bottom_navigation_menu_business);
            }
            }
        catch (JSONException e) {
            e.printStackTrace();
        }

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        try {
            if (new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT)).getString("user_role").equalsIgnoreCase("business")) {
                loadFragment(new BusinessHomeFragment());
            }
            else {
                loadFragment(new HomeFragment());
            }
            }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
                new AlertDialog.Builder(DashBoardActivity.this)
                        .setIcon(R.drawable.caution_icon)
                        .setTitle("Logout")
                        .setMessage("Are you sure you want to logout?")
                        .setPositiveButton("Yes", (dialog, which) -> {

                            sharedPreferenceClass.setValues(Constants.USER_JSON_OBJECT, "");
                            sharedPreferenceClass.setValues(Constants.USER_ID, "");
                            sharedPreferenceClass.setValues(Constants.LOGIN_STATUS, "");
                            finish();
                        }).
                        setNegativeButton("No", (dialog, which) -> {
                        }).show();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.dashboardHomeMi:
                        loadFragment(new HomeFragment());
                        return true;

                    case R.id.dashboardHomeBus:
                        loadFragment(new BusinessHomeFragment());
                        return true;

                    case R.id.dashboardSearchMenuItem:
                        loadFragment(new SearchFragment());
                        return true;

                    case R.id.dashboardSearchMenuItemBus:
                        loadFragment(new SearchFragment());
                        return true;

                    case R.id.dasboardPartnershipsMi:
                        loadFragment(new PartnershipsFragment());
                        return true;

                    case R.id.dashboardPartnershipBus:
                        loadFragment(new PartnershipsFragment());
                        return true;

                    case R.id.dashboardEarningMi:
                        loadFragment(new EarningsFragment());
                        return true;

                    case R.id.dashboardAboutMi:
                        loadFragment(new DashboardAboutFragment());
                        return true;

                    case R.id.dashboardAboutMiBus:
                        loadFragment(new DashboardAboutFragment());
                        return true;

                    case R.id.addPostBus:
                        loadFragment(new AddPostFragment());
                        return true;
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2000 && resultCode == RESULT_OK && data != null) {
            Bundle bundle = new Bundle();
            bundle.putString("tags", data.getStringExtra("tags") + "");
            selectedBundle.onBundleSelect(bundle);
        } else if (requestCode == 1074 && resultCode == RESULT_OK) {

            try {
                Uri uri = data.getData();
                String uriString = uri.toString();
                File myFile = new File(uriString);
                String path = myFile.getAbsolutePath();
                String displayName = null;

                if (uriString.startsWith("content://")) {
                    Cursor cursor = null;
                    try {
                        cursor = getContentResolver().query(uri, null, null, null, null);
                        if (cursor != null && cursor.moveToFirst()) {
                            displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                            Bundle bundle = new Bundle();
                            bundle.putString("uri", uri + "");
                            bundle.putString("name", displayName + "");
                            selectedBundle.onBundleSelect(bundle);
                        }
                    } finally {
                        cursor.close();
                    }
                } else if (uriString.startsWith("file://")) {
                    displayName = myFile.getName();
                }
            } catch (NullPointerException e) {

            }
        }
    }
}
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".ui.activities.Dashboard.Main_Dashboard">
    <androidx.appcompat.widget.Toolbar
        android:layout_width="match_parent"
        android:layout_height="@dimen/_40sdp"
        android:background="@color/white_text"
       android:layout_alignParentTop="true"
        android:orientation="vertical"
        android:outlineSpotShadowColor="@color/black_text"
        >
        <RelativeLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            >
            <ImageView
                android:id="@+id/toolbar_image_1"
                android:contentDescription="@string/app_name"
                android:layout_width="@dimen/_30sdp"
                android:layout_height="@dimen/_30sdp"
                android:layout_marginTop="@dimen/_5sdp"
                android:src="@drawable/ic_back_btn"
                />
            <TextView
                android:id="@+id/toolbar_center_tv"
                android:layout_width="wrap_content"
                android:layout_height="match_parent"
                android:text="@string/app_name"
                android:textStyle="bold"
                android:maxWidth="@dimen/_150sdp"
                android:textSize="@dimen/_18sdp"
                android:textColor="@color/black_text"
                android:layout_centerHorizontal="true"
                android:gravity="center|center_vertical"
                />
            <ImageView
                android:id="@+id/toolbar_image_2"
                android:layout_width="@dimen/_30sdp"
                android:layout_height="@dimen/_30sdp"
                android:src="@drawable/ic_chat"
                android:layout_gravity="end|center"
                android:layout_alignParentEnd="true"
                android:layout_marginRight="@dimen/_10sdp"
                android:layout_marginTop="@dimen/_5sdp"
                />
        </RelativeLayout>
    </androidx.appcompat.widget.Toolbar>

    <FrameLayout
        android:id="@+id/mainContainer"
        android:layout_width="match_parent"
        android:layout_marginTop="@dimen/_43sdp"
        android:layout_marginBottom="@dimen/_53sdp"
        android:background="@color/gray_all_bg"
        android:layout_height="match_parent"
        app:layout_behavior="@string/appbar_scrolling_view_behavior"
        tools:context="MainActivity">
    </FrameLayout>

    <com.google.android.material.bottomnavigation.BottomNavigationView
        android:id="@+id/db_bottom_navigation"
        android:layout_width="match_parent"
        android:layout_height="@dimen/_50sdp"
        android:layout_alignParentBottom="true"
        android:background="@color/white_text"
        android:animateLayoutChanges="true"
        />

</RelativeLayout>
  <com.google.android.material.textfield.TextInputLayout
        android:id="@+id/email_sign_in_edt1"
        style="@style/Widget.MaterialComponents.TextInputLayout.OutlinedBox.Dense"
        android:layout_width="match_parent"
        android:layout_below="@id/sign_up_t"
        android:layout_height="wrap_content"
        android:layout_marginTop="@dimen/_70sdp"
        android:layout_marginRight="@dimen/_20sdp"
        android:layout_marginLeft="@dimen/_20sdp"
        app:errorEnabled="true"
        android:hint="Email">
        <com.google.android.material.textfield.TextInputEditText
            android:id="@+id/email_sign_in_edt"
            android:layout_width="match_parent"
            android:layout_height="@dimen/_40sdp"
            android:inputType="textEmailAddress">
        </com.google.android.material.textfield.TextInputEditText>
    </com.google.android.material.textfield.TextInputLayout>

    <com.google.android.material.textfield.TextInputLayout
        android:id="@+id/password_sign_in_edt1"
        style="@style/Widget.MaterialComponents.TextInputLayout.OutlinedBox.Dense"
        android:layout_width="match_parent"
        android:layout_below="@id/email_sign_in_edt1"
        android:layout_height="wrap_content"
        android:layout_marginTop="@dimen/_10sdp"
        android:layout_marginLeft="@dimen/_20sdp"
        android:layout_marginRight="@dimen/_20sdp"
        app:passwordToggleEnabled="true"
        app:errorEnabled="true"
        android:hint="Password">

        <com.google.android.material.textfield.TextInputEditText
            android:id="@+id/password_sign_in_edt"
            android:layout_width="match_parent"
            android:layout_height="@dimen/_40sdp"
            android:inputType="textPassword">

        </com.google.android.material.textfield.TextInputEditText>
    </com.google.android.material.textfield.TextInputLayout>