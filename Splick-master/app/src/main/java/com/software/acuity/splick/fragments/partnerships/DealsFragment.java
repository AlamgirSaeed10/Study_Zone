package com.software.acuity.splick.fragments.partnerships;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.software.acuity.splick.R;
import com.software.acuity.splick.activities.DashBoardActivity;
import com.software.acuity.splick.activities.partnerships.PartnershipDetailsActivity;
import com.software.acuity.splick.adapters.RecyclerViewDealsCustomAdapter;
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
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class DealsFragment extends Fragment implements IVolleyResponse  {

    @BindView(R.id.recyclerViewDealsFragment)
    RecyclerView recyclerView;

    @BindView(R.id.sort_spinner)
    Spinner sort_spinner;

    @BindView(R.id.sort_text)
    TextView sort_text;

    @BindView(R.id.linear_sort)
    LinearLayout linear_sort;

    RecyclerViewDealsCustomAdapter recyclerViewDealsCustomAdapter;

    private RecyclerView.LayoutManager layoutManager;
    private VolleyRequestClass volleyRequestClass;
    private String requestTag = "deals_fragment";

    @BindView(R.id.nodataFoundMessage)
    LinearLayout nodataFoundMessageLayout;

    List<AffiliateDeals> mDataList = new ArrayList<>();

    JSONObject userObject = null;
    private SharedPreferenceClass sharedPreferenceClass;

    String[] sort_array = {"Latest Added","Highest Revenue","Most Clicks","Most Added to cart"};


    public DealsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_deals, container, false);

        sharedPreferenceClass = SharedPreferenceClass.getInstance(DashBoardActivity.dashboardActvityContext);
//        try {
//            userObject = new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


//        try {
//            userId = new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT)).getString("id");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

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

        recyclerViewDealsCustomAdapter = new RecyclerViewDealsCustomAdapter(getActivity(),
                mDataList);
        recyclerView.setAdapter(recyclerViewDealsCustomAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),
                recyclerView, new IRecyclerClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getActivity(), PartnershipDetailsActivity.class);
                intent.putExtra("deal_obj", mDataList.get(position));
            //    getFragmentManager().beginTransaction().addToBackStack("deal");
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
    }


    public void requestData() {
        HashMap<String, String> params = new HashMap<>();

        try {

            String data = Constants.jsonObject;
            userObject = new JSONObject(Constants.jsonObject);

            if (userObject.getString("user_role").trim().equalsIgnoreCase("business")) {
                params.put("user_id", new JSONObject(Constants.jsonObject).getString("id") + "");
//                params.put("user_id","3");

                volleyRequestClass = new VolleyRequestClass(DashBoardActivity.dashboardActvityContext, DealsFragment.this,
                        params, "Deals", "Loading deals!", requestTag, Request.Method.POST);


                volleyRequestClass.volleyServiceCall(Constants.BASE_URL + Constants.API_BUSINESS_AFFILIATE_WISE_EARNING);

            } else if (userObject.getString("user_role").trim().equalsIgnoreCase("affiliate")) {
                params.put("user_id", new JSONObject(Constants.jsonObject).getString("id") + "");

                volleyRequestClass = new VolleyRequestClass(DashBoardActivity.dashboardActvityContext, DealsFragment.this,
                        params, "Deals", "Loading deals!", requestTag, Request.Method.POST);


                volleyRequestClass.volleyServiceCall(Constants.BASE_URL + Constants.API_AFFILIATE_STATS_DEALS_DISCOVER);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        volleyRequestClass.volleyServiceCall(Constants.BASE_URL + Constants.API_AFFILIATE_STAT_AFFILIATIONS_AFFILIATE);
    }


    @Override
    public void networkResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);

            mDataList.clear();

            if (jsonObject.getBoolean("success")) {

                if (userObject.getString("user_role").trim().equalsIgnoreCase("business")) {
                    ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,sort_array);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sort_spinner.setAdapter(adapter);

                    JSONObject jsonDealsObject = jsonObject.getJSONObject("data").getJSONObject(
                            "affiliates");

                    for (Iterator<String> it = jsonDealsObject.keys(); it.hasNext(); ) {
                        String key = it.next();

                        AffiliateDeals affiliateDeals = new AffiliateDeals();

                        affiliateDeals.setAffiliate_id(jsonDealsObject.getJSONObject(key + "").getString("affiliate_id"));
                        affiliateDeals.setAff_name(jsonDealsObject.getJSONObject(key + "").getString("aff_name"));
                        affiliateDeals.setViews(jsonDealsObject.getJSONObject(key + "").getInt("views"));
                        affiliateDeals.setOrders(jsonDealsObject.getJSONObject(key + "").getInt("orders") + "");
                        affiliateDeals.setRevenue(jsonDealsObject.getJSONObject(key + "").getInt("revenue") + "");
                        mDataList.add(affiliateDeals);
                        Collections.sort(mDataList, new Comparator<AffiliateDeals>() {
                            @Override
                            public int compare(AffiliateDeals o1, AffiliateDeals o2) {
                                Toast.makeText(getApplicationContext(), "Sorted", Toast.LENGTH_SHORT).show();
                                return Integer.valueOf(o1.getRevenue()).compareTo(Integer.valueOf(o2.getRevenue()));

                            }
                        });
                    }

                } else {
                    JSONObject jsonDealsObject = jsonObject.getJSONObject("data").getJSONObject(
                            "deals");

                    for (Iterator<String> it = jsonDealsObject.keys(); it.hasNext(); ) {
                        String key = it.next();

                        AffiliateDeals affiliateDeals = new AffiliateDeals();

                        affiliateDeals.setDeal_id(jsonDealsObject.getJSONObject(key + "").getString("deal_id"));
                        affiliateDeals.setDeal_name(jsonDealsObject.getJSONObject(key + "").getString("deal_name"));
                        affiliateDeals.setViews(jsonDealsObject.getJSONObject(key + "").getInt("views"));
                        affiliateDeals.setOrders(jsonDealsObject.getJSONObject(key + "").getString("orders"));
                        affiliateDeals.setRevenue(jsonDealsObject.getJSONObject(key + "").getString("revenue"));
                        affiliateDeals.setCommission(jsonDealsObject.getJSONObject(key + "").getString("commission"));
                        affiliateDeals.setDeal_url(jsonDealsObject.getJSONObject(key + "").getString("deal_url"));
                        mDataList.add(affiliateDeals);
                        sort_spinner.setVisibility(View.INVISIBLE);
                        sort_text.setVisibility(View.INVISIBLE);
                        linear_sort.setVisibility(View.INVISIBLE);

                    }
                }

                //Hide layout if list contains data
                if (mDataList.size() > 0)
                    nodataFoundMessageLayout.setVisibility(View.INVISIBLE);
                initRecyclerView();
            } else {
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
