package com.software.acuity.splick.fragments.earnings;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.software.acuity.splick.R;
import com.software.acuity.splick.interfaces.IVolleyResponse;
import com.software.acuity.splick.interfaces.IVolleyResponseWithRequestId;
import com.software.acuity.splick.models.payout.Payout;
import com.software.acuity.splick.models.payout.PayoutBusinessModel;
import com.software.acuity.splick.utils.AppController;
import com.software.acuity.splick.utils.Constants;
import com.software.acuity.splick.utils.SharedPreferenceClass;
import com.software.acuity.splick.utils.VolleyRequestClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PayoutFragment extends Fragment implements IVolleyResponseWithRequestId, IVolleyResponse {


    private VolleyRequestClass volleyRequestClass;
    private String requestTag = "payout_fragment";
    public Payout payout = new Payout();

    @BindView(R.id.edit_earning_amount)
    TextView commissionAmountTv;

    @BindView(R.id.button3)
    Button payoutBtn;

    private SharedPreferenceClass sharedPreferenceClass;
    String thresholdString = "";
    private String commissionAmountStr = "0";
    private static final int COMMISSION_AMOUNT_REQUEST = 1000;
    private static final int THRESHOLD_AMOUNT_REQUEST = 1001;
    private static final int COMMISSION_AMOUNT_BY_BUSINESSES = 1002;

    List<PayoutBusinessModel> businessesList = new ArrayList<>();

    public PayoutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rowView = inflater.inflate(R.layout.fragment_payout, container, false);

        ButterKnife.bind(this, rowView);

        sharedPreferenceClass = SharedPreferenceClass.getInstance(AppController.getContext());
        requestData();
        payoutCommissionThreshold();

        payoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!commissionAmountStr.equalsIgnoreCase("")) {
                    if (Integer.parseInt(commissionAmountStr) != 0) {
                        showPayoutAmountDialog();
                    } else {
                        Toast.makeText(getActivity(), "No amount for withdraw", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return rowView;
    }

    public void requestData() {

        HashMap<String, String> params = new HashMap<>();
        try {
            JSONObject userObject = new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT));

            params.put("user_id", userObject.getString("id"));
//            params.put("user_id", "4");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        volleyRequestClass = new VolleyRequestClass(getActivity(), PayoutFragment.this,
                params, "Payout Commission", "Please wait while we getting the commission amount!",
                requestTag,
                Request.Method.POST, COMMISSION_AMOUNT_REQUEST);

        volleyRequestClass.volleyServiceCallWithRequestId(Constants.BASE_URL + Constants.API_AFFILIATE_PAYOUT_VIEW);
    }

    public void requestBusinesses() {

        HashMap<String, String> params = new HashMap<>();
        try {
            JSONObject userObject = new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT));

            params.put("user_id", userObject.getString("id"));
