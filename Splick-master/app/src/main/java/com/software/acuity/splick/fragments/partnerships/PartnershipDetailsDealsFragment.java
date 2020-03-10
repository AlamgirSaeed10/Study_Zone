package com.software.acuity.splick.fragments.partnerships;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.software.acuity.splick.R;
import com.software.acuity.splick.activities.DashBoardActivity;
import com.software.acuity.splick.activities.partnerships.PartnershipDetailsActivity;
import com.software.acuity.splick.interfaces.IVolleyResponse;
import com.software.acuity.splick.models.partnerships.AffiliateDeals;
import com.software.acuity.splick.utils.Constants;
import com.software.acuity.splick.utils.VolleyRequestClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PartnershipDetailsDealsFragment extends Fragment implements IVolleyResponse {

    AffiliateDeals affiliateDeals;

    @BindView(R.id.commissionTv)
    TextView commissionTv;

    @BindView(R.id.dealPayment)
    TextView dealPayment;

    @BindView(R.id.dealInformation)
    TextView dealInformation;
    private VolleyRequestClass volleyRequestClass;
    private String requestTag = "deal_detail";

    public PartnershipDetailsDealsFragment() {
        // Required empty public constructor
        affiliateDeals = PartnershipDetailsActivity.affiliateDeals;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_deals_details, container, false);

        ButterKnife.bind(this, fragmentView);

        return fragmentView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            requestData();
        }
    }

    public void requestData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("deal_id", affiliateDeals.getDeal_id() + "");

        volleyRequestClass = new VolleyRequestClass(DashBoardActivity.dashboardActvityContext, PartnershipDetailsDealsFragment.this,
                params, "Deals Details", "Loading details!", requestTag, Request.Method.POST);
        volleyRequestClass.volleyServiceCall(Constants.BASE_URL + Constants.API_AFFILIATE_DEAL_DETAIL);
    }

    @Override
    public void networkResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.getBoolean("success")) {

                JSONObject jsonDataObject = jsonObject.getJSONObject("data");

                AffiliateDeals affiliateDeals = new AffiliateDeals();
                affiliateDeals.setDeal_id(jsonDataObject.getString("id"));
                affiliateDeals.setDeal_title(jsonDataObject.getString("deal_title"));
                affiliateDeals.setDeal_url(jsonDataObject.getString("deal_url"));
                affiliateDeals.setInformation(jsonDataObject.getString("deal_details"));
                affiliateDeals.setComm_amount(jsonDataObject.getString("comm_amount"));
                affiliateDeals.setComm_type(jsonDataObject.getString("comm_type"));

                commissionTv.setText(affiliateDeals.getComm_type().trim());
                dealPayment.setText(affiliateDeals.getComm_amount().trim() + "/sale");
                dealInformation.setText(affiliateDeals.getInformation().trim());
            } else {
                new MaterialAlertDialogBuilder(getActivity(),
                        R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                        .setTitle("Deal Details")
                        .setMessage(jsonObject.getString("msg"))
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
