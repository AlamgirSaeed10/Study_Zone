package com.software.acuity.splick.fragments.partnerships;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.software.acuity.splick.R;
import com.software.acuity.splick.activities.DashBoardActivity;
import com.software.acuity.splick.adapters.StatisticsRecyclerAdapter;
import com.software.acuity.splick.behaviours.RecyclerTouchListener;
import com.software.acuity.splick.interfaces.IRecyclerClickListener;
import com.software.acuity.splick.interfaces.IVolleyResponse;
import com.software.acuity.splick.models.StatisticsModel;
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

public class StatisticsFragment extends Fragment implements IVolleyResponse {
    private VolleyRequestClass volleyRequestClass;
    private String requestTag = "statistics";

  // @BindView(R.id.statisticsRecycler)
  // RecyclerView recyclerView;

    LinearLayoutManager layoutManager;
    private List<StatisticsModel> mDataList = new ArrayList<>();

    StatisticsRecyclerAdapter statisticsRecyclerAdapter;

    SharedPreferenceClass sharedPreferenceClass;

    JSONObject userObject = null;

    public StatisticsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rowView = inflater.inflate(R.layout.fragment_statistics, container, false);

        ButterKnife.bind(this, rowView);

        sharedPreferenceClass = SharedPreferenceClass.getInstance(getApplicationContext());


        return rowView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

//        if (isVisibleToUser) {
//            requestData();
//        }
    }

//    public void initRecyclerView() {
//        recyclerView.setHasFixedSize(true);
//
//        // use a linear layout manager
//        layoutManager = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(layoutManager);
//
//        statisticsRecyclerAdapter = new StatisticsRecyclerAdapter(getActivity(),
//                mDataList);
//        recyclerView.setAdapter(statisticsRecyclerAdapter);
//
//        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),
//                recyclerView, new IRecyclerClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//            }
//        }));
//    }

    public void requestData() {
        HashMap<String, String> params = new HashMap<>();

        //TODO: Add Params
       /* params.put("from", "2020-01-07");
        params.put("to", "2019-08-22");
        params.put("type", "view");
        */

        volleyRequestClass = new VolleyRequestClass(DashBoardActivity.dashboardActvityContext, StatisticsFragment.this,
                params, "Deals Details", "Loading details!", requestTag, Request.Method.POST);


        try {
            userObject = new JSONObject(Constants.jsonObject);
            volleyRequestClass.volleyServiceCall(Constants.BASE_URL + Constants.API_BUSINESS_CLICK_DEALS + userObject.get("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void networkResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.getBoolean("success")) {
                JSONArray jsonDataArray = jsonObject.getJSONArray("data");

                for (int i = 0; i < jsonDataArray.length(); i++) {
                    JSONArray jsonObj = jsonDataArray.getJSONArray(i);

                    mDataList.add(new StatisticsModel(jsonObj.get(0).toString(), jsonObj.get(1).toString()));
                }
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
