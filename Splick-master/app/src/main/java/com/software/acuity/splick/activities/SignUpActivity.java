package com.software.acuity.splick.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.facebook.FacebookSdk;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.software.acuity.splick.R;
import com.software.acuity.splick.adapters.SignUpViewPagerAdapter;
import com.software.acuity.splick.fragments.SignUpAboutFragment;
import com.software.acuity.splick.fragments.BiographyFragment;
import com.software.acuity.splick.fragments.CompleteProfileFragment;
import com.software.acuity.splick.fragments.PortfolioFragment;
import com.software.acuity.splick.fragments.SignUpFragment;
import com.software.acuity.splick.fragments.SocialReachFragment;
import com.software.acuity.splick.fragments.TagsFragment;
import com.software.acuity.splick.interfaces.IChangeViewPagerItem;
import com.software.acuity.splick.interfaces.IGetData;
import com.software.acuity.splick.interfaces.ISelectedMedia;
import com.software.acuity.splick.models.common.UserAuthModel;
import com.software.acuity.splick.utils.AppController;
import com.software.acuity.splick.utils.Constants;
import com.software.acuity.splick.utils.SharedPreferenceClass;
import com.software.acuity.splick.views.CustomViewPager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity implements IChangeViewPagerItem, IGetData {

    @BindView(R.id.toolbarSignUp)
    Toolbar toolbar;

    @BindView(R.id.viewPagerSignUp)
    CustomViewPager viewPagerSignUp;

    SignUpViewPagerAdapter signUpViewPagerAdapter;

    UserAuthModel userAuthModel;

    SharedPreferenceClass sharedPreferenceClass;

    ISelectedMedia iSelectedMedia;

    public static SignUpActivity signUpActivityContext;

    public void setSelectedMediaListener(ISelectedMedia iSelectedMedia) {
        this.iSelectedMedia = iSelectedMedia;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        FacebookSdk.sdkInitialize(this.getApplicationContext());

        ButterKnife.bind(this);

        signUpActivityContext = SignUpActivity.this;
        toolbarInit();
        viewPagerInit();
        viewPagerSignUp.setPagingEnabled(false);
        userAuthModel = new UserAuthModel();
        sharedPreferenceClass = SharedPreferenceClass.getInstance(getApplicationContext());
    }

    public void toolbarInit() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void viewPagerInit() {
        signUpViewPagerAdapter = new SignUpViewPagerAdapter(getSupportFragmentManager(),getFragmentsList());
        viewPagerSignUp.setAdapter(signUpViewPagerAdapter);
        viewPagerSignUp.setOffscreenPageLimit(0);
    }

    public List<Fragment> getFragmentsList() {

        List<Fragment> fragmentList = new ArrayList<>();

        fragmentList.add(new SignUpFragment(getApplicationContext(), SignUpActivity.this));
        fragmentList.add(new SignUpAboutFragment(getApplicationContext(), SignUpActivity.this));
        fragmentList.add(new TagsFragment(getApplicationContext(), SignUpActivity.this));
        fragmentList.add(new BiographyFragment(getApplicationContext(), SignUpActivity.this));
        fragmentList.add(new PortfolioFragment(getApplicationContext(), SignUpActivity.this));
        fragmentList.add(new CompleteProfileFragment(getApplicationContext(), SignUpActivity.this));
        return fragmentList;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void changeViewPagerItem(String object) {

        try {

            Log.d("obj_str", object + "");
            if (!object.equalsIgnoreCase("complete"))
                viewPagerSignUp.setCurrentItem(signUpViewPagerAdapter.getItemPosition(object));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case Constants.GALLERY_INTENT_REQUEST_CODE:

                if(resultCode == RESULT_OK && data != null){
                    iSelectedMedia.selectedMedia(data);
                }
                break;

            case Constants.CAMERA_INTENT_REQUEST_CODE:
                break;

            default:
                SocialReachFragment.callbackManager.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public static byte[] getFileDataFromDrawable(Context context, Uri uri) {
        // Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            InputStream iStream = context.getContentResolver().openInputStream(uri);
            int bufferSize = 2048;
            byte[] buffer = new byte[bufferSize];

            // we need to know how may bytes were read to write them to the byteBuffer
            int len = 0;
            if (iStream != null) {
                while ((len = iStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, len);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //  bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    // Get Path of selected image
    private String getPath(Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    @Override
    public void getData(String dataString, String fragmentTag) {
        switch (fragmentTag) {
            case "about":
                userAuthModel.setUserFullName(dataString.split(",")[0]);
                userAuthModel.setUsername(dataString.split(",")[1]);
                userAuthModel.setUserLocation(dataString.split(",")[2]);
                break;

            case "tags":
                userAuthModel.setUserTags(dataString);
                break;

            case "bio":
                userAuthModel.setUserBiography(dataString);
                updateProfile();
                break;

            case "social":
                //Put data into model
                break;

            case "portfolio":
                viewPagerSignUp.setCurrentItem(signUpViewPagerAdapter.getItemPosition("complete"));
                break;

        }
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
    }

    public void updateProfile() {
        ProgressDialog progressDialog = ProgressDialog.show(SignUpActivity.this, "Updating " +
                "Profile", "Please wait! Profile update is in progress.");

        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                Constants.BASE_URL + Constants.API_PROFILE_UPDATE + "",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {

//                            String[] data = response.split("</div>");
//                            if (data.length > 0) {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("success")) {

//                                sharedPreferenceClass.setValues(Constants.USER_ID,
//                                        jsonObject.getString("data"));
                                new MaterialAlertDialogBuilder(SignUpActivity.this,
                                        R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                                        .setTitle("Updating Profile")
                                        .setMessage("User profile has been updated " +
                                                "successfully!")
                                        .setPositiveButton("Ok",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog,
                                                                        int which) {
                                                        viewPagerSignUp.setCurrentItem(signUpViewPagerAdapter.getItemPosition("portfolio"));
                                                    }
                                                })
                                        .show();
                            } else {
//                                Toast.makeText(SignUpActivity.this, jsonObject.getString("msg"
//                                ) + "", Toast.LENGTH_SHORT).show();
                                new MaterialAlertDialogBuilder(SignUpActivity.this,
                                        R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                                        .setTitle("Updating profile")
                                        .setMessage(jsonObject.getString("msg") + "")
                                        .setPositiveButton("Ok", null)
                                        .show();
                            }
                            Log.d("update_profile", response.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("update_profile", "Error: " + error.getMessage());

                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("full_name", userAuthModel.getUserFullName() + "");
                params.put("user_location", userAuthModel.getUserLocation() + "");
                params.put("user_bio", userAuthModel.getUserBiography() + "");
                params.put("user_id", sharedPreferenceClass.getValues(Constants.USER_ID));
                params.put("industry_tags", userAuthModel.getUserTags() + "");
                params.put("user_role", sharedPreferenceClass.getValues(Constants.USER_TYPE) + "");

                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, "update_profile");
    }
}
