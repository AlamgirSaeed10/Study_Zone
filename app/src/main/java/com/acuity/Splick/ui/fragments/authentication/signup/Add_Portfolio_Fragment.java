package com.acuity.Splick.ui.fragments.authentication.signup;

import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.acuity.Splick.R;
import com.acuity.Splick.ui.activities.Dashboard.Main_Dashboard;
import com.acuity.Splick.util.PrefUtil;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class Add_Portfolio_Fragment extends Fragment {
    ProgressDialog p;
    private AddPortfolioViewModel mViewModel;
    @BindView(R.id.portfolio_upload_btn)
    Button btnUpload;
    @BindView(R.id.skip_bio_tv)
    TextView tvSkip;
    @BindView(R.id.back_image)
    ImageView imgBack;

    public static Add_Portfolio_Fragment newInstance() {
        return new Add_Portfolio_Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add__portfolio__fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AddPortfolioViewModel.class);
        // TODO: Use the ViewModel
        btnUpload.setOnClickListener(v -> {
           AsyncTaskExample example = new AsyncTaskExample();
           example.execute();
            Navigation.findNavController(v).navigate(R.id.action_add_Portfolio_Fragment_to_profile_Completed_Fragment);
        });
        tvSkip.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_add_Portfolio_Fragment_to_profile_Completed_Fragment);
        });
        imgBack.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_add_Portfolio_Fragment_to_social_Reach_Fragment);
        });
    }

    @SuppressLint("StaticFieldLeak")
    private class AsyncTaskExample extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(getActivity());
            p.setMessage("Please wait...System is verifying User");
            p.setIndeterminate(false);
            p.setCancelable(false);
            p.show();
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mViewModel.checkLogin(email_sign_in_edt.getText().toString().trim(), password_sign_in_edt.getText().toString().trim());
            mViewModel.getUserRepository().observe(getViewLifecycleOwner(), auth -> {
                if (auth.getSuccess()==true) {
                    p.dismiss();
                    PrefUtil.saveUser(auth.getData(), Objects.requireNonNull(getActivity()));
                    Intent intent = new Intent(getActivity(), Main_Dashboard.class);
                    startActivity(intent);
                } else {
                    p.dismiss();
                    Toasty.error(Objects.requireNonNull(getActivity()), "Check email or password.", Toast.LENGTH_SHORT, true).show();
                }
            });
        }
    }

}
