package com.software.acuity.splick.fragments.discover;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.software.acuity.splick.R;
import com.software.acuity.splick.activities.DashBoardActivity;
import com.software.acuity.splick.activities.discover.FiltersActivity;
import com.software.acuity.splick.activities.discover.profile.DiscoverProfileActivity;
import com.software.acuity.splick.adapters.GridViewSearchFeedCustomAdapter;
import com.software.acuity.splick.behaviours.RecyclerTouchListener;
import com.software.acuity.splick.fragments.HomeFragment;
import com.software.acuity.splick.interfaces.IRecyclerClickListener;
import com.software.acuity.splick.interfaces.IVolleyResponse;
import com.software.acuity.splick.models.business_deals.Business;
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
public class SearchFragment extends Fragment implements IVolleyResponse {

    @BindView(R.id.recyclerViewSearchFeed)
    RecyclerView recyclerView;

    @BindView(R.id.tagsContainer)
    LinearLayout linearLayout;

    @BindView(R.id.searchBtn)
    ImageButton searchBtn;

    int visibleItems = 0;
    int totalItemsReturned = 0;
    int totalItems = 0;
    int currentItem = 0;
    int scrolledOutItems = 0;
//
//    @BindView(R.id.chipFitness)
//    Chip chipFitness;
//    @BindView(R.id.chipFilmMaking)
//    Chip chipFilmMaking;
//    @BindView(R.id.chipSports)
//    Chip chipSports;
//    @BindView(R.id.chipTrade)
//    Chip chipTrade;
//    @BindView(R.id.chipPhotography)
//    Chip chipPhotography;

    @BindView(R.id.discoverSearchEt)
    EditText discoverSearchEt;

    @BindView(R.id.filterBtn)
    MaterialButton filterBtn;

    @BindView(R.id.loadMoreBtn)
    MaterialButton loadMoreBtn;

    SharedPreferenceClass sharedPreferenceClass;

    JSONObject jsonObject =
            null;

    public static String tagsString = "";

    GridViewSearchFeedCustomAdapter gridViewSearchFeedCustomAdapter;

    private RecyclerView.LayoutManager layoutManager;
    private VolleyRequestClass volleyRequestClass;
    private String requestTag = "discover_fragment";

    public List<Business> businessList = new ArrayList<>();

    @BindView(R.id.nodataFoundMessage)
    LinearLayout noDataFoundLayout;

    LayoutInflater layoutInflater;

    private boolean isScrolling = false;
    private boolean hasLoadedAllItems = false;
    private int totalPages = 0;
    private int currentPage = 1;
    private int firstLoadForScroll = 0;
    public boolean isAdapterSet = false;

    public SearchFragment() {
        // Required empty public constructor
        isAdapterSet = false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser)
            getActivity().getSupportFragmentManager().popBackStackImmediate(HomeFragment.class.getName(), 0);
    }

    List<String> selectedTags = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_search, container, false);

        ButterKnife.bind(this, fragmentView);

        sharedPreferenceClass = SharedPreferenceClass.getInstance(getApplicationContext());

        //TODO: Refractor

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                businessList.clear();
                requestData(discoverSearchEt.getText().toString() + "", "");
                discoverSearchEt.clearFocus();
                InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(discoverSearchEt.getWindowToken(), 0);
            }
        });

        discoverSearchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                businessList.clear();

