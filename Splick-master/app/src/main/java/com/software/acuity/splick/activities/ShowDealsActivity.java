package com.software.acuity.splick.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.software.acuity.splick.R;
import com.software.acuity.splick.adapters.RecyclerViewOfferDealsCustomAdapter;
import com.software.acuity.splick.behaviours.RecyclerTouchListener;
import com.software.acuity.splick.interfaces.IRecyclerClickListener;
import com.software.acuity.splick.interfaces.IVolleyResponse;
import com.software.acuity.splick.models.partnerships.AffiliateDeals;
import com.software.acuity.splick.utils.Constants;
import com.software.acuity.splick.utils.SharedPreferenceClass;
import com.software.acuity.splick.utils.VolleyRequestClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowDealsActivity extends AppCompatActivity implements IVolleyResponse {

    @BindView(R.id.recyclerViewDealsFragment)
    RecyclerView recyclerView;

    @BindView(R.id.addDeal)
    ImageButton addDealBtn;

    @BindView(R.id.close_btn)
    ImageButton closeBtn;

    RecyclerViewOfferDealsCustomAdapter recyclerViewOfferDealsCustomAdapter;

    private RecyclerView.LayoutManager layoutManager;
    private VolleyRequestClass volleyRequestClass;
    private String requestTag = "deals_fragment";

    List<AffiliateDeals> mDataList = new ArrayList<>();

    JSONObject userObject =
            null;
    private SharedPreferenceClass sharedPreferenceClass;

    String commissionType = "fixed";

    //Dialog view
    EditText etDealTitle = null;
    EditText etCommissionAmount = null;
    EditText etDealUrl = null;
    EditText etDealInfo = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_deals);

        sharedPreferenceClass = SharedPreferenceClass.getInstance(getApplicationContext());
        try {
            userObject = new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT));
        } catch (JSONException e) {

        }
        ButterKnife.bind(this);


        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        addDealBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDealDialog();
            }
        });

        requestData();
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.fixedComm:
                if (checked) {
                    commissionType = "fixed";
                    if (!etCommissionAmount.getText().toString().trim().isEmpty()) {

                        etCommissionAmount.setText("$" + etCommissionAmount.getText().toString().trim().replace("%", "")  + "");
                    }
                }
                break;
            case R.id.perComm:
                if (checked) {
                    commissionType = "percentage";
                    if (!etCommissionAmount.getText().toString().trim().isEmpty()) {

                        etCommissionAmount.setText(etCommissionAmount.getText().toString().trim().replace("$", "") + "%");
                    }
                }
                break;
        }
    }

    public void showAddDealDialog() {
        Dialog dialog = new Dialog(ShowDealsActivity.this,
                android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.add_deal_dialog);

        etDealTitle = dialog.findViewById(R.id.etDealTitle);
        etCommissionAmount = dialog.findViewById(R.id.etDealCommission);
        etDealUrl = dialog.findViewById(R.id.etDealUrl);
        etDealInfo = dialog.findViewById(R.id.etDescription);

        dialog.findViewById(R.id.cancelbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.addBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etDealTitle.getText().toString().trim().isEmpty()) {
                    etDealTitle.setError("Please enter deal title");
                } else if (etCommissionAmount.getText().toString().trim().isEmpty()) {
                    etCommissionAmount.setError("Please enter deal commission amount");
                } else if (commissionType.isEmpty()) {
                    Toast.makeText(ShowDealsActivity.this, "Please select commission type", Toast.LENGTH_SHORT).show();
                } else {
                    HashMap<String, String> params = new HashMap<>();
                    String userId = null;
                    try {
                        userId = new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT)).getString("id");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {
                        userObject = new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT));

                        params.put("deal_title", etDealTitle.getText().toString().trim());
                        params.put("comm_amount", etCommissionAmount.getText().toString().trim());
                        params.put("user_id", userId + "");
                        params.put("comm_type", commissionType);
                        params.put("deal_url", etDealUrl.getText().toString().trim());
                        params.put("deal_details", etDealInfo.getText().toString().trim() + "");

                        volleyRequestClass = new VolleyRequestClass(ShowDealsActivity.this, ShowDealsActivity.this,
                                params, "Create Deal", "Creating a new deal", requestTag, Request.Method.POST);
                        volleyRequestClass.volleyServiceCall(Constants.BASE_URL + Constants.API_BUSINESS_ADD_DEAL);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        dialog.show();
    }


    public void initRecyclerView() {
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerViewOfferDealsCustomAdapter = new RecyclerViewOfferDealsCustomAdapter(ShowDealsActivity.this,
                mDataList);
        recyclerView.setAdapter(recyclerViewOfferDealsCustomAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),
                recyclerView, new IRecyclerClickListener() {
            @Override
            public void onClick(View view, int position) {
                offerDealToAffiliate(position);
             }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    public void offerDealToAffiliate(int position) {
        HashMap<String, String> params = new HashMap<>();
        String userId = null;
        try {
            userId = new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT)).getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            userObject = new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT));

            params.put("affiliate_id", sharedPreferenceClass.getValues(Constants.AFFILIATE_ID) + "");
            params.put("deal_id", mDataList.get(position).getId() + "");
            params.put("business_id", userId + "");

            volleyRequestClass = new VolleyRequestClass(ShowDealsActivity.this, ShowDealsActivity.this,
                    params, "Offer Deal", "Offering Deal to affiliate", requestTag, Request.Method.POST);
            volleyRequestClass.volleyServiceCall(Constants.BASE_URL + Constants.API_BUSINESS_OFFER_DEAL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void requestData() {
        HashMap<String, String> params = new HashMap<>();
        String userId = sharedPreferenceClass.getValues(Constants.USER_ID);

        try {
            userObject = new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT));

            params.put("user_id", new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT)).getString("id") + "");

            volleyRequestClass = new VolleyRequestClass(ShowDealsActivity.this, ShowDealsActivity.this,
                    params, "Deals", "Please Wait Loading deals!", requestTag, Request.Method.POST);
            volleyRequestClass.volleyServiceCall(Constants.BASE_URL + Constants.API_BUSINESS_MY_DEALS);
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        volleyRequestClass.volleyServiceCall(Constants.BASE_URL + Constants.API_AFFILIATE_STAT_AFFILIATIONS_AFFILIATE);
    }


    @Override
    public void networkResponse(String response) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);

            if (jsonObject.getBoolean("success")) {

                JSONArray jsonArray = jsonObject.getJSONArray("data");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject object = jsonArray.getJSONObject(i);

                    AffiliateDeals affiliateDeals = new AffiliateDeals();
                    affiliateDeals.setId(object.getString(
                            "id"));
                    affiliateDeals.setDeal_title(object.getString(
                            "deal_title"));
                    affiliateDeals.setComm_amount(object.getString(
                            "comm_amount"));
                    affiliateDeals.setComm_type(object.getString(
                            "comm_type"));
                    affiliateDeals.setDeal_url(object.getString(
                            "deal_url"));
                    affiliateDeals.setStatus(object.getString(
                            "deal_status"));
                    affiliateDeals.setBusiness_id(object.getString(
                            "user_id"));

                    mDataList.add(affiliateDeals);
                }

                //Setup Recycler View
                initRecyclerView();

            } else {
                new MaterialAlertDialogBuilder(ShowDealsActivity.this,
                        R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                        .setTitle("Deal")
                        .setMessage(jsonObject.getString("msg"))
                        .setPositiveButton("Ok", (dialog, which) -> {

                        })
                        .show();
            }
        } catch (JSONException e) {
            e.printStackTrace();

            if (jsonObject != null) {
                try {
                    if (jsonObject.getBoolean("success")) {
                        new MaterialAlertDialogBuilder(ShowDealsActivity.this,
                                R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                                .setTitle("Deal")
                                .setMessage("Deal has been created")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                })
                                .show();
                    }
                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

}
