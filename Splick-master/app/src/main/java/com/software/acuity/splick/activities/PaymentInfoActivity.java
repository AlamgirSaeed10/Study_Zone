package com.software.acuity.splick.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.software.acuity.splick.R;
import com.software.acuity.splick.interfaces.IVolleyResponseWithRequestId;
import com.software.acuity.splick.utils.Constants;
import com.software.acuity.splick.utils.SharedPreferenceClass;
import com.software.acuity.splick.utils.VolleyRequestClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentInfoActivity extends AppCompatActivity implements IVolleyResponseWithRequestId {

    @BindView(R.id.bankName)
    EditText bankName;

    @BindView(R.id.bankAccount)
    EditText bankAccount;

    @BindView(R.id.bankAccountName)
    EditText bankAccountName;

    @BindView(R.id.bankCountry)
    EditText bankCountry;

    @BindView(R.id.bankMisc)
    EditText bankMisc;

    @BindView(R.id.saveBtnFromPaymentInfo)
    MaterialButton saveBtn;

    @BindView(R.id.backBtnFromPaymentInfo)
    MaterialButton backBtn;
    private SharedPreferenceClass sharedPreferenceClass;
    private VolleyRequestClass volleyRequestClass;
    private String requestTag = "payment_info";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_info);

        ButterKnife.bind(this);

        sharedPreferenceClass = SharedPreferenceClass.getInstance(getApplicationContext());

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (bankCountry.getText().toString().trim().isEmpty()) {
                    bankCountry.setError("PLease enter country name");
                } else if (bankName.getText().toString().trim().isEmpty()) {
                    bankName.setError("PLease enter bank name");
                } else if (bankAccount.getText().toString().trim().isEmpty()) {
                    bankAccount.setError("PLease enter bank account IBAN");
                } else if (bankAccountName.getText().toString().trim().isEmpty()) {
                    bankAccountName.setError("PLease enter account title");
                } else if (bankMisc.getText().toString().trim().isEmpty()) {
                    bankMisc.setError("PLease enter misc.");
                } else
                    saveBankInfo();
            }
        });
    }

    public void saveBankInfo() {
        HashMap<String, String> params = new HashMap<>();
        try {
            JSONObject userObject = new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT));

            params.put("user_id", userObject.getString("id"));
            params.put("bank_country", bankCountry.getText().toString().trim());
            params.put("bank_name", bankName.getText().toString().trim());
            params.put("bank_account", bankAccount.getText().toString().trim());
            params.put("bank_acc_name", bankAccountName.getText().toString().trim());
            params.put("bank_misc", bankMisc.getText().toString().trim());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        volleyRequestClass = new VolleyRequestClass(PaymentInfoActivity.this, PaymentInfoActivity.this,
                params, "Add Bank", "Please wait while bank is being added!",
                requestTag,
                Request.Method.POST, 1000);

        volleyRequestClass.volleyServiceCallWithRequestId(Constants.BASE_URL + Constants.API_BUSINESS_BANK_ADD);
    }

    @Override
    public void networkResponse(String response, int requestId) {
        try {
            JSONObject jsonResponse = new JSONObject(response);

            switch (requestId) {
                case 1000:
                    if (jsonResponse.getBoolean("success")) {
                        new MaterialAlertDialogBuilder(PaymentInfoActivity.this,
                                R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                                .setTitle("Bank")
                                .setMessage(jsonResponse.getString("msg"))
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                })
                                .show();
                    } else {
                        new MaterialAlertDialogBuilder(PaymentInfoActivity.this,
                                R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                                .setTitle("Bank")
                                .setMessage(jsonResponse.getString("msg"))
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                })
                                .show();
                    }
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