//                loadMoreBtn.setVisibility(View.VISIBLE);
                if (s.toString().trim().equalsIgnoreCase("")) {

                    firstLoadForScroll = 0;
                    currentPage = 1;
                    name = s.toString().trim();
                    requestData(name, convertArrayIntoString());
                } else {
                    name = s.toString().trim();
                }
            }
        });

        ((DashBoardActivity) getActivity()).setOnBundleSelected(new DashBoardActivity.SelectedBundle() {
            @Override
            public void onBundleSelect(Bundle bundle) {
                businessList.clear();
                linearLayout.removeAllViews();
                String[] tags = bundle.getString("tags").split(",");
                for (int i = 0; i < bundle.getString("tags").split(",").length; i++) {
                    View viewChip = inflater.inflate(R.layout.chip_view, null);
                    Chip tagChip = viewChip.findViewById(R.id.chip);
                    tagChip.setChecked(true);
                    tagChip.setText(tags[i].trim() + "");

                    //selected tags
                    selectedTags.add(tags[i].trim());
                    tagChip.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (!tagChip.isChecked()) {
                                selectedTags.remove(findTag(tagChip.getText().toString().trim()));
                            } else {
                                selectedTags.add(tagChip.getText().toString().trim());
                            }
                            businessList.clear();

                            if (selectedTags.size() > 0) {
                                requestData(name, convertArrayIntoString() + "");
                            } else {
                                currentPage = 1;
                                requestData(name, convertArrayIntoString() + "");
                            }
                        }
                    });

                    tagChip.setOnCloseIconClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            selectedTags.remove(findTag(tagChip.getText().toString().trim()));
                            businessList.clear();
                            requestData(name, convertArrayIntoString() + "");
                            linearLayout.removeView(viewChip);
                        }
                    });


                    linearLayout.addView(viewChip);
                }

                requestData(discoverSearchEt.getText().toString() + "", convertArrayIntoString() + "");
            }
        });

        discoverSearchEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    businessList.clear();
                    requestData(discoverSearchEt.getText().toString() + "", "");
                    discoverSearchEt.clearFocus();
                    InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(discoverSearchEt.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), FiltersActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                getActivity().startActivityForResult(intent, 2000);
            }
        });

        try {
            jsonObject = new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        requestData(name, convertArrayIntoString());

        loadMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalItemsReturned > 10) {
                    if (gridViewSearchFeedCustomAdapter.getItemCount() <= totalItemsReturned) {
                        if (currentPage <= totalPages) {
                            requestData(10 * currentPage);
                            currentPage++;
                        } else {
                            loadMoreBtn.setVisibility(View.GONE);
                        }
                    }
                } else {
                    if (currentPage <= totalPages) {
                        currentPage++;
                        requestData(10 * currentPage);
                    } else {
                        loadMoreBtn.setVisibility(View.GONE);
                    }
                }
            }
        });

        return fragmentView;
    }

    public String convertArrayIntoString() {
        StringBuilder csvBuilder = new StringBuilder();
        for (String tag : selectedTags) {
            csvBuilder.append(tag);
            csvBuilder.append(",");
        }
        String tagsString = csvBuilder.toString();
        tagsString = tagsString.replaceAll(",$", "");

        return tagsString;
    }

    public void initRecyclerView() {
        recyclerView.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(layoutManager);

        gridViewSearchFeedCustomAdapter = new GridViewSearchFeedCustomAdapter(getActivity(),
                businessList);
        recyclerView.setAdapter(gridViewSearchFeedCustomAdapter);

        isAdapterSet = true;

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),
                recyclerView, new IRecyclerClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getActivity(), DiscoverProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                intent.putExtra("business_item_object", businessList.get(position));
                startActivity(intent);
             }

            @Override
            public void onLongClick(View view, int position) {
             }
        }));

        if (firstLoadForScroll < 1) {
            firstLoadForScroll++;
            if (currentPage <= totalPages) {
                requestData(10 * currentPage);
                currentPage++;
            } else {
                loadMoreBtn.setVisibility(View.GONE);
            }
        }

        if (gridViewSearchFeedCustomAdapter.getItemCount() == totalItemsReturned) {
            loadMoreBtn.setVisibility(View.GONE);
        }

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }
        });

        loadMoreBtn.setText("Load More (" + gridViewSearchFeedCustomAdapter.getItemCount() + "/" + totalItemsReturned + ")");
    }

    String name = "", tags = "";

    public int findTag(String tag) {
        for (int i = 0; i < selectedTags.size(); i++) {
            if (selectedTags.get(i).trim().equalsIgnoreCase(tag)) {
                return i;
            }
        }
        return 0;
    }

    public void requestData(String name, String tags) {

        HashMap<String, String> params = new HashMap<>();

        if (!name.trim().isEmpty()) {
            params.put("name", this.name + "");
        }
        if (!tags.trim().isEmpty()) {
            params.put("tags", tags + "");
        }

        if (!(params.size() > 0)) {
            firstLoadForScroll = 0;
        }

        try {

            if (jsonObject.getString("user_role").trim().equalsIgnoreCase("business")) {
                volleyRequestClass = new VolleyRequestClass(getActivity(), SearchFragment.this,
                        params, "List of affiliates", "Please wait while we are loading Affiliates!",
                        requestTag,
                        Request.Method.POST);

                volleyRequestClass.volleyServiceCall(Constants.BASE_URL + Constants.API_BUSINESS_AFFILIATE_LIST);
            } else if (jsonObject.getString("user_role").trim().equalsIgnoreCase("affiliate")) {
                volleyRequestClass = new VolleyRequestClass(getActivity(), SearchFragment.this,
                        params, "List of businesses", "Please wait while we are loading Businesses!",
                        requestTag,
                        Request.Method.POST);

                volleyRequestClass.volleyServiceCall(Constants.BASE_URL + Constants.API_AFFILIATE_BUSINESS_LIST);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void requestData(int nextPage) {

        loadMoreBtn.setVisibility(View.VISIBLE);
        HashMap<String, String> params = new HashMap<>();

        if (!name.trim().isEmpty()) {
            params.put("name", name + "");
            params.put("tags[]", tags + "");
        } else if (!tags.trim().isEmpty()) {
            params.put("name", name + "");
            params.put("tags[]", tags + "");
        }

        try {

            if (jsonObject.getString("user_role").trim().equalsIgnoreCase("business")) {
                volleyRequestClass = new VolleyRequestClass(getActivity(), SearchFragment.this,
                        params, "List of affiliates", "Please wait while we are loading Affiliates!",
                        requestTag,
                        Request.Method.POST);

                volleyRequestClass.volleyServiceCallWithoutLoading(Constants.BASE_URL + Constants.API_BUSINESS_AFFILIATE_LIST + "/" + nextPage);
            } else if (jsonObject.getString("user_role").trim().equalsIgnoreCase("affiliate")) {
                volleyRequestClass = new VolleyRequestClass(getActivity(), SearchFragment.this,
                        params, "List of businesses", "Please wait while we are loading Businesses!",
                        requestTag,
                        Request.Method.POST);

                volleyRequestClass.volleyServiceCallWithoutLoading(Constants.BASE_URL + Constants.API_AFFILIATE_BUSINESS_LIST + "/" + nextPage);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void networkResponse(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getBoolean("success")) {
                totalItemsReturned = jsonObject.getInt("total");
                totalPages = (totalItemsReturned / 10);

                if (jsonObject.has("msg")) {
                    noDataFoundLayout.setVisibility(View.VISIBLE);
                } else if (jsonObject.has("data")) {
                    JSONArray businessesJsonArray = jsonObject.getJSONArray("data");

                    //Iterate over a list of businesses
                    for (int i = 0; i < businessesJsonArray.length(); i++) {
                        JSONObject jsonBusinessItem = businessesJsonArray.getJSONObject(i);

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
                        businessItemModel.setProfile_path(jsonBusinessItem.getString(
                                "profile_path"));

                        businessList.add(businessItemModel);
                    }

                    if (businessList.size() > 0) {
                        noDataFoundLayout.setVisibility(View.INVISIBLE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }

                    initRecyclerView();
                }
            } else {
                businessList.clear();
                initRecyclerView();
                recyclerView.setVisibility(View.INVISIBLE);
                noDataFoundLayout.setVisibility(View.VISIBLE);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                visibleItems = layoutManager.getChildCount();
//                totalItems = layoutManager.getItemCount();
//                scrolledOutItems = ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
//
////                if(visibleItems){}
//
//
//                try {
//
////                    if (totalItems > 10) {
////                        if (isScrolling && (visibleItems + scrolledOutItems == totalItems)) {
////                            isScrolling = false;
////                            if (totalItems <= totalItemsReturned) {
////                                if (currentPage <= totalPages) {
////                                    currentPage++;
////                                    requestData(10 * currentPage);
////                                } else {
////                                    progressBar.setVisibility(View.GONE);
//////                                Toast.makeText(getApplicationContext(), "All items loaded", Toast.LENGTH_SHORT).show();
////                                }
////                            }
////                        }
////                    } else {
////                        if (currentPage <= totalPages) {
////                            currentPage++;
////                            requestData(10 * currentPage);
////                        } else {
////                            progressBar.setVisibility(View.GONE);
////
//////                        Toast.makeText(getApplicationContext(), "All items loaded", Toast.LENGTH_SHORT).show();
////                        }
////                    }
//
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                progressBar.setVisibility(View.GONE);
//            }

