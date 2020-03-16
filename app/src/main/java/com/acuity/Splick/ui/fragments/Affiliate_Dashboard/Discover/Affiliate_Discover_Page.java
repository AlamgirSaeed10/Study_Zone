package com.acuity.Splick.ui.fragments.Affiliate_Dashboard.Discover;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.*;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.*;
import com.acuity.Splick.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Affiliate_Discover_Page extends Fragment {

    private AffiliateDiscoverPageViewModel mViewModel;
    StaggeredGridLayoutManager layoutManager;

    @BindView(R.id.discoverd_business)
    RecyclerView recyclerView;

    public static Affiliate_Discover_Page newInstance() {
        return new Affiliate_Discover_Page();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.affiliate__discover__page_fragment, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AffiliateDiscoverPageViewModel.class);
        // TODO: Use the ViewModel
    }
    public void initRecyclerView() {
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(layoutManager);

    }
}
