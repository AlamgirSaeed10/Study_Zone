package com.software.acuity.splick.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.software.acuity.splick.R;
import com.software.acuity.splick.activities.SignUpActivity;
import com.software.acuity.splick.interfaces.IChangeViewPagerItem;
import com.software.acuity.splick.utils.AppController;
import com.software.acuity.splick.utils.Constants;
import com.software.acuity.splick.utils.SharedPreferenceClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpFragment extends Fragment {

    public static final String TAG = "SIGNUP";

    IChangeViewPagerItem iChangeViewPagerItem;

    @BindView(R.id.btnSignUpNext)
    Button btnSignUpNext;

    @BindView(R.id.etSignUpEmail)
    EditText etSignUpEmail;

    @BindView(R.id.etSignUpPassword)
    EditText etSignUpPassword;

    @BindView(R.id.etSignUpConfirmPassword)
    EditText etSignUpConfirmPassword;

    SharedPreferenceClass sharedPreferenceClass;

    public SignUpFragment(Context context, SignUpActivity activity) {
        // Required empty public constructor
        iChangeViewPagerItem = activity;

        sharedPreferenceClass = SharedPreferenceClass.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View fragmentView = inflater.inflate(R.layout.fragment_sign_up, container, false);

        ButterKnife.bind(this, fragmentView);

        btnSignUpNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFunc()) {
//                    iChangeViewPagerItem.changeViewPagerItem("about");
                    signUpRequest();
                }
            }
        });

        return fragmentView;
    }

    public void signUpRequest() {
        ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "Sign Up", "Please wait! Sign Up process is in progress.");

        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                Constants.BASE_URL + Constants.API_USER_REGISTER + "",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("success")) {
                                //Save data to shared preference

                                sharedPreferenceClass.setValues(Constants.USER_ID, jsonObject.getString("data"));
//                                sharedPreferenceClass.setValues(Constants.USER_ID,
//                                        "4");
//                                sharedPreferenceClass.setValues(Constants.USER_ID, "4");
                                new MaterialAlertDialogBuilder(getActivity(), R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                                        .setTitle("SignUp")
                                        .setMessage("User has been added successfully!")
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                iChangeViewPagerItem.changeViewPagerItem("about");
                                            }
                                        })
                                        .show();
                            } else {
//                                Toast.makeText(getActivity(), jsonObject.getString("msg") + "", Toast.LENGTH_SHORT).show();
                                new MaterialAlertDialogBuilder(getActivity(), R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                                        .setTitle("SignUp")
                                        .setMessage(jsonObject.getString("msg") + "")
                                        .setPositiveButton("Ok", null)
                                        .show();
                            }
                            Log.d(TAG, response.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                new MaterialAlertDialogBuilder(getActivity(), R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                        .setTitle("SignUp")
                        .setMessage(error.getMessage() + "")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();

                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("full_name", etSignUpConfirmPassword.getText().toString() + "");
                params.put("user_email", etSignUpEmail.getText().toString() + "");
                params.put("user_pass", etSignUpPassword.getText().toString() + "");
                params.put("user_role", sharedPreferenceClass.getValues(Constants.USER_TYPE) + "");

                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, TAG);
    }

    public boolean validateFunc() {

        if (etSignUpEmail.getText().toString().trim().isEmpty()) {
            etSignUpEmail.setError("Email is required");
            return false;
        } else if (!isValidEmail(etSignUpEmail.getText().toString())) {
            etSignUpEmail.setError("Invalid Email");
            return false;
        } else if (etSignUpPassword.getText().toString().trim().isEmpty()) {
            etSignUpPassword.setError("Password is required");
            return false;
        } else if (etSignUpConfirmPassword.getText().toString().trim().isEmpty()) {
            etSignUpConfirmPassword.setError("Confirm Password is required");
            return false;
        } else if (!etSignUpPassword.getText().toString().equals(etSignUpConfirmPassword.getText().toString())) {
            etSignUpPassword.setError("Password and Confirm password does not match");
            return false;
        }

        return true;
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}