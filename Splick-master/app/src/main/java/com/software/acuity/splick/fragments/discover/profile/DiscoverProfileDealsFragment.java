package com.software.acuity.splick.fragments.discover.profile;


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
import com.software.acuity.splick.activities.discover.profile.DiscoverProfileActivity;
import com.software.acuity.splick.adapters.BusinessProfileDealsAdapter;
import com.software.acuity.splick.behaviours.RecyclerTouchListener;
import com.software.acuity.splick.interfaces.IRecyclerClickListener;
import com.software.acuity.splick.interfaces.IVolleyResponse;
import com.software.acuity.splick.models.business_deals.Business;
import com.software.acuity.splick.models.business_deals.BusinessDeal;
import com.software.acuity.splick.utils.Constants;
import com.software.acuity.splick.utils.VolleyRequestClass;

import org.json.JSONArray;
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
public class DiscoverProfileDealsFragment extends Fragment implements IVolleyResponse {


    Business businessItem;

    private VolleyRequestClass volleyRequestClass;
    private String requestTag = "deals_fragment";

    @BindView(R.id.profile_deals_recycler_view)
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    BusinessProfileDealsAdapter businessProfileDealsAdapter;

    @BindView(R.id.nodataFoundMessage)
    LinearLayout nodataFoundMessage;

    public DiscoverProfileDealsFragment(Business businessItem) {
        // Required empty public constructor
        this.businessItem = businessItem;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rowView = inflater.inflate(R.layout.fragment_discover_profile_deals, container, false);

        ButterKnife.bind(this, rowView);

        return rowView;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    public void initRecyclerView(Business businessItem) {
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        businessProfileDealsAdapter = new BusinessProfileDealsAdapter(getActivity(),
                businessItem.getBusinessDeals());
        recyclerView.setAdapter(businessProfileDealsAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),
                recyclerView, new IRecyclerClickListener() {
            @Override
            public void onClick(View view, int position) {
//                Toast.makeText(getActivity(), "Item Clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
//                Toast.makeText(getActivity(), "Item Long Clicked", Toast.LENGTH_SHORT).show();
            }
        }));
    }

    public void requestData() {

        HashMap<String, String> params = new HashMap<>();
        params.put("business_id", businessItem.getId() + "");

        volleyRequestClass = new VolleyRequestClass(DiscoverProfileActivity.activityContext,
                DiscoverProfileDealsFragment.this,
                params, "Business Profile", "Loading business profile",
                requestTag,
                Request.Method.POST);

        volleyRequestClass.volleyServiceCall(Constants.BASE_URL + Constants.API_AFFILIATE_VIEW_BUSINESS);
    }

    @Override
    public void networkResponse(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.getBoolean("success")) {
                if (jsonObject.has("msg")) {
//                    new MaterialAlertDialogBuilder(getActivity(),
//                            R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
//                            .setTitle("Deals")
//                            .setMessage("No deals found")
//                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//
//                                }
//                            })
//                            .show();
                } else if (jsonObject.has("data")) {
                    JSONObject jsonBusinessItem = jsonObject.getJSONObject("data");

                    Business businessItemModel = new Business();
                    businessItemModel.setId(jsonBusinessItem.getString("id"));
                    businessItemModel.setFull_name(jsonBusinessItem.getString("full_name"));
                    businessItemModel.setUser_role(jsonBusinessItem.getString("user_role"));
                    businessItemModel.setUser_name(jsonBusinessItem.getString("user_name"));
                    businessItemModel.setUser_email(jsonBusinessItem.getString("user_email"));
                    businessItemModel.setUser_bio(jsonBusinessItem.getString("user_bio"));
                    businessItemModel.setUser_location(jsonBusinessItem.getString(
                            "user_location"));
                    businessItemModel.setLocation_lat(jsonBusinessItem.getString(
                            "location_lat"));
                    businessItemModel.setLocation_lng(jsonBusinessItem.getString(
                            "location_lng"));
                    businessItemModel.setInsta_userid(jsonBusinessItem.getString(
                            "insta_userid"));
                    businessItemModel.setInsta_username(jsonBusinessItem.getString(
                            "insta_username"));
                    businessItemModel.setYoutube_username(jsonBusinessItem.getString(
                            "youtube_username"));
                    businessItemModel.setSnapchat_username(jsonBusinessItem.getString(
                            "snapchat_username"));
                    businessItemModel.setSnapchat_username(jsonBusinessItem.getString(
                            "website_url"));
                    businessItemModel.setIndustry_tags(jsonBusinessItem.getString(
                            "industry_tags"));
                    businessItemModel.setUser_profile(jsonBusinessItem.getString(
                            "user_profile"));
                    businessItemModel.setUser_banner(jsonBusinessItem.getString("user_banner"));
                    businessItemModel.setUser_status(jsonBusinessItem.getString("user_status"));
                    businessItemModel.setAdd_time(jsonBusinessItem.getString("add_time"));
//                        businessItemModel.setProfile_path(jsonBusinessItem.getString(
//                                "profile_path"));

                    JSONArray dealsArray = jsonBusinessItem.getJSONArray("deals");

                    List<BusinessDeal> businessDealsList = new ArrayList<>();

                    for (int j = 0; j < dealsArray.length(); j++) {

                        BusinessDeal businessDeal = new BusinessDeal();

                        businessDeal.setId(dealsArray.getJSONObject(j).getString("id"));
                        businessDeal.setUser_id(dealsArray.getJSONObject(j).getString(
                                "user_id"));
                        businessDeal.setDeal_title(dealsArray.getJSONObject(j).getString(
                                "deal_title"));
                        businessDeal.setDeal_url(dealsArray.getJSONObject(j).getString(
                                "deal_url"));
                        businessDeal.setDeal_details(dealsArray.getJSONObject(j).getString(
                                "deal_details"));
                        businessDeal.setComm_amount(dealsArray.getJSONObject(j).getString(
                                "comm_amount"
                        ));
                        businessDeal.setComm_type(dealsArray.getJSONObject(j).getString(
                                "comm_type"));
                        businessDeal.setDeal_status(dealsArray.getJSONObject(j).getString(
                                "deal_status"
                        ));
                        businessDeal.setAdd_time(dealsArray.getJSONObject(j).getString(
                                "add_time"));

                        businessDealsList.add(businessDeal);
                    }

                    businessItemModel.setBusinessDeals(businessDealsList);

                    nodataFoundMessage.setVisibility(View.INVISIBLE);

                    //Setup Recycler View
                    initRecyclerView(businessItemModel);
                }

            } else if (jsonObject.has("msg")) {
//                new MaterialAlertDialogBuilder(getActivity(),
//                        R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
//                        .setTitle("Deals")
//                        .setMessage("No deals found")
//                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                            }
//                        })
//                        .show();

            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            requestData();
        } else {
            // fragment is no longer visible
        }
    }
}
