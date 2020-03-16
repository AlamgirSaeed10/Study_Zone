package com.acuity.Splick.ui.fragments.authentication.signup;

import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.acuity.Splick.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Social_Reach_Fragment extends Fragment {
    @BindView(R.id.sign_up_bio_next_btn)
    Button btnNext;
    @BindView(R.id.skip_bio_tv)
    TextView tvSkip;
    @BindView(R.id.back_image)
    ImageView imBack;

    @BindView(R.id.connect_to_insta)
    TextView connect_to_insta;
    @BindView(R.id.connect_to_fb)
    TextView connect_to_fb;
    @BindView(R.id.connect_to_twitter)
    TextView connect_to_twitter;
    @BindView(R.id.connect_to_youtube)
    TextView connect_to_youtube;
    @BindView(R.id.connect_to_snapchat)
    TextView connect_to_snapchat;

    String insta_link;

    private SocialReachViewModel mViewModel;

    public static Social_Reach_Fragment newInstance() {
        return new Social_Reach_Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.social__reach__fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SocialReachViewModel.class);
        // TODO: Use the ViewModel
        btnNext.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_social_Reach_Fragment_to_add_Portfolio_Fragment);
        });
        tvSkip.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_social_Reach_Fragment_to_add_Portfolio_Fragment);
        });
        imBack.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_social_Reach_Fragment_to_sign_Up_Bio_Fragment);
        });
    }

    public void getTextVal() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("PASSWORD");
        alertDialog.setMessage("Enter Password");

        final EditText input = new EditText(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton("YES",
                (dialog, which) -> {
                    insta_link = input.getText().toString();
                    if (insta_link.compareTo("") == 0) {
                        } else {
                            Toast.makeText(getActivity(),"Please fill detail.", Toast.LENGTH_SHORT).show();
                        }
                    });

        alertDialog.show();
    }

}
