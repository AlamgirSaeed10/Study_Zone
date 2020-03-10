package com.software.acuity.splick.fragments.payment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.software.acuity.splick.R;
import com.software.acuity.splick.interfaces.IVolleyResponseWithRequestId;
import com.software.acuity.splick.models.payout.Payout;
import com.software.acuity.splick.utils.AppController;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class InvoiceFragment extends Fragment implements IVolleyResponseWithRequestId {

    @BindView(R.id.previousInvoicesListView)
    ListView previousInvoiceListView;

    @BindView(R.id.upcomingInvoiceAmount)
    TextView upcomingInvoiceAmount;

    @BindView(R.id.nodataFoundMessage)
    LinearLayout noDataFoundMessage;

    SharedPreferenceClass sharedPreferenceClass;
    private VolleyRequestClass volleyRequestClass;

    List<Payout> mDataList = new ArrayList<>();
    private String requestTag = "business_payout_request";

    public InvoiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rowView = inflater.inflate(R.layout.fragment_invoice, container, false);

        sharedPreferenceClass = SharedPreferenceClass.getInstance(AppController.getContext());

        ButterKnife.bind(this, rowView);

        requestData();
        return rowView;
    }

    public void requestData() {

        HashMap<String, String> params = new HashMap<>();
        try {
            JSONObject userObject = new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT));

            params.put("business_id", userObject.getString("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        volleyRequestClass = new VolleyRequestClass(getActivity(), InvoiceFragment.this,
                params, "Payout Commission", "Please wait while we getting the commission amount!",
                requestTag,
                Request.Method.POST, 1000);

        volleyRequestClass.volleyServiceCallWithRequestId(Constants.BASE_URL + Constants.API_BUSINESS_PAYOUT_VIEW);
    }

    public void previousInvoices() {

        HashMap<String, String> params = new HashMap<>();
        try {
            JSONObject userObject = new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT));

            params.put("business_id", userObject.getString("id"));
//            params.put("business_id", "3");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        volleyRequestClass = new VolleyRequestClass(getActivity(), InvoiceFragment.this,
                params, "Payout Commission", "Please wait while we getting the commission amount!",
                requestTag,
                Request.Method.POST, 1001);

        volleyRequestClass.volleyServiceCallWithRequestId(Constants.BASE_URL + Constants.API_BUSINESS_BUS_PAYOUT_VIEW);
    }

    @Override
    public void networkResponse(String response, int requestId) {

        try {
            JSONObject jsonObject = new JSONObject(response);
            switch (requestId) {
                case 1000:


                    upcomingInvoiceAmount.setText("$" + jsonObject.getDouble("data") + "");
                    previousInvoices();

                    break;

                case 1001:
                    if (jsonObject.getBoolean("success")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");



                        for (int i = 0; i < jsonArray.length(); i++) {
                            Payout payout = new Payout();

                            payout.setId(jsonArray.getJSONObject(i).getString("id"));
                            payout.setAffiliate_id(jsonArray.getJSONObject(i).getString("affiliate_id"));
                            payout.setBusiness_id(jsonArray.getJSONObject(i).getString("business_id"));
                            payout.setRequest_amount(jsonArray.getJSONObject(i).getString("request_amount"));
                            payout.setStatus(jsonArray.getJSONObject(i).getString("status"));
                            payout.setAdd_time(jsonArray.getJSONObject(i).getString("add_time"));
                            payout.setProceed_by(jsonArray.getJSONObject(i).getString("proceed_by"));
                            payout.setPsp_reference(jsonArray.getJSONObject(i).getString("psp_reference"));
                            payout.setProceed_time(jsonArray.getJSONObject(i).getString("proceed_time"));
                            payout.setAffiliate_name(jsonArray.getJSONObject(i).getString("affiliate_name"));
                            payout.setBusiness_name(jsonArray.getJSONObject(i).getString("business_name"));

                            mDataList.add(payout);
                        }
                    }

                    if (mDataList.size() > 0) {
                        noDataFoundMessage.setVisibility(View.INVISIBLE);
                    }

                    previousInvoiceListView.setAdapter(new PreviousInvoicesCustomAdapter());
                    break;
            }

        } catch (JSONException e) {

        }

    }

    public class PreviousInvoicesCustomAdapter extends BaseAdapter {

        LayoutInflater layoutInflater;

        public PreviousInvoicesCustomAdapter() {
            layoutInflater = LayoutInflater.from(getActivity());
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
            View rowView = layoutInflater.inflate(R.layout.row_previous_invoice, null);

            ((TextView) rowView.findViewById(R.id.invoiceDate)).setText(mDataList.get(position).getAdd_time().split(" ")[0]);

            return rowView;
        }
    }
}
