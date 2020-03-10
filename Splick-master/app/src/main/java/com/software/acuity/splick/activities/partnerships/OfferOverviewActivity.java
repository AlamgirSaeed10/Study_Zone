package com.software.acuity.splick.activities.partnerships;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.software.acuity.splick.R;
import com.software.acuity.splick.activities.DashBoardActivity;
import com.software.acuity.splick.interfaces.IVolleyResponse;
import com.software.acuity.splick.models.offers.AffiliateOffer;
import com.software.acuity.splick.models.partnerships.AffiliateDeals;
import com.software.acuity.splick.utils.Constants;
import com.software.acuity.splick.utils.SharedPreferenceClass;
import com.software.acuity.splick.utils.VolleyRequestClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OfferOverviewActivity extends AppCompatActivity implements IVolleyResponse {

    @BindView(R.id.btnOfferBackBtn)
    ImageButton backBtnOfferOverview;

    @BindView(R.id.acceptbtn)
    Button acceptOffer;

    @BindView(R.id.declineBtn)
    Button rejectOffer;

    @BindView(R.id.dealPayment)
    TextView commisionPayment;

    @BindView(R.id.commissionTv)
    TextView commissionType;

    @BindView(R.id.dealInformation)
    TextView dealInformation;

    @BindView(R.id.dealStatus)
    TextView dealStatus;

    AffiliateOffer affiliateOffer = new AffiliateOffer();
    private VolleyRequestClass volleyRequestClass;
    private String requestTag = "offer_activity";

    SharedPreferenceClass sharedPreferenceClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_overview);

        ButterKnife.bind(this);

        affiliateOffer = (AffiliateOffer) getIntent().getSerializableExtra("offer_object");

        if (!(affiliateOffer.getStatus().equalsIgnoreCase("pending"))) {
            acceptOffer.setVisibility(View.INVISIBLE);
            rejectOffer.setVisibility(View.INVISIBLE);
            dealStatus.setText(affiliateOffer.getStatus() + "");
        } else {
            acceptOffer.setVisibility(View.VISIBLE);
            rejectOffer.setVisibility(View.VISIBLE);
            dealStatus.setText(affiliateOffer.getStatus() + "");

        }

        sharedPreferenceClass = SharedPreferenceClass.getInstance(getApplicationContext());

        getOfferDetails();

        try {
            if (new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT)).getString("user_role").equalsIgnoreCase("business")) {
                acceptOffer.setVisibility(View.INVISIBLE);
                rejectOffer.setVisibility(View.INVISIBLE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        backBtnOfferOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        acceptOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptOffer();
            }
        });

        rejectOffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rejectOffer();
            }
        });

    }

    public void rejectOffer() {

        HashMap<String, String> params = new HashMap<>();
        params.put("affiliate_id", affiliateOffer.getAffiliate_id() + "");
        params.put("business_id", affiliateOffer.getBusiness_id() + "");
        params.put("deal_id", affiliateOffer.getDeal_id() + "");

        volleyRequestClass = new VolleyRequestClass(OfferOverviewActivity.this,
                OfferOverviewActivity.this,
                params, "Reject offer", "Rejecting offer!", requestTag, Request.Method.POST);

        volleyRequestClass.volleyServiceCall(Constants.BASE_URL + Constants.API_AFFILIATE_APP_OFFER_REJECT);
    }

    public void acceptOffer() {

        HashMap<String, String> params = new HashMap<>();
        params.put("affiliate_id", affiliateOffer.getAffiliate_id() + "");
        params.put("business_id", affiliateOffer.getBusiness_id() + "");
        params.put("deal_id", affiliateOffer.getDeal_id() + "");

        volleyRequestClass = new VolleyRequestClass(OfferOverviewActivity.this,
                OfferOverviewActivity.this,
                params, "Accept offer", "Accepting offer!", requestTag, Request.Method.POST);

        volleyRequestClass.volleyServiceCall(Constants.BASE_URL + Constants.API_AFFILIATE_AFF_OFFER_ACCEPT);
    }

    @Override
    public void networkResponse(String response) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);

            if (jsonObject.has("msg")) {

                JSONObject msgObject = jsonObject.getJSONObject("msg");

                if (msgObject.has("deal")) {
                    JSONObject offerObject = msgObject.getJSONObject("deal");

                    AffiliateDeals affiliateDeals = new AffiliateDeals();
                    affiliateDeals.setDeal_id(offerObject.getString("id"));
//                    affiliateDeals.setStatus(offerObject.getString("status"));
                    affiliateDeals.setDeal_title(offerObject.getString("deal_title"));
                    affiliateDeals.setDeal_url(offerObject.getString("deal_url"));
                    affiliateDeals.setInformation(offerObject.getString("deal_details"));
                    affiliateDeals.setComm_amount(offerObject.getString("comm_amount"));
                    affiliateDeals.setComm_type(offerObject.getString("comm_type"));

                    commissionType.setText(affiliateDeals.getComm_type().trim());
                    commisionPayment.setText(affiliateDeals.getComm_amount().trim() + "/sale");
                    dealInformation.setText(affiliateDeals.getInformation().trim());
//                    dealStatus.setText(affiliateDeals.getStatus() + "");
                } else {
                    new MaterialAlertDialogBuilder(OfferOverviewActivity.this,
                            R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                            .setTitle("Offers")
                            .setMessage(jsonObject.getString("msg"))
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
            } else if (jsonObject.getBoolean("success")) {
                JSONObject jsonDealsObject = jsonObject.getJSONObject("data").getJSONObject(
                        "deals");

            } else {
                new MaterialAlertDialogBuilder(OfferOverviewActivity.this,
                        R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                        .setTitle("Offers")
                        .setMessage(jsonObject.getString("msg"))
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }

        } catch (JSONException e) {
            try {
                new MaterialAlertDialogBuilder(OfferOverviewActivity.this,
                        R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                        .setTitle("Offers")
                        .setMessage(jsonObject.getString("msg"))
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    public void getOfferDetails() {
        HashMap<String, String> params = new HashMap<>();
        params.put("id", affiliateOffer.getId() + "");

        volleyRequestClass = new VolleyRequestClass(DashBoardActivity.dashboardActvityContext, OfferOverviewActivity.this,
                params, "Offer Details", "Loading details!", requestTag, Request.Method.POST);
        volleyRequestClass.volleyServiceCall(Constants.BASE_URL + Constants.API_AFFILIATE_OFFER_SINGLE);
    }
}
