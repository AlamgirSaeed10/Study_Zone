package com.software.acuity.splick.fragments.about;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.software.acuity.splick.R;
import com.software.acuity.splick.activities.PortfolioActivity;
import com.software.acuity.splick.adapters.DiscoverProfileBusinessNewsFeedAdapter;
import com.software.acuity.splick.adapters.DiscoverProfileNewsFeedAdapter;
import com.software.acuity.splick.behaviours.RecyclerTouchListener;
import com.software.acuity.splick.interfaces.IRecyclerClickListener;
import com.software.acuity.splick.interfaces.IVolleyResponse;
import com.software.acuity.splick.models.PortfolioModel;
import com.software.acuity.splick.models.home.UsersPost;
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
public class AboutTabPortfolioFragment extends Fragment implements IVolleyResponse {

    private VolleyRequestClass volleyRequestClass;
    private String requestTag = "deals_fragment";

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    DiscoverProfileNewsFeedAdapter discoverProfileNewsFeedAdapter;
    DiscoverProfileBusinessNewsFeedAdapter discoverProfileBusinessNewsFeedAdapter;
    SharedPreferenceClass sharedPreferenceClass;

    List<PortfolioModel> portfolioList = new ArrayList<>();

    List<UsersPost> usersPostList = new ArrayList<>();

    @BindView(R.id.nodataFoundMessage)
    LinearLayout nodataFoundMessage;

    Intent intent = null;

    public AboutTabPortfolioFragment() {
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

//            getActivity().getSupportFragmentManager().popBackStackImmediate(HomeFragment.class.getName(), 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View rowView = inflater.inflate(R.layout.fragment_about_tab_portfolio, container, false);
        ButterKnife.bind(this, rowView);

        sharedPreferenceClass = SharedPreferenceClass.getInstance(getApplicationContext());
        intent = new Intent(getActivity(), PortfolioActivity.class);

        return rowView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        requestData();
    }

    public void initRecyclerView() {
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.setLayoutManager(layoutManager);

        try {
            userObject = new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT));

            if (userObject.getString("user_role").trim().equalsIgnoreCase("business")) {
                discoverProfileBusinessNewsFeedAdapter = new DiscoverProfileBusinessNewsFeedAdapter(getActivity(),
                        usersPostList);

                recyclerView.setAdapter(discoverProfileBusinessNewsFeedAdapter);
            } else {
                discoverProfileNewsFeedAdapter = new DiscoverProfileNewsFeedAdapter(getActivity(),
                        portfolioList);

                recyclerView.setAdapter(discoverProfileNewsFeedAdapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),
                recyclerView, new IRecyclerClickListener() {
            @Override
            public void onClick(View view, int position) {

                try {
                    if (userObject.getString("user_role").equalsIgnoreCase("business")) {
                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        intent.putExtra("post_obj", usersPostList.get(position));
                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                    } else {
                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        intent.putExtra("portfolio_obj", portfolioList.get(position));
                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent);
                    }
                } catch (JSONException e) {

                }
//                Toast.makeText(getActivity(), "Item Clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
//                Toast.makeText(getActivity(), "Item Long Clicked", Toast.LENGTH_SHORT).show();
            }
        }));
    }

    JSONObject userObject = null;

    public void requestData() {

        HashMap<String, String> params = new HashMap<>();

        String userId = "";

        try {
            userObject = new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT));


            userId = userObject.getString("id");


            if (userObject.getString("user_role").trim().equalsIgnoreCase("business")) {

                params.put("business_id", userId + "");
                volleyRequestClass = new VolleyRequestClass(getActivity(),
                        AboutTabPortfolioFragment.this,
                        params, "News Feed", "Loading news feed",
                        requestTag,
                        Request.Method.POST);

                volleyRequestClass.volleyServiceCall(Constants.BASE_URL + Constants.API_BUSINESS_VIEW_POSTS);
            } else {

                params.put("user_id", userId + "");
                volleyRequestClass = new VolleyRequestClass(getActivity(),
                        AboutTabPortfolioFragment.this,
                        params, "Portfolio", "Portfolios",
                        requestTag,
                        Request.Method.POST);

                volleyRequestClass.volleyServiceCall(Constants.BASE_URL + Constants.API_VIEW_PORTFOLIO);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void networkResponse(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);

            usersPostList.clear();
            portfolioList.clear();

            if (jsonObject.getBoolean("success")) {
                if (jsonObject.has("msg")) {
                    new MaterialAlertDialogBuilder(getActivity(),
                            R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                            .setTitle("Portfolio")
                            .setMessage(jsonObject.getString("msg"))
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                } else if (jsonObject.has("data")) {
                    JSONArray jsonBusinessItem = jsonObject.getJSONArray("data");

                    if (userObject.getString("user_role").trim().equalsIgnoreCase("business")) {
                        for (int i = 0; i < jsonBusinessItem.length(); i++) {
                            UsersPost usersPost = new UsersPost();

                            //Parse Object to get the post items
                            JSONObject postJsonObject = jsonBusinessItem.getJSONObject(i);

                            usersPost.setId(postJsonObject.getString("id"));
                            usersPost.setTitle(postJsonObject.getString("title"));
                            usersPost.setDescription(postJsonObject.getString("description"));
                            usersPost.setFile_url(postJsonObject.getString("file_url"));
                            usersPost.setFull_name(userObject.getString("full_name"));
//                        post.setUser_profile(postJsonObject.getString("user_profile"));

                            usersPostList.add(usersPost);
                        }

                    } else {
                        for (int i = 0; i < jsonBusinessItem.length(); i++) {
                            JSONObject portfolioObject = jsonBusinessItem.getJSONObject(i);

                            PortfolioModel portfolioModel = new PortfolioModel();

                            portfolioModel.setId(portfolioObject.getString("id"));
                            portfolioModel.setUser_id(portfolioObject.getString("user_id"));
                            portfolioModel.setTitle(portfolioObject.getString("title"));
                            portfolioModel.setDescription(portfolioObject.getString("description"));
                            portfolioModel.setAdd_time(portfolioObject.getString("add_time"));
                            portfolioModel.setFile_url(portfolioObject.getString("file_url"));
                            portfolioModel.setFullName(userObject.getString("full_name"));

                            portfolioList.add(portfolioModel);
                        }
                    }


                    if (portfolioList.size() > 0 || usersPostList.size() > 0) {
                        nodataFoundMessage.setVisibility(View.INVISIBLE);
                    }

                    //Setup Recycler View
                    initRecyclerView();
                }

            } else if (!jsonObject.getBoolean("success")) {
                if (jsonObject.has("msg")) {
                    nodataFoundMessage.setVisibility(View.VISIBLE);
//                    new MaterialAlertDialogBuilder(getActivity(),
//                            R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
//                            .setTitle("Portfolio")
//                            .setMessage(jsonObject.getString("msg"))
//                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//
//                                }
//                            })
//                            .show();
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

}
