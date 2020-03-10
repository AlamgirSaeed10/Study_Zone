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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.software.acuity.splick.R;
import com.software.acuity.splick.fragments.SocialReachFragment;
import com.software.acuity.splick.interfaces.IVolleyMultipartResponse;
import com.software.acuity.splick.utils.Constants;
import com.software.acuity.splick.utils.SharedPreferenceClass;
import com.software.acuity.splick.utils.UtilsClass;
import com.software.acuity.splick.utils.VolleyMultipartRequest;
import com.software.acuity.splick.utils.VolleyMultpartRequestClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MarketingMaterialActivity extends AppCompatActivity implements IVolleyMultipartResponse {

    public static final int MY_PERMISSIONS_REQUEST_READ_WRITE_EXTERNAL_STORAGE = 1000;
    @BindView(R.id.chooseImageBtn)
    ImageView chooseImageBtn;

    @BindView(R.id.btnAddPost)
    ImageButton uploadBtn;

    @BindView(R.id.close_btn)
    ImageButton closeBtn;

    @BindView(R.id.materialTitle)
    EditText materialTitle;

    @BindView(R.id.materialDetails)
    EditText materialDetails;

    @BindView(R.id.materialDealId)
    EditText materialDealId;

    Uri uri = null;
    String displayName = "";

    private SharedPreferenceClass sharedPreferenceClass;

    private VolleyMultpartRequestClass volleyMultpartRequestClass;
    private String requestTag = "add_new_post";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketing_material);

        ButterKnife.bind(this);

        sharedPreferenceClass = SharedPreferenceClass.getInstance(getApplicationContext());

        chooseImageBtn.setOnClickListener(new View.OnClickListener() {
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

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!uri.toString().isEmpty() || !materialTitle.getText().toString().trim().isEmpty() ||
                        !materialDealId.getText().toString().trim().isEmpty()) {
                    uploadFile(displayName, uri);
                } else {
                    Toast.makeText(MarketingMaterialActivity.this, "Please select image", Toast.LENGTH_SHORT).show();
                }
            }
        });

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

                                chooseImageBtn.setImageURI(uri);
//                            uploadFile(displayName, uri);
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
                    SocialReachFragment.callbackManager.onActivityResult(requestCode, resultCode, data);
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
            multiPartParams.put("media_file", new VolleyMultipartRequest.DataPart(displayName, inputData));

            //Provide other parameters here
            HashMap<String, String> params = new HashMap<String, String>();

            JSONObject userObject = new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT));

            params.put("mat_title", "test title");
            params.put("mat_size", materialTitle.getText().toString().trim() + "");
            params.put("mat_details", materialDetails.getText().toString().trim() + "");
            params.put("user_id", userObject.getString("id") + "");
            params.put("id", materialDealId.getText().toString() + "");


            volleyMultpartRequestClass = new VolleyMultpartRequestClass(MarketingMaterialActivity.this, MarketingMaterialActivity.this, params, multiPartParams, "Adding post",
                    "Please wait " +
                            "while " +
                            "media is uploading", requestTag, Request.Method.POST);

            volleyMultpartRequestClass.volleyServiceCall(Constants.BASE_URL + Constants.API_BUSINESS_ADD_MATERIAL);
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
                    new MaterialAlertDialogBuilder(MarketingMaterialActivity.this,
                            R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                            .setTitle("Marketing Material")
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
                    new MaterialAlertDialogBuilder(MarketingMaterialActivity.this,
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
            }
            if (jsonObject.getString("status").equals("true")) {
                Log.d("come::: >>>  ", "yessssss");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
