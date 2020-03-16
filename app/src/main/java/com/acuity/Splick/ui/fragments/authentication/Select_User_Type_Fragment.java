package com.acuity.Splick.ui.fragments.authentication;

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
import android.widget.TextView;

import com.acuity.Splick.R;

import butterknife.BindView;
import butterknife.ButterKnife;



public class Select_User_Type_Fragment extends Fragment {


    @BindView(R.id.sign_in_tv)
    TextView btnSign;
    @BindView(R.id.affiliate_signup_btn)
    Button btnAffiliate;
    @BindView(R.id.business_signup_btn)
    Button btnBusiness;


    public static Select_User_Type_Fragment newInstance() {
        return new Select_User_Type_Fragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.select__user__type__fragment, container, false);
        ButterKnife.bind(this, view);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel
        btnSign.setOnClickListener(view->{
            Navigation.findNavController(view).navigate(R.id.goToSignIn);
        });
        btnAffiliate.setOnClickListener(view ->{
            navigationController("Affiliate",view);
        });
        btnBusiness.setOnClickListener(view ->{
            navigationController("Business",view);
        });
    }
    public void navigationController(String user_role,View view){
       Select_User_Type_FragmentDirections.GoToSignUP goToSignUP=new Select_User_Type_FragmentDirections.GoToSignUP(user_role);
        Navigation.findNavController(view).navigate(goToSignUP);
    }
}
