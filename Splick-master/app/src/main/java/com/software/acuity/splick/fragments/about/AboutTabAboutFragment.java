package com.software.acuity.splick.fragments.about;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.software.acuity.splick.R;
import com.software.acuity.splick.utils.Constants;
import com.software.acuity.splick.utils.SharedPreferenceClass;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutTabAboutFragment extends Fragment {

    @BindView(R.id.textView30)
    TextView textView;

    SharedPreferenceClass sharedPreferenceClass;

    public AboutTabAboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rowView = inflater.inflate(R.layout.fragment_about_tab_about, container, false);

        ButterKnife.bind(this, rowView);

        sharedPreferenceClass = SharedPreferenceClass.getInstance(getActivity());

        try {
            JSONObject jsonObject =
                    new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT));

            if(jsonObject.getString("user_bio").trim().isEmpty()){
                textView.setText(jsonObject.getString("full_name") + "");
            }else
                textView.setText(jsonObject.getString("user_bio") + "");

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return rowView;
    }

}
