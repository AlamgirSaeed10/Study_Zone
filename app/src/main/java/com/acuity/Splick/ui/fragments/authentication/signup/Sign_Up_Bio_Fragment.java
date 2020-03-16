package com.acuity.Splick.ui.fragments.authentication.signup;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.acuity.Splick.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Sign_Up_Bio_Fragment extends Fragment {

    private SignUpBioViewModel mViewModel;
    @BindView(R.id.sign_up_bio_next_btn)
    Button btnNext;
    @BindView(R.id.back_image)
    ImageView imgBack;
    @BindView(R.id.skip_bio_tv)
    TextView tvSkip;

    public static Sign_Up_Bio_Fragment newInstance() {
        return new Sign_Up_Bio_Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign__up__bio__fragment, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SignUpBioViewModel.class);
        // TODO: Use the ViewModel
        btnNext.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_sign_Up_Bio_Fragment_to_social_Reach_Fragment);
        });
        tvSkip.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_sign_Up_Bio_Fragment_to_social_Reach_Fragment);
        });
        imgBack.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_social_Reach_Fragment_to_sign_Up_Bio_Fragment);
        });
    }

}
