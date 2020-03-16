package com.acuity.Splick.ui.fragments.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.acuity.Splick.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Messages_Fragment extends Fragment {

    @BindView(R.id.back_image)
    ImageView back_image;
    @BindView(R.id.search_message)
    EditText search_message;
    @BindView(R.id.message_list_recycler_view)
    RecyclerView message_list_recycler_view;

    private MessagesViewModel mViewModel;

    public static Messages_Fragment newInstance() {
        return new Messages_Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.messages__fragment, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MessagesViewModel.class);
        // TODO: Use the ViewModel
    }

}
