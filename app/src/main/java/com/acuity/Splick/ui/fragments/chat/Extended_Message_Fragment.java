package com.acuity.Splick.ui.fragments.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.acuity.Splick.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Extended_Message_Fragment extends Fragment {


    @BindView(R.id.profile_image)
    ImageView profile_image;
    @BindView(R.id.user_name_display)
    TextView user_name_display;
    @BindView(R.id.active_status)
    TextView active_status;
    @BindView(R.id.button_chatbox_attachment)
    Button button_chatbox_attachment;
    @BindView(R.id.button_chatbox_send)
    TextView button_chatbox_send;
    @BindView(R.id.reply_message_edt)
    EditText reply_message_edt;
    @BindView(R.id.back_image)
    ImageView back_image;

    @BindView(R.id.message_recycler_view)
    RecyclerView message_recycler_view;

    private ExtendedMessageViewModel mViewModel;

    public static Extended_Message_Fragment newInstance() {
        return new Extended_Message_Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.extended__message__fragment, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ExtendedMessageViewModel.class);
        // TODO: Use the ViewModel
    }

}
