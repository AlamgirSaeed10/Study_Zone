package com.software.acuity.splick.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.software.acuity.splick.R;
import com.software.acuity.splick.fragments.SocialReachFragment;
import com.software.acuity.splick.fragments.discover.profile.DiscoverProfileNewsFeedFragment;
import com.software.acuity.splick.interfaces.IVolleyMultipartResponse;
import com.software.acuity.splick.interfaces.IVolleyResponse;
import com.software.acuity.splick.utils.Constants;
import com.software.acuity.splick.utils.SharedPreferenceClass;
import com.software.acuity.splick.utils.UtilsClass;
import com.software.acuity.splick.utils.VolleyMultipartRequest;
import com.software.acuity.splick.utils.VolleyMultpartRequestClass;
import com.software.acuity.splick.utils.VolleyRequestClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateProfileActivity extends AppCompatActivity implements IVolleyMultipartResponse, IVolleyResponse {

    @BindView(R.id.chooseImageBtn)
    CircleImageView circleImageView;

    @BindView(R.id.cancelUpdateBtn)
    MaterialButton cancelBtn;

    @BindView(R.id.saveUpdateProfileBtn)
    MaterialButton saveProfileBtn;

    @BindView(R.id.upFullNameEt)
    EditText upFullNameEt;

    @BindView(R.id.upUsernameEt)
    EditText upUsernameEt;

    @BindView(R.id.upLocationEt)
    EditText upLocationEt;

    @BindView(R.id.upBiogtrahy)
    EditText upBiogtrahyEt;

    @BindView(R.id.upEditTagsBtn)
    MaterialButton editTagsBtn;

    @BindView(R.id.edit_news_feed_business)
    MaterialButton edit_news_feed_business;

    @BindView(R.id.connect_to_instagram)
    MaterialButton connect_to_instagram;

    @BindView(R.id.connect_to_facebook)
    MaterialButton connect_to_facebook;

    @BindView(R.id.connect_to_twitter)
    MaterialButton connect_to_twitter;

    @BindView(R.id.connect_to_youtube)
    MaterialButton connect_to_youtube;

    @BindView(R.id.connect_to_snapchat)
    MaterialButton connect_to_snapchat;


    public static final int MY_PERMISSIONS_REQUEST_READ_WRITE_EXTERNAL_STORAGE = 1000;
    public static final int TAGS_ACTIVITY_REQUEST_CODE = 2001;

    Uri uri = null;
    JSONObject userObject = null;
    String displayName = "";
    private VolleyMultpartRequestClass volleyMultpartRequestClass;
    private SharedPreferenceClass sharedPreferenceClass;
    private String requestTag = "update_profile";
    private VolleyRequestClass volleyRequestClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        ButterKnife.bind(this);

        sharedPreferenceClass = SharedPreferenceClass.getInstance(getApplicationContext());

        try {
            userObject = new JSONObject(Constants.jsonObject);

            upFullNameEt.setText(userObject.getString("full_name"));
            upUsernameEt.setText(userObject.getString("user_name"));
            upLocationEt.setText(userObject.getString("user_location"));
            upBiogtrahyEt.setText(userObject.getString("user_bio"));


            if (!userObject.getString("thumbnail_url").isEmpty())
                UtilsClass.setImageUsingPicasso(getApplicationContext(), userObject.getString("thumbnail_url").trim(), circleImageView);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasGalleryPermissions()) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivityForResult(Intent.createChooser(intent, "Select Portfolio"), Constants.GALLERY_INTENT_REQUEST_CODE);
                }
            }
        });

        editTagsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateProfileActivity.this, EditTagsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityForResult(intent, 2001);
            }
        });
        edit_news_feed_business.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UpdateProfileActivity.this, "Under Development in DiscoverProfileFeedbackFragment", Toast.LENGTH_SHORT).show();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfileActivity();
    //            uploadFile("",uri);
            }
        });

        connect_to_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UpdateProfileActivity.this, "Under Development in DiscoverProfileFeedbackFragment", Toast.LENGTH_SHORT).show();
            }
        });
        connect_to_twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UpdateProfileActivity.this, "Under Development in DiscoverProfileFeedbackFragment", Toast.LENGTH_SHORT).show();
            }
        });
        connect_to_youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UpdateProfileActivity.this, "Under Development in DiscoverProfileFeedbackFragment", Toast.LENGTH_SHORT).show();
            }
        });
        connect_to_snapchat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UpdateProfileActivity.this, "Under Development in DiscoverProfileFeedbackFragment", Toast.LENGTH_SHORT).show();
            }
        });
        connect_to_instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UpdateProfileActivity.this, "Under Development in DiscoverProfileFeedbackFragment", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void updateProfileActivity() {

        HashMap<String, String> params = new HashMap<String, String>();
        try {
            params.put("full_name", upFullNameEt.getText().toString().trim() + "");
            params.put("user_location", upLocationEt.getText().toString().trim() + "");
            params.put("username", upUsernameEt.getText().toString().trim() + "");
            params.put("user_bio", upBiogtrahyEt.getText().toString().trim() + "");
            params.put("user_id", userObject.getString("id"));
            params.put("industry_tags", userObject.getString("industry_tags") + "");
            params.put("user_role", sharedPreferenceClass.getValues(Constants.USER_TYPE) + "");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        volleyRequestClass = new VolleyRequestClass(UpdateProfileActivity.this, UpdateProfileActivity.this,
                params, "Updating Profile", "Updating Profile!", requestTag, Request.Method.POST);

        volleyRequestClass.volleyServiceCall(Constants.BASE_URL + Constants.API_PROFILE_UPDATE);

    }

    private boolean hasGalleryPermissions() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_WRITE_EXTERNAL_STORAGE);
            }

            return false;
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivityForResult(Intent.createChooser(intent, "Select Portfolio"), Constants.GALLERY_INTENT_REQUEST_CODE);
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            switch (requestCode) {
                case Constants.GALLERY_INTENT_REQUEST_CODE:
                    uri = data.getData();
                    String uriString = uri.toString();
                    File myFile = new File(uriString);
                    String path = myFile.getAbsolutePath();
                    displayName = null;

                    if (uriString.startsWith("content://")) {
                        Cursor cursor = null;
                        try {
                            cursor = getContentResolver().query(uri, null, null, null, null);
                            if (cursor != null && cursor.moveToFirst()) {
                                displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                                Log.d("nameeeee>>>>  ", displayName);

                                circleImageView.setImageURI(uri);
                                uploadFile(displayName, uri);
                            }
                        } finally {
                            cursor.close();
                        }
                    } else if (uriString.startsWith("file://")) {
                        displayName = myFile.getName();
                        Log.d("nameeeee>>>>  ", displayName);
                    }
                    break;

                case Constants.CAMERA_INTENT_REQUEST_CODE:
                    break;

                default:
                    //SocialReachFragment.callbackManager.onActivityResult(requestCode, resultCode, data);
                   // Toast.makeText(this, "default"+requestCode, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    public void uploadFile(String displayName, Uri file) {
        InputStream iStream = null;

        try {
            iStream = getContentResolver().openInputStream(file);
            final byte[] inputData = UtilsClass.getBytes(iStream);

            //Provide multipart media
            HashMap<String, VolleyMultipartRequest.DataPart> multiPartParams = new HashMap<>();
            multiPartParams.put("blog_post", new VolleyMultipartRequest.DataPart(displayName, inputData));

            //Provide other parameters here
            HashMap<String, String> params = new HashMap<String, String>();

            JSONObject userObject = new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT));

            params.put("business_id", userObject.getString("id") + "");

            volleyMultpartRequestClass = new VolleyMultpartRequestClass(UpdateProfileActivity.this, UpdateProfileActivity.this, params, multiPartParams, "Updating Profile",
                    "Please wait " +
                            "while " +
                            "we are updating your profile", requestTag, Request.Method.POST);

            volleyMultpartRequestClass.volleyServiceCall(Constants.BASE_URL + Constants.API_PROFILE_UPDATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void networkResponse(NetworkResponse response) {
        try {
            JSONObject jsonObject = new JSONObject(new String(response.data));

            jsonObject.toString().replace("\\\\", "");

            if (jsonObject.getBoolean("success")) {
                if (jsonObject.has("msg")) {
                    new MaterialAlertDialogBuilder(UpdateProfileActivity.this,
                            R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                            .setTitle("Portfolio")
                            .setMessage(jsonObject.getString("msg"))
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                            .show();
                }
            } else {
                if (jsonObject.has("msg")) {
                    new MaterialAlertDialogBuilder(UpdateProfileActivity.this,
                            R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                            .setTitle("Portfolio")
                            .setMessage(jsonObject.getString("msg"))
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();
                }
            }
            if (jsonObject.getString("status").equals("true")) {
                Log.d("come::: >>>  ", "yessssss");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void networkResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getBoolean("success")) {
                new MaterialAlertDialogBuilder(UpdateProfileActivity.this,
                        R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                        .setTitle("Updating Profile")
                        .setMessage("User profile has been updated " +
                                "successfully!")
                        .setPositiveButton("Ok",
                                (dialog, which) -> {

                                    try {
                                        userObject.put("full_name", upFullNameEt.getText().toString().trim() + "");
                                        userObject.put("user_location", upLocationEt.getText().toString().trim() + "");
                                        userObject.put("username", upUsernameEt.getText().toString().trim() + "");
                                        userObject.put("user_bio", upBiogtrahyEt.getText().toString().trim() + "");
                                        userObject.put("user_id", userObject.getString("id"));
                                        userObject.put("industry_tags", userObject.getString("industry_tags") + "");
                                        userObject.put("user_role", sharedPreferenceClass.getValues(Constants.USER_TYPE) + "");

                                        Constants.jsonObject = userObject.toString();

                                        sharedPreferenceClass.setValues(Constants.USER_JSON_OBJECT, Constants.jsonObject);

                                        finish();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                })
                        .show();
            } else {
                new MaterialAlertDialogBuilder(UpdateProfileActivity.this,
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

    }
}
