package com.software.acuity.splick.fragments.discover.profile;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.software.acuity.splick.R;
import com.software.acuity.splick.activities.ShowDealsActivity;
import com.software.acuity.splick.activities.home.AllMessagesActivity;
import com.software.acuity.splick.interfaces.IVolleyResponse;
import com.software.acuity.splick.models.business_deals.Business;
import com.software.acuity.splick.utils.Constants;
import com.software.acuity.splick.utils.SharedPreferenceClass;
import com.software.acuity.splick.utils.VolleyRequestClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverProfileAboutFragment extends Fragment implements IVolleyResponse {


    Business businessItem;

    @BindView(R.id.businessProfileAboutBio)
    TextView businessProfileAboutBio;

    @BindView(R.id.discoverProfileMsssageBtn)
    Button messageBtn;

    @BindView(R.id.discoverProfileFollowBtn)
    Button followBtn;

    private VolleyRequestClass volleyRequestClass;
    private String requestTag = "discpver_profile_about_fragment";
    private SharedPreferenceClass sharedPreferenceClass;

    public DiscoverProfileAboutFragment(Business businessItem) {
        // Required empty public constructor
        this.businessItem = businessItem;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.e("About", this.businessItem.getFull_name());


        View rowView = inflater.inflate(R.layout.fragment_discover_profile_about, container, false);

        ButterKnife.bind(this, rowView);


        sharedPreferenceClass = SharedPreferenceClass.getInstance(getActivity());
        businessProfileAboutBio.setText(this.businessItem.getUser_bio());


        followBtn.setOnClickListener(v -> {
            try {
                if (!(new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT)).getString("user_role").equalsIgnoreCase("business"))) {
                    if (followBtn.getText().toString().equalsIgnoreCase("follow")) {
                        followBusiness();
                    } else
                        unfollowBusiness();
                } else {
                    offerDeal();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        });
        messageBtn.setOnClickListener(v ->
                {
                    Intent intent = new Intent(getActivity(), AllMessagesActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    getContext().startActivity(intent);
                }
                );

        return rowView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            try {
                if (!(new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT)).getString("user_role").equalsIgnoreCase("business"))) {
                    checkFollowStatus();
                } else {
                    sharedPreferenceClass.setValues(Constants.AFFILIATE_ID + "", this.businessItem.getId());
                    followBtn.setText("Offer Deal");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void offerDeal() {
        Intent intent = new Intent(getActivity(), ShowDealsActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void followBusiness() {
        String userId = "";

        try {
            JSONObject userObject = new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT));
            userId = userObject.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("affiliate_id", userId + "");
        params.put("business_id", this.businessItem.getId() + "");

        volleyRequestClass = new VolleyRequestClass(getActivity(),
                DiscoverProfileAboutFragment.this,
                params, "Follow", "Following a business!", requestTag, Request.Method.POST);

        volleyRequestClass.volleyServiceCall(Constants.BASE_URL + Constants.API_AFFILIATE_USER_FOLLOW);
    }

    public void checkFollowStatus() {
        String userId = "";

        try {
            JSONObject userObject = new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT));
            userId = userObject.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("affiliate_id", userId + "");
        params.put("business_id", this.businessItem.getId() + "");

        volleyRequestClass = new VolleyRequestClass(getActivity(),
                DiscoverProfileAboutFragment.this,
                params, "Follow", "Checking Status!", requestTag, Request.Method.POST);

        volleyRequestClass.volleyServiceCall(" https://splick.aqtdemos.com/api/v1_user/verify");
    }

    public void unfollowBusiness() {
        String userId = "";

        try {
            JSONObject userObject = new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT));
            userId = userObject.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("affiliate_id", userId + "");
        params.put("business_id", this.businessItem.getId() + "");

        volleyRequestClass = new VolleyRequestClass(getActivity(),
                DiscoverProfileAboutFragment.this,
                params, "UnFollow", "Unfollowing a business!", requestTag, Request.Method.POST);

        volleyRequestClass.volleyServiceCall(Constants.BASE_URL + Constants.API_AFFILIATE_USER_UNFOLLOW);
    }

    @Override
    public void networkResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.getBoolean("success")) {

                if (jsonObject.has("msg")) {
                    if (jsonObject.getString("msg").equalsIgnoreCase("true")) {
                        followBtn.setText("Unfollow");
                    } else if (jsonObject.getString("msg").equalsIgnoreCase("false")) {
                        followBtn.setText("Follow");
                    } else if (jsonObject.getString("msg").contains("unfollowed")) {
                        followBtn.setText("Follow");
                    } else if (jsonObject.getString("msg").contains("followed")) {
                        followBtn.setText("Unfollow");
                    }
                }
            } else {
                new MaterialAlertDialogBuilder(getActivity(),
                        R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                        .setTitle("Follow")
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
