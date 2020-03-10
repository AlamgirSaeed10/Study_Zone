package com.software.acuity.splick.fragments;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.software.acuity.splick.R;
import com.software.acuity.splick.activities.AddPostActivity;
import com.software.acuity.splick.activities.SelectUserType;
import com.software.acuity.splick.activities.home.AllMessagesActivity;
import com.software.acuity.splick.adapters.RecyclerNewsFeedCustomAdapter;
import com.software.acuity.splick.behaviours.RecyclerTouchListener;
import com.software.acuity.splick.interfaces.IRecyclerClickListener;
import com.software.acuity.splick.interfaces.IVolleyResponse;
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
public class HomeFragment extends Fragment implements IVolleyResponse {

    @BindView(R.id.recyclerViewNewsFeed)
    RecyclerView recyclerView;

    @BindView(R.id.logout_btn)
    ImageButton logoutBtn;

    @BindView(R.id.noDataFoundContainer)
    LinearLayout noDataFoundContainer;

    RecyclerNewsFeedCustomAdapter recyclerNewsFeedCustomAdapter;

    private RecyclerView.LayoutManager layoutManager;

    VolleyRequestClass volleyRequestClass;
    SharedPreferenceClass sharedPreferenceClass;
    String requestTag = "home_fragment";
    JSONObject userObject = null;
    List<UsersPost> usersPostList = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser)
            getActivity().getSupportFragmentManager().popBackStackImmediate(HomeFragment.class.getName(), 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_news_feed, container, false);

        ButterKnife.bind(this, fragmentView);

        sharedPreferenceClass = SharedPreferenceClass.getInstance(getApplicationContext());
        try {
            userObject = new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT));

            if (userObject.getString("user_role").trim().equalsIgnoreCase("business")) {
                logoutBtn.setImageResource(R.drawable.ic_add_post);
            }
        } catch (JSONException e) {

        }

        requestData();


        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AllMessagesActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

        return fragmentView;
    }

    public void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        recyclerNewsFeedCustomAdapter = new RecyclerNewsFeedCustomAdapter(getActivity(), usersPostList);
        recyclerView.setAdapter(recyclerNewsFeedCustomAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),
                recyclerView, new IRecyclerClickListener() {
            @Override
            public void onClick(View view, int position) {
       //         Toast.makeText(getActivity(), "Item Clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
     //           Toast.makeText(getActivity(), "Item Long Clicked", Toast.LENGTH_SHORT).show();
            }
        }));
    }

    public void requestData() {

        HashMap<String, String> params = new HashMap<>();

        sharedPreferenceClass = SharedPreferenceClass.getInstance(getApplicationContext());
        try {
            userObject = new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT));

            if (userObject.getString("user_role").trim().equalsIgnoreCase("business")) {
                logoutBtn.setImageResource(R.drawable.ic_add_post);
            }
        } catch (JSONException e) {

        }

        String userId = "";

        try {
            JSONObject userObject = new JSONObject(Constants.jsonObject);
            userId = userObject.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            if (userObject.getString("user_role").trim().equalsIgnoreCase("business")) {
                params.put("business_id", userId + "");

                volleyRequestClass = new VolleyRequestClass(getActivity(), HomeFragment.this,
                        params, "Posts", "Please wait loading Business posts!", requestTag, Request.Method.POST);
                volleyRequestClass.volleyServiceCall(Constants.BASE_URL + Constants.API_BUSINESS_VIEW_POSTS);
            } else if (userObject.getString("user_role").trim().equalsIgnoreCase("affiliate")) {
                        params.put("affiliate_id", userId + "");
                volleyRequestClass = new VolleyRequestClass(getActivity(), HomeFragment.this,
                        params, "Posts", "Please wait while loading Affiliate posts!", requestTag, Request.Method.POST);
                volleyRequestClass.volleyServiceCall(Constants.BASE_URL + Constants.API_AFFILIATE_POST_VIEW);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void networkResponse(String response) {
        try {
            usersPostList.clear();
            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.getBoolean("success")) {
                JSONArray jsonArray = jsonObject.getJSONArray("data");
              /*  if (userObject.getString("user_role").trim().equalsIgnoreCase("business")) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        UsersPost usersPost = new UsersPost();
                        JSONObject postJsonObject = jsonArray.getJSONObject(i);

                        Toast.makeText(getActivity(), i+"-> "+postJsonObject.getString("user_role"), Toast.LENGTH_SHORT).show();

                        usersPost.setId(postJsonObject.getString("id"));
                        usersPost.setTitle(postJsonObject.getString("title"));
                        usersPost.setDescription(postJsonObject.getString("description"));
                        usersPost.setMedia_id(postJsonObject.getString("file_url"));
                        usersPost.setFull_name(userObject.getString("full_name"));
                        usersPostList.add(usersPost);
                    }

                } else {

               */
                    for (int i = 0; i < jsonArray.length(); i++) {
                        UsersPost usersPost = new UsersPost();
                        JSONObject postJsonObject = jsonArray.getJSONObject(i);
                        usersPost.setId(postJsonObject.getString("id"));
                        usersPost.setTitle(postJsonObject.getString("title"));
                        usersPost.setDescription(postJsonObject.getString("description"));
                        usersPost.setMedia_id(postJsonObject.getString("media_id"));
                        usersPost.setFull_name(postJsonObject.getString("full_name"));
                        usersPost.setUser_profile(postJsonObject.getString("user_profile"));
                        usersPostList.add(usersPost);
                    }
                }

                if (usersPostList.size() > 0) {
                    noDataFoundContainer.setVisibility(View.INVISIBLE);
                }
                //Setup Recycler View
                initRecyclerView();
            } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }
}
