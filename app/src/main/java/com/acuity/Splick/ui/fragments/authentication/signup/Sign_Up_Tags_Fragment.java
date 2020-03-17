package com.acuity.Splick.ui.fragments.authentication.signup;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.acuity.Splick.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Sign_Up_Tags_Fragment extends Fragment {
    private static final String TAG = "Sign_Up_Tags_Fragment";
    private SignUpInfoFragmentViewModel mViewModel;
    private static ArrayList<String> chipList=new ArrayList<String>();
    @BindView(R.id.sign_up_tags_next_btn)
    Button btnNext;
    @BindView(R.id.back_image)
    ImageView imBack;
    @BindView(R.id.skip_tag_tv)
    TextView tvSkip;
    @BindView(R.id.chipGroup)
    ChipGroup tags_list;

    public static Sign_Up_Tags_Fragment newInstance() {
        return new Sign_Up_Tags_Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign__up__tags__fragment, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel=new ViewModelProvider(getActivity()).get(SignUpInfoFragmentViewModel.class);
        mViewModel.getTag();
        mViewModel.getTagMutableLiveData().observe(getViewLifecycleOwner(),tag -> {
            if (tag.isSuccess()==true)
            {
                //todo: Success connection but Tag refactor  problem
                Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getActivity(), "Error occur", Toast.LENGTH_SHORT).show();
            }
        });
        //Todo:change after server return data
     chipList.add("Business");
     chipList.add("sports");
     chipList.add("clothing");
     chipList.add("etc");
     for (String s:chipList){
         addChip(s);
     }

        tags_list.setOnCheckedChangeListener((chipGroup, i) -> {
           Chip chip=chipGroup.findViewById(i);
           Toast.makeText(getActivity(),chip.getText(),Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onActivityCreated: ");
        });
        // TODO: Use the ViewModel
        btnNext.setOnClickListener(v -> {
            //TODO:
            Navigation.findNavController(v).navigate(R.id.action_sign_Up_Tags_Fragment_to_sign_Up_Bio_Fragment);

        });
        tvSkip.setOnClickListener(v -> {
            //TODO:
            Navigation.findNavController(v).navigate(R.id.action_sign_Up_Tags_Fragment_to_sign_Up_Bio_Fragment);
        });
        imBack.setOnClickListener(v -> {
            //TODO:
            Navigation.findNavController(v).navigate(R.id.action_sign_Up_Tags_Fragment_to_sign_Up_About_Fragment);
        });

    }
    private void addChip(String pItem) {
        Chip lChip = new Chip(getActivity());
        lChip.setText(pItem);
        lChip.setTextColor(getResources().getColor(R.color.white_text));
        lChip.setCheckable(true);
        lChip.setChipBackgroundColor(getResources().getColorStateList(R.color.black_text));
        lChip.setOnCheckedChangeListener((buttonView, isChecked) -> {
            Toast.makeText(getActivity(), "Success"+lChip.getText(), Toast.LENGTH_SHORT).show();
        });
        tags_list.addView(lChip, tags_list.getChildCount() - 1);
    }
}
