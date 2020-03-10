package com.software.acuity.splick.fragments.about;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.google.android.material.button.MaterialButton;
import com.nex3z.flowlayout.FlowLayout;
import com.software.acuity.splick.R;
import com.software.acuity.splick.interfaces.IVolleyResponse;
import com.software.acuity.splick.models.about.SocialDeal;
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
public class AboutUpperSocialInfoFragment extends Fragment implements IVolleyResponse {

    JSONObject userObject = null;
    private SharedPreferenceClass sharedPreferenceClass;
    private VolleyRequestClass volleyRequestClass;
    private String requestTag = "";

    @BindView(R.id.chipsContainer)
    FlowLayout chipsContainer;

    @BindView(R.id.nodataFoundMessageBtn)
    MaterialButton nodataFoundMessageBtn;

    private List<SocialDeal> mDataList = new ArrayList<>();

    Business businessItem;

    int[] iconArr = {R.drawable.facebook, R.drawable.instagram, R.drawable.snapchat, R.drawable.youtube, R.drawable.twitter};
    String[] iconArrStr = {"Facebook", "Instagram", "Snapchat", "YouTube", "Twitter"};

    public AboutUpperSocialInfoFragment() {
        // Required empty public constructor
    }

    public AboutUpperSocialInfoFragment(Business businessItem) {
        this.businessItem = businessItem;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rowView = inflater.inflate(R.layout.fragment_about_upper_social_info, container, false);

        ButterKnife.bind(this, rowView);

        sharedPreferenceClass = SharedPreferenceClass.getInstance(getApplicationContext());

        requestData();

        return rowView;
    }

    public void requestData() {


        if(businessItem == null){
            HashMap<String, String> params = new HashMap<>();

            String userId = "";

            try {
                userObject = new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT));
                userId = userObject.getString("id");
                params.put("user_id", userId + "");
                volleyRequestClass = new VolleyRequestClass(getActivity(),
                        AboutUpperSocialInfoFragment.this,
                        params, "Social reach", "Loading social data",
                        requestTag,
                        Request.Method.POST);

                volleyRequestClass.volleyServiceCall(Constants.BASE_URL + Constants.API_AFFILIATE_SOCIAL_VIEW);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            HashMap<String, String> params = new HashMap<>();

            String userId = "";

            try {
                userObject = new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT));
                userId = businessItem.getId();
                params.put("user_id", userId + "");
                volleyRequestClass = new VolleyRequestClass(getActivity(),
                        AboutUpperSocialInfoFragment.this,
                        params, "Social reach", "Loading social data",
                        requestTag,
                        Request.Method.POST);

                volleyRequestClass.volleyServiceCall(Constants.BASE_URL + Constants.API_AFFILIATE_SOCIAL_VIEW);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    public void networkResponse(String response) {

        try {
            JSONObject socialInfoJsonObject = new JSONObject(response);

            if (socialInfoJsonObject.getBoolean("success")) {
                JSONArray dataArray = socialInfoJsonObject.getJSONArray("data");

                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject socialObject = dataArray.getJSONObject(i);

                    SocialDeal socialDeal = new SocialDeal();

                    socialDeal.setId(socialObject.getString("id"));
                    socialDeal.setUser_id(socialObject.getString("user_id"));
                    socialDeal.setAccount_type(socialObject.getString("account_type"));
                    socialDeal.setAccount_id(socialObject.getString("account_id"));
                    socialDeal.setAccount_link(socialObject.getString("account_link"));
                    socialDeal.setTotal_followers(socialObject.getString("total_followers"));

                    mDataList.add(socialDeal);

                    if (mDataList.size() > 0) {
                        nodataFoundMessageBtn.setVisibility(View.INVISIBLE);
                    } else {
                        nodataFoundMessageBtn.setVisibility(View.VISIBLE);
                    }
                }

                for (int j = 0; j < mDataList.size(); j++) {
                    View viewChip = LayoutInflater.from(getActivity()).inflate(R.layout.chip_view_social_reach, null);
                    MaterialButton tagChip = viewChip.findViewById(R.id.chip);
                    tagChip.setIcon(getActivity().getResources().getDrawable(iconArr[getIconIndex(mDataList.get(j).getAccount_type())]));
                    tagChip.setText(mDataList.get(j).getTotal_followers() + "");

                    chipsContainer.addView(viewChip);
                }
            } else {
                if (mDataList.size() > 0) {
                    nodataFoundMessageBtn.setVisibility(View.INVISIBLE);
                } else {
                    nodataFoundMessageBtn.setVisibility(View.VISIBLE);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getIconIndex(String text) {
        for (int i = 0; i < iconArrStr.length; i++) {
            if (iconArrStr[i].equalsIgnoreCase(text + "")) {
                return i;
            }
        }
        return 0;
    }
}
