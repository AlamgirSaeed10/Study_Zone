package com.acuity.Splick.ui.fragments.Affiliate_Dashboard.Partnership.Tabsdata;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.acuity.Splick.R;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class Affiliate_Partnership_Deals extends Fragment {
    @BindView(R.id.deal_user_image)
    CircleImageView deal_user_image;
    @BindView(R.id.deal_user_name)
    TextView deal_user_name;
    @BindView(R.id.deal_copy_link)
    TextView deal_copy_link;
    @BindView(R.id.deal_clicks_value)
    TextView deal_clicks_value;
    @BindView(R.id.deal_sale_value)
    TextView deal_sale_value;
    @BindView(R.id.deal_commission_value)
    TextView deal_commission_value;
    @BindView(R.id.deals_recycler_view)
    RecyclerView deals_recycler_view;


    private AffiliatePartnershipDealsViewModel mViewModel;

    public static Affiliate_Partnership_Deals newInstance() {
        return new Affiliate_Partnership_Deals();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.affiliate__partnership__deals_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AffiliatePartnershipDealsViewModel.class);
        // TODO: Use the ViewModel
    }

}
