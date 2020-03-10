package com.software.acuity.splick.fragments.partnerships;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.software.acuity.splick.R;
import com.software.acuity.splick.activities.partnerships.OfferOverviewActivity;
import com.software.acuity.splick.adapters.OffersCustomAdapter;
import com.software.acuity.splick.behaviours.RecyclerTouchListener;
import com.software.acuity.splick.interfaces.IRecyclerClickListener;
import com.software.acuity.splick.interfaces.IVolleyResponse;
import com.software.acuity.splick.models.offers.AffiliateOffer;
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

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class OffersFragment extends Fragment implements IVolleyResponse {

    @BindView(R.id.recyclerViewOfferFragment)
    RecyclerView recyclerView;

    @BindView(R.id.nodataFoundMessage)
    LinearLayout noDataFoundLayout;

    private RecyclerView.LayoutManager layoutManager;
    private OffersCustomAdapter offersCustomAdapter;
    private VolleyRequestClass volleyRequestClass;
    private String requestTag = "partnerships_offers";
    private List<AffiliateOffer> mDataList = new ArrayList<>();
    private SharedPreferenceClass sharedPreferenceClass;

    JSONObject userObject =
            null;

    boolean isVisible = false;


    public OffersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_offers, container, false);

        sharedPreferenceClass = SharedPreferenceClass.getInstance(getApplicationContext());
        try {
            userObject = new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ButterKnife.bind(this, fragmentView);

//        requestData();

        return fragmentView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            requestData();
        }
    }

    public void initRecyclerView() {
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        offersCustomAdapter = new OffersCustomAdapter(getActivity(), mDataList);
        recyclerView.setAdapter(offersCustomAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),
                recyclerView, new IRecyclerClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getActivity(), OfferOverviewActivity.class);
                intent.putExtra("offer_object", mDataList.get(position));
                getFragmentManager().beginTransaction().addToBackStack("fragment");
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }

    public void requestData() {

        HashMap<String, String> params = new HashMap<>();

        sharedPreferenceClass = SharedPreferenceClass.getInstance(getApplicationContext());
        try {
            userObject = new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT));
        } catch (JSONException e) {

        }
        String userId = "";

        try {
            JSONObject userObject = new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT));

            userId = userObject.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if (userObject.getString("user_role").trim().equalsIgnoreCase("business")) {
                params.put(" business_id", userId + "");
                volleyRequestClass = new VolleyRequestClass(getActivity(), OffersFragment.this,
                        params, "Offers", "Loading Offers!", requestTag, Request.Method.POST);

                volleyRequestClass.volleyServiceCall(Constants.BASE_URL + Constants.API_BUSINESS_OFFER_VIEW);

            } else if (userObject.getString("user_role").trim().equalsIgnoreCase("affiliate")) {
                params.put("affiliate_id", userId + "");
                volleyRequestClass = new VolleyRequestClass(getActivity(), OffersFragment.this,
                        params, "Offers", "Loading Offers!", requestTag, Request.Method.POST);


                volleyRequestClass.volleyServiceCall(Constants.BASE_URL + Constants.API_AFFILIATE_AFF_OFFER_VIEW);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        params.put("affiliate_id", "12");


//        volleyRequestClass.volleyServiceCall(Constants.BASE_URL + Constants.API_AFFILIATE_AFF_OFFER_VIEW);
    }

    @Override
    public void networkResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            mDataList.clear();
            if (jsonObject.getBoolean("success")) {
                JSONArray jsonDealsArray = jsonObject.getJSONArray("data");

                if (this.userObject.getString("user_role").trim().equalsIgnoreCase("business")) {
                    for (int i = 0; i < jsonDealsArray.length(); i++) {

                        AffiliateOffer affiliateOffer = new AffiliateOffer();
                        affiliateOffer.setId(jsonDealsArray.getJSONObject(i).getString("id"));
                        affiliateOffer.setBusiness_id(jsonDealsArray.getJSONObject(i).getString(
                                "business_id"));
                        affiliateOffer.setAffiliate_id(jsonDealsArray.getJSONObject(i).getString(
                                "affiliate_id"));
                        affiliateOffer.setStatus(jsonDealsArray.getJSONObject(i).getString(
                                "status"));
                        affiliateOffer.setDeal_id(jsonDealsArray.getJSONObject(i).getString(
                                "deal_id"));
                        affiliateOffer.setAdd_time(jsonDealsArray.getJSONObject(i).getString(
                                "add_time"));
                        affiliateOffer.setBusiness_name(jsonDealsArray.getJSONObject(i).getString(
                                "affiliate_name"));
                        affiliateOffer.setDeal_title(jsonDealsArray.getJSONObject(i).getString(
                                "deal_title"));
                        affiliateOffer.setInfo(jsonDealsArray.getJSONObject(i).getString(
                                "info"));

                        mDataList.add(affiliateOffer);
                    }

//                    generateListAsPerAffiliate();
                } else {
                    for (int i = 0; i < jsonDealsArray.length(); i++) {

                        AffiliateOffer affiliateOffer = new AffiliateOffer();
                        affiliateOffer.setId(jsonDealsArray.getJSONObject(i).getString("id"));
                        affiliateOffer.setBusiness_id(jsonDealsArray.getJSONObject(i).getString(
                                "business_id"));
                        affiliateOffer.setAffiliate_id(jsonDealsArray.getJSONObject(i).getString(
                                "affiliate_id"));
                        affiliateOffer.setStatus(jsonDealsArray.getJSONObject(i).getString(
                                "status"));
                        affiliateOffer.setDeal_id(jsonDealsArray.getJSONObject(i).getString(
                                "deal_id"));
                        affiliateOffer.setAdd_time(jsonDealsArray.getJSONObject(i).getString(
                                "add_time"));
                        affiliateOffer.setBusiness_name(jsonDealsArray.getJSONObject(i).getString(
                                "business_name"));
                        affiliateOffer.setDeal_title(jsonDealsArray.getJSONObject(i).getString(
                                "deal_title"));
                        affiliateOffer.setInfo(jsonDealsArray.getJSONObject(i).getString(
                                "info"));

                        mDataList.add(affiliateOffer);
                    }
                }

                if (mDataList.size() > 0) {
                    noDataFoundLayout.setVisibility(View.INVISIBLE);
                }
                //Setup Recycler View

                initRecyclerView();
            } else {
//                new MaterialAlertDialogBuilder(getActivity(),
//                        R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
//                        .setTitle("Offers")
//                        .setMessage(jsonObject.getString("msg"))
//                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        })
//                        .show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
