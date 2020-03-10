package com.software.acuity.splick.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.software.acuity.splick.R;
import com.software.acuity.splick.utils.AppController;
import com.software.acuity.splick.utils.Constants;
import com.software.acuity.splick.utils.SharedPreferenceClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignInActivity extends AppCompatActivity {

    @BindView(R.id.toolbarSignIn)
    Toolbar toolbar;

    SharedPreferenceClass sharedPreferenceClass;


    @BindView(R.id.etSignUpEmail)
    EditText etSignUpEmail;

    @BindView(R.id.etSignUpPassword)
    EditText etSignUpPassword;

    @BindView(R.id.btnSignInNext)
    Button signInNext;

    @BindView(R.id.forgotPasswordBtn)
    TextView forgotPasswordBtn;


    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mContext = SignInActivity.this;
        ButterKnife.bind(this);
        toolbarInit();

        sharedPreferenceClass = SharedPreferenceClass.getInstance(getApplicationContext());

        signInNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFunc()) {
                signIn();
                }
            }
        });
    }

    public void toolbarInit() {
        //setup toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    public void signIn() {
        ProgressDialog progressDialog = ProgressDialog.show(mContext, "Sign In", "Please wait! Authentication is in progress.");
        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,Constants.BASE_URL + Constants.API_USER_AUTHENTICATE + "",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getBoolean("success")) {
                                //Save data to shared preference
                                JSONObject dataObject = jsonObject.getJSONObject("data");

                                sharedPreferenceClass.setValues(Constants.USER_ID, dataObject.getString("id"));
                                sharedPreferenceClass.setValues(Constants.LOGIN_STATUS, "1");
                                sharedPreferenceClass.setValues(Constants.USER_JSON_OBJECT, dataObject.toString());
                                JSONObject userObject = new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT));

                                if (userObject.getString("user_role").trim().equalsIgnoreCase("business")) {
                                    sharedPreferenceClass.setValues(Constants.USER_ID, "3");
                                    sharedPreferenceClass.setValues(Constants.USER_JSON_OBJECT, userObject.toString());
                                } else {
                                    sharedPreferenceClass.setValues(Constants.USER_ID, "4");
                                    sharedPreferenceClass.setValues(Constants.USER_JSON_OBJECT, userObject.toString());
                                }

                                Intent intent = new Intent(SignInActivity.this, DashBoardActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                startActivity(intent);
                                finish();

                            } else {
                                new MaterialAlertDialogBuilder(SignInActivity.this,
                                        R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                                        .setTitle("Authentication")
                                        .setMessage("User Authentication failed. Contact your Service Provider.")
                                        .setPositiveButton("Ok", null)
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        progressDialog.dismiss();
                    }
                }, error -> {
                    Toast.makeText(mContext, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_email", etSignUpEmail.getText().toString().trim() + "");
                params.put("user_pass", etSignUpPassword.getText().toString().trim() + "");

                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, "sign_in");
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
        }

        return true;
    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}
