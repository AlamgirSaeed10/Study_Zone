package com.software.acuity.splick.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.software.acuity.splick.R;
import com.software.acuity.splick.interfaces.IVolleyResponse;
import com.software.acuity.splick.interfaces.IVolleyResponseWithRequestId;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VolleyRequestClass {

    Context applicationContext;
    IVolleyResponse IVolleyResponse;
    Map<String, String> params = new HashMap<>();
    String progressTitle, progressMessage;
    SharedPreferenceClass sharedPreferenceClass;
    ProgressDialog progressDialog;
    IVolleyResponse iVolleyResponse;
    String requestTag;
    IVolleyResponseWithRequestId iVolleyResponseWithRequestId;
    int requestMethod, requestId;

    public VolleyRequestClass(Context applicationContext, IVolleyResponse iVolleyResponse,
                              HashMap<String
                                      , String> params, String progressTitle,
                              String progressMessage,
                              String requestTag,
                              int requestMethod) {

        this.applicationContext = applicationContext;
        this.iVolleyResponse = iVolleyResponse;
        this.params = params;
        this.progressTitle = progressTitle;
        this.progressMessage = progressMessage;
        this.requestTag = requestTag;
        this.requestMethod = requestMethod;
        this.sharedPreferenceClass = SharedPreferenceClass.getInstance(applicationContext);
    }

    public VolleyRequestClass(Context applicationContext, IVolleyResponse iVolleyResponse,
                              String progressTitle,
                              String progressMessage,
                              String requestTag,
                              int requestMethod) {

        this.applicationContext = applicationContext;
        this.iVolleyResponse = iVolleyResponse;
        this.progressTitle = progressTitle;
        this.progressMessage = progressMessage;
        this.requestTag = requestTag;
        this.requestMethod = requestMethod;
        this.sharedPreferenceClass = SharedPreferenceClass.getInstance(applicationContext);
    }


    public VolleyRequestClass(Context applicationContext, IVolleyResponseWithRequestId iVolleyResponseWithRequestId,
                              HashMap<String
                                      , String> params, String progressTitle,
                              String progressMessage,
                              String requestTag,
                              int requestMethod, int requestId) {

        this.applicationContext = applicationContext;
        this.iVolleyResponseWithRequestId = iVolleyResponseWithRequestId;
        this.progressTitle = progressTitle;
        this.params = params;
        this.progressMessage = progressMessage;
        this.requestTag = requestTag;
        this.requestMethod = requestMethod;
        this.sharedPreferenceClass = SharedPreferenceClass.getInstance(applicationContext);
        this.requestId = requestId;
    }

    public VolleyRequestClass(Context applicationContext, IVolleyResponseWithRequestId iVolleyResponseWithRequestId,
                              String progressTitle,
                              String progressMessage,
                              String requestTag,
                              int requestMethod, int requestId) {

        this.applicationContext = applicationContext;
        this.iVolleyResponseWithRequestId = iVolleyResponseWithRequestId;
        this.progressTitle = progressTitle;
        this.progressMessage = progressMessage;
        this.requestTag = requestTag;
        this.requestMethod = requestMethod;
        this.sharedPreferenceClass = SharedPreferenceClass.getInstance(applicationContext);
        this.requestId = requestId;
    }

    public void volleyServiceCall(String endPoint) {
        try {

            this.progressDialog = ProgressDialog.show(this.applicationContext, this.progressTitle,
                    this.progressMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringRequest jsonObjReq = new StringRequest(requestMethod,
                endPoint,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
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
                            .setMessage("Error in network Connection.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(applicationContext, "Something went wrong!",
                            Toast.LENGTH_LONG).show();
                } finally {
                    progressDialog.dismiss();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, requestTag + "");
    }

    public void volleyServiceCallWithoutLoading(String endPoint) {
        try {

//            this.progressDialog = ProgressDialog.show(this.applicationContext, this.progressTitle,
//                    this.progressMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringRequest jsonObjReq = new StringRequest(requestMethod,
                endPoint,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        iVolleyResponse.networkResponse(response);
//                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                try {
                    new MaterialAlertDialogBuilder(applicationContext,
                            R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                            .setTitle("Volley")
                            .setMessage("Unknown error occurred.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                } catch (Exception e) {
                    Toast.makeText(applicationContext, "Something went wrong!",
                            Toast.LENGTH_LONG).show();
                } finally {
//                    progressDialog.dismiss();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, requestTag + "");
    }


    public void volleyServiceCallWithRequestId(String endPoint) {
        try {

            this.progressDialog = ProgressDialog.show(this.applicationContext, this.progressTitle,
                    this.progressMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringRequest jsonObjReq = new StringRequest(requestMethod,
                endPoint,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        iVolleyResponseWithRequestId.networkResponse(response, requestId);
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                try {
                    new MaterialAlertDialogBuilder(applicationContext,
                            R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                            .setTitle("Volley")
                            .setMessage("An Error has occurred.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                } catch (Exception e) {
                    Toast.makeText(applicationContext, "Something went wrong!",
                            Toast.LENGTH_LONG).show();
                } finally {
                    progressDialog.dismiss();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, requestTag + "");
    }

    public void volleyServiceCallWithRawParams(String endPoint, JSONObject jsonObject) {
        try {

            this.progressDialog = ProgressDialog.show(this.applicationContext, this.progressTitle,
                    this.progressMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, endPoint, jsonObject,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        iVolleyResponse.networkResponse(response.toString());
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    new MaterialAlertDialogBuilder(applicationContext,
                            R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                            .setTitle("Volley")
                            .setMessage("Error has occurred.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                } catch (Exception e) {
                    Toast.makeText(applicationContext, "Something went wrong!",
                            Toast.LENGTH_LONG).show();
                } finally {
                    progressDialog.dismiss();
                }
            }
        }) {
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonRequest, requestTag + "");
    }

    public void volleyServiceCallWithRawParamsWithoutProgress(String endPoint, JSONObject jsonObject) {
        try {

//            this.progressDialog = ProgressDialog.show(this.applicationContext, this.progressTitle,
//                    this.progressMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, endPoint, jsonObject,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        iVolleyResponse.networkResponse(response.toString());
//                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    new MaterialAlertDialogBuilder(applicationContext,
                            R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                            .setTitle("Volley")
                            .setMessage("Error has Occurred.")
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
//                    progressDialog.dismiss();
                }
            }
        }) {
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonRequest, requestTag + "");
    }

    public void volleyServiceCallWithRawParamsWithoutProgressWithRequestId(String endPoint, JSONObject jsonObject) {
        try {

//            this.progressDialog = ProgressDialog.show(this.applicationContext, this.progressTitle,
//                    this.progressMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, endPoint, jsonObject,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        iVolleyResponseWithRequestId.networkResponse(response.toString(), requestId);
//                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    new MaterialAlertDialogBuilder(applicationContext,
                            R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                            .setTitle("Volley")
                            .setMessage("Error has Occurred please contact your service provider.")
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
//                    progressDialog.dismiss();
                }
            }
        }) {
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonRequest, requestTag + "");
    }

}
