package com.acuity.Splick.ui.fragments.authentication.signup;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
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
import com.acuity.Splick.ui.fragments.authentication.signup.userInfo.SignUpInfoFragmentViewModel;
import com.acuity.Splick.util.Constant;

import java.util.HashMap;
import java.util.Objects;

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
    private HashMap<String,Object> userSocialConnection=new HashMap<>();

    String insta_link;

    private SignUpInfoFragmentViewModel mViewModel;

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
        mViewModel=new ViewModelProvider(Objects.requireNonNull(getActivity())).get(SignUpInfoFragmentViewModel.class);

        // TODO: Use the ViewModel
        btnNext.setOnClickListener(v -> {
            userSocialConnection.put("user_id", Constant.USER_ID);
            mViewModel.updateUser(userSocialConnection);
            mViewModel.getUpdateUserLiveData().observe(getViewLifecycleOwner(), register -> {
                if(register.getSuccess()){
                    Navigation.findNavController(v).navigate(R.id.action_social_Reach_Fragment_to_add_Portfolio_Fragment);
                }
                else {
                    Toast.makeText(getActivity(), "Error Occurred", Toast.LENGTH_SHORT).show();
                }
            });

        });
        tvSkip.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_social_Reach_Fragment_to_add_Portfolio_Fragment));
        imBack.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_social_Reach_Fragment_to_sign_Up_Bio_Fragment));

        connect_to_insta.setOnClickListener(v-> getInstaName());
        connect_to_fb.setOnClickListener(v-> getFbName());
        connect_to_twitter.setOnClickListener(v-> getTwitterFollowers());
        connect_to_youtube.setOnClickListener(v-> getYoutubeFollowers());
        connect_to_snapchat.setOnClickListener(v-> getSnapchatFollowers());

    }

    @SuppressLint("ResourceAsColor")
    private void getInstaName() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Instagram");
        alertDialog.setIcon(R.drawable.insta);

        final EditText input = new EditText(getActivity());
        input.setHint("Enter Instagram name/user ID");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton("YES",
                (dialog, which) -> {
                    insta_link = input.getText().toString();
                    if (insta_link.compareTo("") == 0) {
                        Toast.makeText(getActivity(),"Please fill detail.", Toast.LENGTH_SHORT).show();
                    } else {
                        connect_to_insta.setText(""+insta_link);
                        connect_to_insta.setTextColor(R.color.black);
                    }
                    });

        alertDialog.show();
    }
    @SuppressLint("ResourceAsColor")
    public void getFbName() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Facebook");
        alertDialog.setIcon(R.drawable.facebook);

        final EditText input = new EditText(getActivity());
        input.setHint("Enter facebook name/user ID");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton("YES",
                (dialog, which) -> {
                    insta_link = input.getText().toString();
                    if (insta_link.compareTo("") == 0) {
                        Toast.makeText(getActivity(),"Please fill detail.", Toast.LENGTH_SHORT).show();
                    } else {
                        connect_to_fb.setText(""+insta_link);
                        connect_to_fb.setTextColor(R.color.black);
                        userSocialConnection.put("fab_username",insta_link);
                    }
                    });

        alertDialog.show();
    }
    @SuppressLint("ResourceAsColor")
    public void getYoutubeFollowers() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("YouTube");
        alertDialog.setIcon(R.drawable.youtube);

        final EditText input = new EditText(getActivity());
        input.setHint("Enter youtube name/user ID");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton("YES",
                (dialog, which) -> {
                    insta_link = input.getText().toString();
                    if (insta_link.compareTo("") == 0) {
                        Toast.makeText(getActivity(),"Please fill detail.", Toast.LENGTH_SHORT).show();
                    } else {
                        connect_to_youtube.setText(""+insta_link);
                        connect_to_youtube.setTextColor(R.color.black);
                        userSocialConnection.put("youtube_username",insta_link);
                    }
                    });

        alertDialog.show();
    }
    @SuppressLint("ResourceAsColor")
    public void getTwitterFollowers() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Twitter");
        alertDialog.setIcon(R.drawable.twitter);

        final EditText input = new EditText(getActivity());
        input.setHint("Enter Twitter name/user ID");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton("YES",
                (dialog, which) -> {
                    insta_link = input.getText().toString();
                    if (insta_link.compareTo("") == 0) {
                        Toast.makeText(getActivity(),"Please fill detail.", Toast.LENGTH_SHORT).show();
                    } else {
                        connect_to_twitter.setText(""+insta_link);
                        connect_to_twitter.setTextColor(R.color.black);
                        userSocialConnection.put("twitter_username",insta_link);
                    }
                    });

        alertDialog.show();
    }
    @SuppressLint("ResourceAsColor")
    public void getSnapchatFollowers() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Snapchat");
        alertDialog.setIcon(R.drawable.snapchat);

        final EditText input = new EditText(getActivity());
        input.setHint("Enter SnapChat name/user ID.");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);

        alertDialog.setPositiveButton("YES",
                (dialog, which) -> {
                    insta_link = input.getText().toString();
                    if (insta_link.compareTo("") == 0) {
                        Toast.makeText(getActivity(),"Please fill detail.", Toast.LENGTH_SHORT).show();
                    } else {
                        connect_to_snapchat.setText(""+insta_link);
                        connect_to_snapchat.setTextColor(R.color.black);
                        userSocialConnection.put("snapchat_username",insta_link);
                    }
                    });

        alertDialog.show();
    }

}
