package com.software.acuity.splick.fragments;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.software.acuity.splick.R;
import com.software.acuity.splick.activities.SignUpActivity;
import com.software.acuity.splick.adapters.GridViewPortfolioCustomAdapter;
import com.software.acuity.splick.interfaces.IChangeViewPagerItem;
import com.software.acuity.splick.interfaces.IGetData;
import com.software.acuity.splick.interfaces.ISelectedMedia;
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
import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PortfolioFragment extends Fragment implements IVolleyMultipartResponse {

    IChangeViewPagerItem iChangeViewPagerItem;
    IGetData iGetData;

    @BindView(R.id.btnPortfolioUpload)
    Button btnPortfolioUpload;

//    @BindView(R.id.takePictureBtn)
//    MaterialButton takePictureBtn;

    SharedPreferenceClass sharedPreferenceClass;


    @BindView(R.id.chooseImageBtn)
    ImageView chooseImageBtn;

    public static final int MY_PERMISSIONS_REQUEST_READ_WRITE_EXTERNAL_STORAGE = 1000;
    public static final int GALLERY_INTENT = 1001;

//    @BindView(R.id.gridViewPortfolio)
//    GridView gridViewPortfolio;

    GridViewPortfolioCustomAdapter gridViewPortfolioCustomAdapter;
    private VolleyMultpartRequestClass volleyMultpartRequestClass;
    private String requestTag = "add_portfolio";
    private ArrayList<HashMap<String, String>> arraylist;
    private String url;
    private Uri uri = null;
    private String displayName = "";

    public PortfolioFragment(Context context, SignUpActivity activity) {
        // Required empty public constructor
        iChangeViewPagerItem = activity;
        iGetData = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_portfolio, container, false);

        ButterKnife.bind(this, fragmentView);

        btnPortfolioUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iChangeViewPagerItem.changeViewPagerItem("complete");
            }
        });

        sharedPreferenceClass = SharedPreferenceClass.getInstance(getActivity());
        setHasOptionsMenu(true);

        chooseImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasGalleryPermissions()) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    getActivity().startActivityForResult(Intent.createChooser(intent, "Select Portfolio"), Constants.GALLERY_INTENT_REQUEST_CODE);
                }
            }
        });

        btnPortfolioUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(displayName.trim().isEmpty() || uri.toString().trim().isEmpty())) {
                    uploadFile(displayName, uri);
                } else {
                    Toast.makeText(getActivity(), "Select Image for portfolio", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ((SignUpActivity) getActivity()).setSelectedMediaListener(new ISelectedMedia() {
            @Override
            public void selectedMedia(Intent intent) {
                Log.e("intent", "reached");

                uri = intent.getData();
                String uriString = uri.toString();
                File myFile = new File(uriString);
                String path = myFile.getAbsolutePath();
                displayName = null;

                if (uriString.startsWith("content://")) {
                    Cursor cursor = null;
                    try {
                        cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
                        if (cursor != null && cursor.moveToFirst()) {
                            displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                            Log.d("nameeeee>>>>  ", displayName);

                            chooseImageBtn.setImageURI(uri);
                        }
                    } finally {
                        cursor.close();
                    }
                } else if (uriString.startsWith("file://")) {
                    displayName = myFile.getName();
                    Log.d("nameeeee>>>>  ", displayName);
                }
            }
        });

//        gridViewPortfolioCustomAdapter = new GridViewPortfolioCustomAdapter(getActivity());
//        gridViewPortfolio.setAdapter(gridViewPortfolioCustomAdapter);

        return fragmentView;
    }

    public void uploadFile(String displayName, Uri file) {
        InputStream iStream = null;

        try {
            iStream = getActivity().getContentResolver().openInputStream(file);
            final byte[] inputData = UtilsClass.getBytes(iStream);

            //Provide multipart media
            HashMap<String, VolleyMultipartRequest.DataPart> multiPartParams = new HashMap<>();
            multiPartParams.put("media_item", new VolleyMultipartRequest.DataPart(displayName, inputData));

            //Provide other parameters here
            HashMap<String, String> params = new HashMap<String, String>();

            params.put("user_id", sharedPreferenceClass.getValues(Constants.USER_ID)+"");
            params.put("title", displayName + "");

            volleyMultpartRequestClass = new VolleyMultpartRequestClass(getActivity(), PortfolioFragment.this, params, multiPartParams, "Adding Portfolio", "Please wait while " +
                    "media is uploading", requestTag, Request.Method.POST);

            volleyMultpartRequestClass.volleyServiceCall(Constants.BASE_URL + Constants.API_ADD_PORTFOLIO);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItemSkipTagFragment:
                iGetData.getData("", "portfolio");
                break;

            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_tags_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private boolean hasCameraPermissions() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            return true;
        }

        return false;
    }

    private boolean hasGalleryPermissions() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_READ_WRITE_EXTERNAL_STORAGE);

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
                    Toast.makeText(getActivity(), "Permission Granted", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    getActivity().startActivityForResult(Intent.createChooser(intent, "Select Portfolio"), Constants.GALLERY_INTENT_REQUEST_CODE);
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

//    @Override
//    public void networkResponse(String response) {
//        try {
//
//            Log.e("net_res", response + "");
//            iGetData.getData("", "portfolio");
//
////
////            if (jsonObject.getBoolean("success")) {
////                if (jsonObject.has("msg")) {
////                    new MaterialAlertDialogBuilder(getActivity(),
////                            R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
////                            .setTitle("Portfolio")
////                            .setMessage(jsonObject.getString("msg"))
////                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
////                                @Override
////                                public void onClick(DialogInterface dialog, int which) {
////                                    iGetData.getData("", "portfolio");
////                                }
////                            })
////                            .show();
////                }
////            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void networkResponse(NetworkResponse response) {
        try {
            JSONObject jsonObject = new JSONObject(new String(response.data));

            jsonObject.toString().replace("\\\\", "");

            if (jsonObject.getBoolean("success")) {
                if (jsonObject.has("msg")) {
                    iGetData.getData("", "portfolio");
//                    new MaterialAlertDialogBuilder(getActivity(),
//                            R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
//                            .setTitle("Portfolio")
//                            .setMessage(jsonObject.getString("msg"))
//                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//
//                                }
//                            })
//                            .show();
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


/**
 * //    private void requestCameraPermission() {
 * //        Dexter.withActivity(getActivity())
 * //                .withPermission(Manifest.permission.CAMERA)
 * //                .withListener(new PermissionListener() {
 * //                    @Override
 * //                    public void onPermissionGranted(PermissionGrantedResponse response) {
 * //                        // permission is granted
 * //                        openCamera();
 * //                    }
 * //
 * //                    @Override
 * //                    public void onPermissionDenied(PermissionDeniedResponse response) {
 * //                        // check for permanent denial of permission
 * //                        if (response.isPermanentlyDenied()) {
 * ////                            showSettingsDialog();
 * //                        }
 * //                    }
 * //
 * //                    @Override
 * //                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
 * //                        token.continuePermissionRequest();
 * //                    }
 * //                }).check();
 * //    }
 * //
 * //    public void requestStoragePermissions() {
 * //        Dexter.withActivity(getActivity())
 * //                .withPermissions(
 * //                        Manifest.permission.READ_EXTERNAL_STORAGE,
 * //                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
 * //                        Manifest.permission.ACCESS_FINE_LOCATION)
 * //                .withListener(new MultiplePermissionsListener() {
 * //                    @Override
 * //                    public void onPermissionsChecked(MultiplePermissionsReport report) {
 * //                        // check if all permissions are granted
 * //                        if (report.areAllPermissionsGranted()) {
 * //                            Intent i = new Intent(
 * //                                    Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
 * //
 * //                            startActivityForResult(i, GALLERY_INTENT);
 * //                        }
 * //
 * //                        // check for permanent denial of any permission
 * //                        if (report.isAnyPermissionPermanentlyDenied()) {
 * //                            // show alert dialog navigating to Settings
 * ////                            showSettingsDialog();
 * //                        }
 * //                    }
 * //
 * //                    @Override
 * //                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
 * //                        token.continuePermissionRequest();
 * //                    }
 * //                }).
 * //                withErrorListener(new PermissionRequestErrorListener() {
 * //                    @Override
 * //                    public void onError(DexterError error) {
 * //                        Toast.makeText(getApplicationContext(), "Error occurred! ", Toast.LENGTH_SHORT).show();
 * //                    }
 * //                })
 * //                .onSameThread()
 * //                .check();
 * //    }
 * <p>
 * //    // navigating user to app settings
 * //    private void openSettings() {
 * //        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
 * //        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
 * //        intent.setData(uri);
 * //        startActivityForResult(intent, 101);
 * //    }
 * //
 * //    private void openCamera() {
 * //        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
 * //        startActivityForResult(intent, 100);
 * //    }
 * <p>
 * <p>
 * public void checkPermissions() {
 * else if (!hasReadExternalStoragePermission()) {
 * EasyPermissions.requestPermissions(PortfolioFragment.this,
 * "Permission",
 * 1001,
 * Manifest.permission.READ_EXTERNAL_STORAGE);
 * } else {
 * startDialog();
 * }
 * }
 * <p>
 * private void startDialog() {
 * AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(
 * getActivity());
 * myAlertDialog.setTitle("Upload Pictures Option");
 * myAlertDialog.setMessage("How do you want to set your picture?");
 * <p>
 * myAlertDialog.setPositiveButton("Gallery",
 * new DialogInterface.OnClickListener() {
 * public void onClick(DialogInterface arg0, int arg1) {
 * if (!hasWriteExternalStoragePermission()) {
 * EasyPermissions.requestPermissions(PortfolioFragment.this,
 * "Permission",
 * 1000,
 * Manifest.permission.WRITE_EXTERNAL_STORAGE);
 * }
 * Intent pictureActionIntent = null;
 * <p>
 * pictureActionIntent = new Intent(
 * Intent.ACTION_PICK,
 * android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
 * startActivityForResult(
 * pictureActionIntent,
 * 1001);
 * <p>
 * }
 * });
 * <p>
 * myAlertDialog.setNegativeButton("Camera",
 * new DialogInterface.OnClickListener() {
 * public void onClick(DialogInterface arg0, int arg1) {
 * <p>
 * Intent intent = new Intent(
 * MediaStore.ACTION_IMAGE_CAPTURE);
 * File f = new File(android.os.Environment
 * .getExternalStorageDirectory(), "temp.jpg");
 * intent.putExtra(MediaStore.EXTRA_OUTPUT,
 * Uri.fromFile(f));
 * <p>
 * startActivityForResult(intent,
 * 1000);
 * <p>
 * }
 * });
 * myAlertDialog.show();
 * }
 * <p>
 * public void cameraIntent() {
 * Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
 * startActivityForResult(cameraIntent, 1001);
 * }
 * <p>
 * public boolean hasReadExternalStoragePermission() {
 * return EasyPermissions.hasPermissions(getActivity(),
 * Manifest.permission.READ_EXTERNAL_STORAGE);
 * }
 * <p>
 * public boolean hasWriteExternalStoragePermission() {
 * return EasyPermissions.hasPermissions(getActivity(),
 * Manifest.permission.READ_EXTERNAL_STORAGE);
 * }
 *
 * @Override public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
 * checkPermissions();
 * }
 * @Override public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
 * if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
 * new AppSettingsDialog.Builder(this).build().show();
 * }
 * }
 * @Override public void onRationaleAccepted(int requestCode) {
 * <p>
 * }
 * @Override public void onRationaleDenied(int requestCode) {
 * <p>
 * }
 * @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
 * @NonNull int[] grantResults) {
 * super.onRequestPermissionsResult(requestCode, permissions, grantResults);
 * <p>
 * EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults,
 * PortfolioFragment.this);
 * }
 */