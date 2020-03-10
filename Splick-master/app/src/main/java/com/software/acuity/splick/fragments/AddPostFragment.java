package com.software.acuity.splick.fragments;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.software.acuity.splick.R;
import com.software.acuity.splick.activities.DashBoardActivity;
import com.software.acuity.splick.interfaces.IVolleyMultipartResponse;
import com.software.acuity.splick.utils.Constants;
import com.software.acuity.splick.utils.SharedPreferenceClass;
import com.software.acuity.splick.utils.UtilsClass;
import com.software.acuity.splick.utils.VolleyMultipartRequest;
import com.software.acuity.splick.utils.VolleyMultpartRequestClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddPostFragment extends Fragment implements IVolleyMultipartResponse {
    public static final int MY_PERMISSIONS_REQUEST_READ_WRITE_EXTERNAL_STORAGE = 1074;
    @BindView(R.id.chooseImageBtn)
    ImageView chooseImageBtn;

    @BindView(R.id.btnAddPost)
    ImageButton uploadBtn;
//    @BindView(R.id.close_btn)
//    ImageButton closeBtn;

    @BindView(R.id.postCaptionEt)
    EditText captionEt;

    @BindView(R.id.postDescriptionEt)
    EditText postDescEt;

    Uri uri = null;
    String displayName = "";

    private SharedPreferenceClass sharedPreferenceClass;

    private VolleyMultpartRequestClass volleyMultpartRequestClass;
    private String requestTag = "add_new_post";

    public AddPostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rowView = inflater.inflate(R.layout.activity_add_post, container, false);

        ButterKnife.bind(this, rowView);

        uri = Uri.parse("");

        sharedPreferenceClass = SharedPreferenceClass.getInstance(getApplicationContext());

        chooseImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasGalleryPermissions()) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    getActivity().startActivityForResult(Intent.createChooser(intent, "Select Portfolio"), 1074);
                }
            }
        });

//        closeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                getActivity()finish();
//            }
//        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!uri.toString().isEmpty() && !(captionEt.getText().toString().isEmpty())) {
                    uploadFile(displayName, uri);
                } else {
                    Toast.makeText(getActivity(), "Please select image", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ((DashBoardActivity) getActivity()).setOnBundleSelected(new DashBoardActivity.SelectedBundle() {
            @Override
            public void onBundleSelect(Bundle bundle) {
                uri = Uri.parse(bundle.getString("uri"));
                displayName = bundle.getString("name");


                chooseImageBtn.setImageURI(uri);
            }
        });


        return rowView;
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

    public void uploadFile(String displayName, Uri file) {
        InputStream iStream = null;

        try {
            iStream = getActivity().getContentResolver().openInputStream(file);
            final byte[] inputData = UtilsClass.getBytes(iStream);

            //Provide multipart media
            HashMap<String, VolleyMultipartRequest.DataPart> multiPartParams = new HashMap<>();
            multiPartParams.put("blog_post", new VolleyMultipartRequest.DataPart(displayName, inputData));

            //Provide other parameters here
            HashMap<String, String> params = new HashMap<String, String>();

            JSONObject userObject = new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT));

            params.put("business_id", userObject.getString("id") + "");
            params.put("title", captionEt.getText().toString().trim() + "");
            params.put("content", postDescEt.getText().toString().trim() + "");
            params.put("description", postDescEt.getText().toString().trim() + "");

            volleyMultpartRequestClass = new VolleyMultpartRequestClass(getActivity(), AddPostFragment.this, params, multiPartParams, "Adding post", "Please wait " +
                    "while " +
                    "media is uploading", requestTag, Request.Method.POST);

            volleyMultpartRequestClass.volleyServiceCall(Constants.BASE_URL + Constants.API_BUSINESS_ADD_POST);
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
                    new MaterialAlertDialogBuilder(getActivity(),
                            R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                            .setTitle("Portfolio")
                            .setMessage(jsonObject.getString("msg"))
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    displayName = "";
                                    uri = null;
                                    chooseImageBtn.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.app_icon));
                                    captionEt.setText("");
                                    postDescEt.setText("");
                                }
                            })
                            .show();
                }
            } else {
                if (jsonObject.has("msg")) {
                    new MaterialAlertDialogBuilder(getActivity(),
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
}
