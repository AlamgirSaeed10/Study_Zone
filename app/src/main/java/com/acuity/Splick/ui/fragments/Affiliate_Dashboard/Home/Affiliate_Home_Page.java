package com.acuity.Splick.ui.fragments.Affiliate_Dashboard.Home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.acuity.Splick.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Affiliate_Home_Page extends Fragment {

    private AffiliateHomePageViewModel mViewModel;

    @Nullable
    @BindView(R.id.followed_user_image_desc)
    TextView followed_user_image_desc;

    public static Affiliate_Home_Page newInstance() {
        return new Affiliate_Home_Page();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.affiliate__home__page_fragment, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AffiliateHomePageViewModel.class);
        // TODO: Use the ViewModel
    }
}