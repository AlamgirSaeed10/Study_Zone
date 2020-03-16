package com.acuity.Splick.ui.fragments.authentication.signup;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.acuity.Splick.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Search_Tags_Fragment extends Fragment {

    private SearchTagsViewModel mViewModel;

    @BindView(R.id.tags_list_recycler_view)
    RecyclerView tags_list_recycler_view;
    @BindView(R.id.cancel_tags_tv)
    TextView cancel_tags_tv;
    @BindView(R.id.skip_tag_tv)
    TextView skip_tag_tv;
    @BindView(R.id.search_tags_edt)
    EditText search_tags_edt;


    public static Search_Tags_Fragment newInstance() {
        return new Search_Tags_Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search__tags__fragment, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SearchTagsViewModel.class);
        // TODO: Use the ViewModel
    }

}
