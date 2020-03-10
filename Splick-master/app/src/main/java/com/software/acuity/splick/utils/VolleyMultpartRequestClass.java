package com.software.acuity.splick.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.software.acuity.splick.R;
import com.software.acuity.splick.interfaces.IVolleyMultipartResponse;
import com.software.acuity.splick.interfaces.IVolleyResponse;

import java.util.HashMap;
import java.util.Map;

public class VolleyMultpartRequestClass {

    Context applicationContext;
    com.software.acuity.splick.interfaces.IVolleyResponse IVolleyResponse;
    Map<String, String> params = new HashMap<>();
    Map<String, VolleyMultipartRequest.DataPart> multiPartParams = new HashMap<>();
    String progressTitle, progressMessage;
    SharedPreferenceClass sharedPreferenceClass;
    ProgressDialog progressDialog;
    IVolleyMultipartResponse iVolleyResponse;
    String requestTag;
    int requestMethod;
    private RequestQueue rQueue;


    public VolleyMultpartRequestClass(Context applicationContext, IVolleyMultipartResponse iVolleyResponse,
                                      HashMap<String
                                              , String> params,
                                      Map<String, VolleyMultipartRequest.DataPart> multiPartParams,
                                      String progressTitle,
                                      String progressMessage,
                                      String requestTag,
                                      int requestMethod) {

        this.applicationContext = applicationContext;
        this.iVolleyResponse = iVolleyResponse;
        this.params = params;
        this.multiPartParams = multiPartParams;
        this.progressTitle = progressTitle;
        this.progressMessage = progressMessage;
        this.requestTag = requestTag;
        this.requestMethod = requestMethod;
        this.sharedPreferenceClass = SharedPreferenceClass.getInstance(applicationContext);
    }

//    public VolleyMultpartRequestClass(Context applicationContext, IVolleyResponse iVolleyResponse,
//                                      String progressTitle,
//                                      String progressMessage,
//                                      String requestTag,
//                                      int requestMethod) {
//
//        this.applicationContext = applicationContext;
//        this.iVolleyResponse = iVolleyResponse;
//        this.progressTitle = progressTitle;
//        this.progressMessage = progressMessage;
//        this.requestTag = requestTag;
//        this.requestMethod = requestMethod;
//        this.sharedPreferenceClass = SharedPreferenceClass.getInstance(applicationContext);
//    }

    public void volleyServiceCall(String endPoint) {
        this.progressDialog = ProgressDialog.show(this.applicationContext, this.progressTitle,
                this.progressMessage);
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(requestMethod,
                endPoint,
                new Response.Listener<NetworkResponse>() {

                    @Override
                    public void onResponse(NetworkResponse response) {
                        Log.e("response", response + "");
                        iVolleyResponse.networkResponse(response);
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                try {
                    new MaterialAlertDialogBuilder(applicationContext,
                            R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                            .setTitle("Volley")
                            .setMessage(error.getMessage().toString())
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                } catch (Exception e) {
                    Toast.makeText(applicationContext, "Something went wrong!",
                            Toast.LENGTH_SHORT).show();
                } finally {
                    progressDialog.dismiss();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                return multiPartParams;
            }
        };

        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rQueue = Volley.newRequestQueue(this.applicationContext);
        rQueue.add(volleyMultipartRequest);
    }
}
