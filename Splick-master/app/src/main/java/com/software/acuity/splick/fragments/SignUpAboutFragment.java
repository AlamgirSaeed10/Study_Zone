package com.software.acuity.splick.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.software.acuity.splick.R;
import com.software.acuity.splick.activities.SignUpActivity;
import com.software.acuity.splick.interfaces.IChangeViewPagerItem;
import com.software.acuity.splick.interfaces.IGetData;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpAboutFragment extends Fragment {

    IChangeViewPagerItem iChangeViewPagerItem;
    IGetData iGetData;

    @BindView(R.id.btnAboutNext)
    Button btnAboutNext;

    @BindView(R.id.etAboutName)
    EditText etAboutName;

    @BindView(R.id.etAboutUsername)
    EditText etAboutUserName;

    @BindView(R.id.etAboutLocation)
    EditText etAboutLocation;

    public SignUpAboutFragment(Context context, SignUpActivity activity) {
        // Required empty public constructor

        iChangeViewPagerItem = activity;
        iGetData = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_about, container, false);

        ButterKnife.bind(this, fragmentView);

        btnAboutNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    iGetData.getData(etAboutName.getText().toString() + "," + etAboutUserName.getText().toString() + "," + etAboutLocation.getText().toString(), "about");
                    iChangeViewPagerItem.changeViewPagerItem("tags");
                }
            }
        });

        return fragmentView;
    }

    public boolean validate() {
        if (etAboutName.getText().toString().trim().isEmpty()
                || etAboutUserName.getText().toString().trim().isEmpty()
                || etAboutLocation.getText().toString().trim().isEmpty()) {
            Toast.makeText(getActivity(), "Fill All fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

}
