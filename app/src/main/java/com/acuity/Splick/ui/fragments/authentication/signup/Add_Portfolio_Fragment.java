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

public class Add_Portfolio_Fragment extends Fragment {


    private AddPortfolioViewModel mViewModel;
    @BindView(R.id.portfolio_upload_btn)
    Button btnUpload;
    @BindView(R.id.skip_bio_tv)
    TextView tvSkip;
    @BindView(R.id.back_image)
    ImageView imgBack;

    public static Add_Portfolio_Fragment newInstance() {
        return new Add_Portfolio_Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add__portfolio__fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AddPortfolioViewModel.class);
        // TODO: Use the ViewModel
        btnUpload.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_add_Portfolio_Fragment_to_profile_Completed_Fragment);
        });
        tvSkip.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_add_Portfolio_Fragment_to_profile_Completed_Fragment);
        });
        imgBack.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_add_Portfolio_Fragment_to_social_Reach_Fragment);
        });
    }

}
