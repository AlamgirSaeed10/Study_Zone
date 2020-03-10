package com.software.acuity.splick.fragments.partnerships;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.software.acuity.splick.R;
import com.software.acuity.splick.activities.DashBoardActivity;
import com.software.acuity.splick.activities.partnerships.PartnershipDetailsActivity;
import com.software.acuity.splick.interfaces.IVolleyResponse;
import com.software.acuity.splick.models.MarketingMaterialModel;
import com.software.acuity.splick.models.partnerships.AffiliateDeals;
import com.software.acuity.splick.utils.Constants;
import com.software.acuity.splick.utils.UtilsClass;
import com.software.acuity.splick.utils.VolleyRequestClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MarketingFragment extends Fragment implements IVolleyResponse {

    private final AffiliateDeals affiliateDeals;


    @BindView(R.id.marketingMaterialListView)
    ListView listView;

    List<MarketingMaterialModel> mDataList = new ArrayList<>();

    private String requestTag = "add_new_post";
    private VolleyRequestClass volleyRequestClass;

    @BindView(R.id.nodataFoundMessageMarketing)
    LinearLayout noDataMessageLayout;


    public MarketingFragment() {
        // Required empty public constructor
        affiliateDeals = PartnershipDetailsActivity.affiliateDeals;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rowView = inflater.inflate(R.layout.fragment_marketing, container, false);

        ButterKnife.bind(this, rowView);

        requestData();

        return rowView;
    }

    public void requestData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("deal_id", affiliateDeals.getDeal_id() + "");

        volleyRequestClass = new VolleyRequestClass(DashBoardActivity.dashboardActvityContext, MarketingFragment.this,
                params, "Marketing material", "Loading material!", requestTag, Request.Method.POST);

        volleyRequestClass.volleyServiceCall(Constants.BASE_URL + Constants.API_BUSINESS_VIEW_MATERIAL);
    }

    @Override
    public void networkResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.getBoolean("success")) {

                JSONArray jsonDataArray = jsonObject.getJSONArray("data");

                for (int i = 0; i < jsonDataArray.length(); i++) {
                    JSONObject marketingObject = jsonDataArray.getJSONObject(i);

                    MarketingMaterialModel marketingMaterialModel = new MarketingMaterialModel();

                    marketingMaterialModel.setId(marketingObject.getString("id"));
                    marketingMaterialModel.setDeal_id(marketingObject.getString("deal_id"));
                    marketingMaterialModel.setUser_id(marketingObject.getString("user_id"));
                    marketingMaterialModel.setMat_title(marketingObject.getString("mat_title"));
                    marketingMaterialModel.setMat_name(marketingObject.getString("mat_name"));
                    marketingMaterialModel.setMat_type(marketingObject.getString("mat_type"));
                    marketingMaterialModel.setMat_size(marketingObject.getString("mat_size"));
                    marketingMaterialModel.setMat_details(marketingObject.getString("mat_details"));
                    marketingMaterialModel.setMat_path(marketingObject.getString("mat_path"));
                    marketingMaterialModel.setAdd_time(marketingObject.getString("add_time"));

                    mDataList.add(marketingMaterialModel);
                }

                if (mDataList.size() > 0) {
                    noDataMessageLayout.setVisibility(View.INVISIBLE);
                }

                listView.setAdapter(new MarketingMaterialAdapter());
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

    public class MarketingMaterialAdapter extends BaseAdapter {

        public MarketingMaterialAdapter() {
        }

        @Override
        public int getCount() {
            return mDataList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = LayoutInflater.from(getActivity()).inflate(R.layout.row_marketing_material, null);

            ((TextView) rowView.findViewById(R.id.materialTitle)).setText(mDataList.get(position).getMat_title());
            ((TextView) rowView.findViewById(R.id.materialTitle_desc)).setText(mDataList.get(position).getMat_details());

            if (!mDataList.get(position).getMat_path().isEmpty())
                UtilsClass.setImageUsingPicasso(getActivity(), mDataList.get(position).getMat_path(), ((ImageView) rowView.findViewById(R.id.materialImageView)));

            return rowView;
        }

    }
}

