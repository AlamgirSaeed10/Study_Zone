package com.acuity.Splick.ui.fragments.authentication.signup;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.acuity.Splick.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class News_Feed_Fragment extends Fragment {

    private NewsFeedViewModel mViewModel;

    @BindView(R.id.back_image)
    ImageView back_image;
    @BindView(R.id.skip_nf_tv)
    TextView skip_nf_tv;
    @BindView(R.id.news_feed_upload_btn)
    Button news_feed_upload_btn;
    @BindView(R.id.news_feed_image)
    ImageView news_feed_image;

    public static News_Feed_Fragment newInstance() {
        return new News_Feed_Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.news__feed__fragment, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(NewsFeedViewModel.class);
        // TODO: Use the ViewModel
    }

}
