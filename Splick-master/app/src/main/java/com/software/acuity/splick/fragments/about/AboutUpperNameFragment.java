package com.software.acuity.splick.fragments.about;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.software.acuity.splick.R;
import com.software.acuity.splick.models.business_deals.Business;
import com.software.acuity.splick.utils.Constants;
import com.software.acuity.splick.utils.UtilsClass;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutUpperNameFragment extends Fragment {

    @BindView(R.id.profilePicture)
    CircleImageView profilePicture;

    @BindView(R.id.profileName)
    TextView profileName;

    Business businessItem;

    public AboutUpperNameFragment() {
        // Required empty public constructor
    }

    public AboutUpperNameFragment(Business businessItem) {
        // Required empty public constructor
        this.businessItem = businessItem;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rowView = inflater.inflate(R.layout.fragment_about_upper_name, container, false);

        ButterKnife.bind(this, rowView);

        try {
            if(businessItem == null){
                JSONObject userObject = new JSONObject(Constants.jsonObject);
                profileName.setText(userObject.getString("full_name"));
                if (!userObject.getString("thumbnail_url").trim().isEmpty()) {
                    UtilsClass.setImageUsingPicasso(getActivity(), userObject.getString("thumbnail_url").trim(), profilePicture);
                }
            }else{
                profileName.setText(businessItem.getFull_name());
                if (!businessItem.getProfile_path().trim().isEmpty()) {
                    UtilsClass.setImageUsingPicasso(getActivity(), businessItem.getProfile_path(), profilePicture);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rowView;
    }

}
