package com.software.acuity.splick.fragments.partnerships;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.software.acuity.splick.R;
import com.software.acuity.splick.adapters.StatisticsRecyclerAdapter;
import com.software.acuity.splick.models.StatisticsModel;
import com.software.acuity.splick.utils.AppController;
import com.software.acuity.splick.utils.Constants;
import com.software.acuity.splick.utils.SharedPreferenceClass;

import org.joda.time.LocalDateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class FilterCriteriaFragmentOneWeek extends Fragment {

    public static FilterCriteriaFragmentOneWeek filterCriteriaFragmentContext;
    /**
     * 1. Count clicks - view
     * 2. Count clicks - cart
     * 3. Count clicks - checkout
     * <p>
     * 1. Purchases - no type
     */
    LinearLayoutManager layoutManager;
    private List<StatisticsModel> mDataList = new ArrayList<>();

    int position = 0;

    StatisticsRecyclerAdapter statisticsRecyclerAdapter;
    SharedPreferenceClass sharedPreferenceClass;

    JSONObject userObject = null;

    private String requestTag = "clicks_data";

    @BindView(R.id.clickStatsFigures)
    TextView clickStatsFigures;

    @BindView(R.id.addedToCartStatsFigures)
    TextView addedToCartStatsFigures;

    @BindView(R.id.reachedCheckoutStatsFigures)
    TextView reachedCheckoutStatsFigures;

    @BindView(R.id.commissionStatsFigures)
    TextView commissionStatsFigures;

    @BindView(R.id.clicksGraphView)
    GraphView clicksGraphView;

    @BindView(R.id.cartGraphView)
    GraphView cartGraphView;

    @BindView(R.id.checkoutGraphView)
    GraphView checkoutGraphView;

    @BindView(R.id.commissionGraphView)
    GraphView commissionGraphView;

    @BindView(R.id.clickProgressBar)
    ProgressBar clickProgressBar;

    @BindView(R.id.cartProgressBar)
    ProgressBar cartProgressBar;

    @BindView(R.id.checkoutProgressBar)
    ProgressBar checkoutProgressBar;

    @BindView(R.id.purchasesProgressBar)
    ProgressBar purchasesProgressBar;


    @BindView(R.id.swipeToRefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    //Current date time
    LocalDateTime localDateTime = null;

    String toDate = "", fromDate = "";

    public FilterCriteriaFragmentOneWeek() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rowView = inflater.inflate(R.layout.fragment_statistics, container, false);
        ButterKnife.bind(this, rowView);

        sharedPreferenceClass = SharedPreferenceClass.getInstance(AppController.getContext());

       // localDateTime = LocalDateTime.now();
        LocalDateTime fromLocalDate = LocalDateTime.now().minusWeeks(1);
        LocalDateTime toLocalDate = LocalDateTime.now().minusDays(0);

        fromDate = Constants.dateInSimpleFormatReveresed(fromLocalDate.toDate()).split("-")[0].trim().replace("/", "-");
        toDate = Constants.dateInSimpleFormatReveresed(toLocalDate.toDate()).split("-")[0].trim().replace("/", "-");

        try {
            userObject = new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        swipeRefreshLayout.setOnRefreshListener(() -> {
            loadDataViewData(fromDate, toDate, "view");
            loadDataCartData(fromDate, toDate, "cart");
            loadDataCheckoutData(fromDate, toDate, "checkout");
            loadDataPurchasesData(fromDate, toDate, "purchases");

        });

        loadDataViewData(fromDate, toDate, "view");
        loadDataCartData(fromDate, toDate, "cart");
        loadDataCheckoutData(fromDate, toDate, "checkout");
        loadDataPurchasesData(fromDate, toDate, "purchases");

        return rowView;
    }
    public void loadDataViewData(String fromDate, String toDate, String type) {
        String userId = "";
        String validEndpoint = "";

        try {
            userId = userObject.getString("id");
        } catch (JSONException e) {

        }
        validEndpoint = Constants.API_BUSINESS_CLICK_DEALS;
        StringRequest jsonObjReq = new StringRequest(Request.Method.POST, Constants.BASE_URL + validEndpoint + userId + "",
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int clickFiguresVal = 0;

                        if (jsonObject.getBoolean("success")) {
                            JSONArray dataObject = jsonObject.getJSONArray("data");

                            clicksGraphView.removeAllSeries();
                            DataPoint[] dataPoints = new DataPoint[dataObject.length()];

                            for (int i = 0; i < dataObject.length(); i++) {

                                Object secondIndex = dataObject.getJSONArray(i).getInt(1);
                                clickFiguresVal += Integer.parseInt(secondIndex.toString());

                                dataPoints[i] = new DataPoint(i, Integer.parseInt(secondIndex.toString()));
                            }


                            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
                            clicksGraphView.addSeries(series);


                            clickProgressBar.setVisibility(View.INVISIBLE);
                            clickStatsFigures.setText(clickFiguresVal + "");

                        } else {
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    swipeRefreshLayout.setRefreshing(true);
                }, error -> VolleyLog.d("update_profile", "Error: " + error.getMessage())) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("from", fromDate + "");
                params.put("to", toDate + "");
                params.put("type", type + "");

                return params;
            }
        };

        // Adding request to request queue
        jsonObjReq.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 10000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 0; //retry turn off
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq, "sign_in");
    }

    public void loadDataCartData(String fromDate, String toDate, String type) {
        String userId = "";
        String validEndpoint = "";

        try {

            userId = userObject.getString("id");
        } catch (JSONException e) {

        }
        validEndpoint = Constants.API_BUSINESS_CLICK_DEALS;
        StringRequest jsonObjReq = new StringRequest(Request.Method.POST, Constants.BASE_URL + validEndpoint + userId + "",
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int addedToCartStatsFiguresVal = 0;

                        if (jsonObject.getBoolean("success")) {
                            JSONArray dataObject = jsonObject.getJSONArray("data");

                            cartGraphView.removeAllSeries();
                            DataPoint[] dataPoints = new DataPoint[dataObject.length()];

                            for (int i = 0; i < dataObject.length(); i++) {

                                Object secondIndex = dataObject.getJSONArray(i).getInt(1);
                                addedToCartStatsFiguresVal += Integer.parseInt(secondIndex.toString());


                                dataPoints[i] = new DataPoint(i, Integer.parseInt(secondIndex.toString()));

                            }


                            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
                            cartGraphView.addSeries(series);


                            cartProgressBar.setVisibility(View.INVISIBLE);
                            addedToCartStatsFigures.setText("$" + addedToCartStatsFiguresVal + "");

                        } else {
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    swipeRefreshLayout.setRefreshing(false);
                }, error -> {
                    VolleyLog.d("update_profile", "Error: " + error.getMessage());
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("from", fromDate + "");
                params.put("to", toDate + "");
                params.put("type", type + "");
//                }

                return params;
            }
        };

        // Adding request to request queue
        jsonObjReq.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 10000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 0; //retry turn off
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq, "sign_in");
    }

    public void loadDataCheckoutData(String fromDate, String toDate, String type) {
        String userId = "";
        String validEndpoint = "";

        try {

            userId = userObject.getString("id");
        } catch (JSONException e) {

        }
        validEndpoint = Constants.API_BUSINESS_CLICK_DEALS;
        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                Constants.BASE_URL + validEndpoint + userId + "",
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int reachedCheckoutStatsFiguresVal = 0;

                        if (jsonObject.getBoolean("success")) {
                            //Save data to shared preference
                            JSONArray dataObject = jsonObject.getJSONArray("data");

                            DataPoint[] dataPoints = new DataPoint[dataObject.length()];

                            for (int i = 0; i < dataObject.length(); i++) {
                                checkoutGraphView.removeAllSeries();
                                Object secondIndex = dataObject.getJSONArray(i).getInt(1);

                                reachedCheckoutStatsFiguresVal += Integer.parseInt(secondIndex.toString());

                                dataPoints[i] = new DataPoint(i, Integer.parseInt(secondIndex.toString()));

                            }


                            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
                            checkoutGraphView.addSeries(series);


                            checkoutProgressBar.setVisibility(View.INVISIBLE);
                            reachedCheckoutStatsFigures.setText(reachedCheckoutStatsFiguresVal + "");
                        } else {
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    swipeRefreshLayout.setRefreshing(false);
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("update_profile", "Error: " + error.getMessage());

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("from", fromDate + "");
                params.put("to", toDate + "");
                params.put("type", type + "");

                return params;
            }
        };

        // Adding request to request queue
        jsonObjReq.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 10000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 0; //retry turn off
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq, "sign_in");
    }

    public void loadDataPurchasesData(String fromDate, String toDate, String type) {
        String userId = "";
        String validEndpoint = "";

        try {
            userId = userObject.getString("id");
        } catch (JSONException e) {

        }

        validEndpoint = Constants.API_BUSINESS_ORDER_DEALS;

        StringRequest jsonObjReq = new StringRequest(Request.Method.POST,
                Constants.BASE_URL + validEndpoint + userId + "",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            int commissionStatsFiguresVal = 0;
                            if (jsonObject.getBoolean("success")) {
                                //Save data to shared preference
                                JSONArray dataObject = jsonObject.getJSONArray("data");
                                commissionGraphView.removeAllSeries();
                                DataPoint[] dataPoints = new DataPoint[dataObject.length()];

                                for (int i = 0; i < dataObject.length(); i++) {

                                    Object secondIndex = dataObject.getJSONArray(i).getInt(1);
                                    commissionStatsFiguresVal += Integer.parseInt(secondIndex.toString());
                                    dataPoints[i] = new DataPoint(i, Integer.parseInt(secondIndex.toString()));
                                }

                                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
                                commissionGraphView.addSeries(series);
                                purchasesProgressBar.setVisibility(View.INVISIBLE);
                                commissionStatsFigures.setText(commissionStatsFiguresVal+"");
                            } else {
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("update_profile", "Error: " + error.getMessage());

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("from", fromDate + "");
                params.put("to", toDate + "");

                return params;
            }
        };

        // Adding request to request queue
        jsonObjReq.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 10000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 0; //retry turn off
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq, "sign_in");
    }
}

