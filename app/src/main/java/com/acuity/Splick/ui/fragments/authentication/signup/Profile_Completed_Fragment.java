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

import com.acuity.Splick.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Profile_Completed_Fragment extends Fragment {

    private ProfileCompletedFragmentViewModel mViewModel;

    @BindView(R.id.complete_profile_btn)
    Button btnCompleted;

    public static Profile_Completed_Fragment newInstance() {
        return new Profile_Completed_Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.profile__completed_fragment, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ProfileCompletedFragmentViewModel.class);
        // TODO: Use the ViewModel
        btnCompleted.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_profile_Completed_Fragment_to_sign_In_Fragment);
        });
    }

}
