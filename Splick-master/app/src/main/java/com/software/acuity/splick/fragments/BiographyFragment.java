package com.software.acuity.splick.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.chip.ChipGroup;
import com.software.acuity.splick.R;
import com.software.acuity.splick.activities.SignUpActivity;
import com.software.acuity.splick.interfaces.IChangeViewPagerItem;
import com.software.acuity.splick.interfaces.IGetData;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class BiographyFragment extends Fragment {

    IChangeViewPagerItem iChangeViewPagerItem;
    IGetData iGetData;

    @BindView(R.id.btnBioNext)
    Button btnAboutNext;

    @BindView(R.id.etBioIntroduction)
    EditText etBiography;

    public BiographyFragment(Context context, SignUpActivity activity) {
        // Required empty public constructor

        iChangeViewPagerItem = activity;
        iGetData = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_biography, container, false);

        ButterKnife.bind(this, fragmentView);

        btnAboutNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if(!etBiography.getText().toString().trim().isEmpty()){
                        iGetData.getData(etBiography.getText().toString().trim(), "bio");
//                        iChangeViewPagerItem.changeViewPagerItem("complete");
                    }else
                        Toast.makeText(getActivity(), "Please fill introduction field", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        return fragmentView;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItemSkipTagFragment:
                iChangeViewPagerItem.changeViewPagerItem("complete");
                break;

            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_tags_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