//            params.put("user_id", "4");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        volleyRequestClass = new VolleyRequestClass(getActivity(), PayoutFragment.this,
                params, "Businesses", "Please wait while we getting the businesses for commission amount!",
                requestTag,
                Request.Method.POST, COMMISSION_AMOUNT_BY_BUSINESSES);

        volleyRequestClass.volleyServiceCallWithRequestId(Constants.BASE_URL + Constants.API_GET_COMMISSION_BY_BUSINESSES);
    }


    public void payoutCommissionAmount(String businessId, String commissionAmount) {

        HashMap<String, String> params = new HashMap<>();

        try {
            JSONObject userObject = new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT));


            params.put("affiliate_id", userObject.getString("id") + "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        params.put("business_id", businessId + "");
        params.put("withdrawalAmount", commissionAmount + "");

        volleyRequestClass = new VolleyRequestClass(getActivity(), PayoutFragment.this,
                params, "Payout Request", "Please wait while are requesting for payout!",
                requestTag,
                Request.Method.POST);

        volleyRequestClass.volleyServiceCall(Constants.BASE_URL + Constants.API_AFFILIATE_PAYOUT_REQUEST);
    }

    public void payoutCommissionThreshold() {

        HashMap<String, String> params = new HashMap<>();

        volleyRequestClass = new VolleyRequestClass(getActivity(), PayoutFragment.this,
                params, "threshold", "Getting minimum threshold ",
                requestTag,
                Request.Method.POST, THRESHOLD_AMOUNT_REQUEST);

        volleyRequestClass.volleyServiceCallWithRequestId(Constants.BASE_URL + Constants.API_AFFILIATE_PAYOUT_THRESHOLD);
    }

    EditText payoutAmountEt;
    ListView listView;
    LinearLayout noDataFoundLayout;

    public void initAdapter() {
        PayoutListViewCustomAdapter payoutListViewCustomAdapter = new PayoutListViewCustomAdapter();

        listView.setAdapter(payoutListViewCustomAdapter);
    }

    Dialog dialog = null;

    public void showPayoutAmountDialog() {
        dialog = new Dialog(getActivity(),
                android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.payout_withdraw_amount);

        listView = dialog.findViewById(R.id.withdrawListView);
        noDataFoundLayout = dialog.findViewById(R.id.nodataFoundMessage);
        dialog.findViewById(R.id.closePayoutDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        //Listview will get set in this request
        requestBusinesses();

        MaterialButton payoutBtn = dialog.findViewById(R.id.withdrawBtn);
        payoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int totalAmountToWithdraw = 0;
                int totalDueCommission = 0;

                for (int i = 0; i < businessesList.size(); i++) {
                    totalDueCommission += Integer.parseInt(businessesList.get(i).getCommission().trim().toString());
                }

                for (int i = 0; i < businessesList.size(); i++) {
                    totalAmountToWithdraw += Integer.parseInt(businessesList.get(i).getCommissionForWithdraw().trim().toString());
                }

                if (totalAmountToWithdraw < Integer.parseInt(thresholdString)
                        || totalAmountToWithdraw > totalDueCommission) {
//                    payoutAmountEt.setError("Invalid amount");
                    Toast.makeText(getActivity(), "Cannot process. Invalid amount", Toast.LENGTH_LONG).show();
                } else {
                    for (int i = 0; i < businessesList.size(); i++) {
                        if (businessesList.get(i).isSelected()) {
                            payoutCommissionAmount(businessesList.get(i).getBusiness_id(), businessesList.get(i).getCommissionForWithdraw());
                        }
                    }
                }
            }
        });

        dialog.show();
    }


    @Override
    public void networkResponse(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.has("msg")) {
                new MaterialAlertDialogBuilder(getActivity(),
                        R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                        .setTitle("Payout Request")
                        .setMessage(jsonObject.getString("msg"))
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                requestData();

                            }
                        })
                        .show();
            } else if (jsonObject.getBoolean("success")) {
                if (jsonObject.has("msg")) {
                    new MaterialAlertDialogBuilder(getActivity(),
                            R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                            .setTitle("Payout Request")
                            .setMessage(jsonObject.getString("msg"))
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    requestData();
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }
            } else if (!jsonObject.getBoolean("success")) {
                if (jsonObject.has("msg")) {
                    new MaterialAlertDialogBuilder(getActivity(),
                            R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                            .setTitle("Payout Request")
                            .setMessage(jsonObject.getString("msg"))
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    requestData();
                                    dialog.dismiss();
                                }
                            })
                            .show();
                }
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void networkResponse(String response, int requestId) {
        switch (requestId) {
            case THRESHOLD_AMOUNT_REQUEST:
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    thresholdString = jsonObject.getJSONObject("data").getJSONObject("payout").getString("threshold");
                } catch (JSONException e) {

                }
                break;

            case COMMISSION_AMOUNT_REQUEST:
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    commissionAmountStr = jsonObject.getInt("data") + "";
                    commissionAmountTv.setText("$" + commissionAmountStr);
                } catch (JSONException e) {

                }
                break;

            case COMMISSION_AMOUNT_BY_BUSINESSES:
                try {
                    businessesList.clear();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonPayoutByBusinesses = jsonObject.getJSONObject("data").getJSONObject(
                            "businesses");

                    for (Iterator<String> it = jsonPayoutByBusinesses.keys(); it.hasNext(); ) {
                        String key = it.next();

                        PayoutBusinessModel payoutBusinessModel = new PayoutBusinessModel();
                        payoutBusinessModel.setBusiness_id(jsonPayoutByBusinesses.getJSONObject(key).getString("business_id"));
                        payoutBusinessModel.setBus_name(jsonPayoutByBusinesses.getJSONObject(key).getString("bus_name"));
                        payoutBusinessModel.setCommission(jsonPayoutByBusinesses.getJSONObject(key).getString("commission"));

                        businessesList.add(payoutBusinessModel);
                    }

                    if (businessesList.size() > 0) {
                        noDataFoundLayout.setVisibility(View.INVISIBLE);
                    }
                    initAdapter();
                } catch (JSONException e) {

                }
                break;
        }
    }

    class PayoutListViewCustomAdapter extends BaseAdapter {

        public PayoutListViewCustomAdapter() {
        }

        @Override
        public int getCount() {
            return businessesList.size();
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
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.payout_row, null);

            MaterialCheckBox checkBox = view.findViewById(R.id.checkbox);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    businessesList.get(position).setSelected(isChecked);
                }
            });

            TextView businessName = view.findViewById(R.id.payoutBusinessName);
            businessName.setText(businessesList.get(position).getBus_name());

            EditText commissionAmountDialog = view.findViewById(R.id.payoutCommissionAmount);
            commissionAmountDialog.setText(businessesList.get(position).getCommission());

            EditText commissionAmountForRequest = view.findViewById(R.id.payoutCommissionAmountForRequest);
            commissionAmountForRequest.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    if (!s.toString().isEmpty()) {
                        int commissionAmount = Integer.parseInt(commissionAmountDialog.getText().toString());
                        int commissionAmountForWithdraw = Integer.parseInt(commissionAmountForRequest.getText().toString());

                        if (commissionAmount < commissionAmountForWithdraw) {
                            Toast.makeText(getActivity(), "Invalid amount", Toast.LENGTH_SHORT).show();
                            checkBox.setChecked(false);
                        } else {
                            checkBox.setChecked(true);
                            businessesList.get(position).setCommissionForWithdraw(commissionAmountForRequest.getText().toString().trim());
                        }
                    } else {
                        commissionAmountForRequest.setText("0");
                    }
                }
            });
            return view;
        }
    }
}