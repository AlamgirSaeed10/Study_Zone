package com.acuity.Splick.ui.fragments.authentication.signup.userInfo;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.acuity.Splick.R;

import com.acuity.Splick.util.Constant;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class Sign_Up_Info_fragment extends Fragment {
    private static final String TAG = "Sign_Up_Info_fragment";
    ProgressDialog p;
    private SignUpInfoFragmentViewModel mViewModel;
    @BindView(R.id.sign_up_next_btn)
    Button btNext;
    @BindView(R.id.back_image)
    ImageView imgBack;

    @BindView(R.id.email_sign_up_edt)
    EditText email_sign_up_edt;
    @BindView(R.id.password_sign_up_edt)
    EditText password_sign_up_edt;
    @BindView(R.id.conf_pass_sign_up_edt)
    EditText conf_pass_sign_up_edt;

    @BindView(R.id.email_sign_up_edt1)
    TextInputLayout email_sign_up_edt1;
    @BindView(R.id.password_sign_up_edt1)
    TextInputLayout password_sign_up_edt1;
    @BindView(R.id.conf_pass_sign_up_edt1)
    TextInputLayout conf_pass_sign_up_edt1;
    String user_role="";
    String email="";
    String pass="";


    public static Sign_Up_Info_fragment newInstance() {
        return new Sign_Up_Info_fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_up_info_fragment, container, false);
        ButterKnife.bind(this, view);
        mViewModel = new ViewModelProvider(getActivity()).get(SignUpInfoFragmentViewModel.class);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Sign_Up_Info_fragmentArgs args = Sign_Up_Info_fragmentArgs.fromBundle(getArguments());
        user_role = args.getUserRole();
        imgBack.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_sign_Up_Info_fragment_to_select_User_Type_Fragment);
        });
        btNext.setOnClickListener(v -> {
            if (validateFunc()) {
                 //Todo: progressBar
                //registerUser(email_sign_up_edt.getText().toString().trim(), password_sign_up_edt.getText().toString().trim(), user_role
                AsyncTaskExample example = new AsyncTaskExample();
                example.execute();
            }
        });
    }

    public boolean validateFunc() {

        if (email_sign_up_edt.getText().toString().isEmpty()) {
            email_sign_up_edt1.setError("Enter email address.");
            conf_pass_sign_up_edt1.setError(null);
            password_sign_up_edt1.setError(null);
            return false;
        } else if (password_sign_up_edt.getText().toString().isEmpty()) {
            password_sign_up_edt1.setError("Please enter password.");
            conf_pass_sign_up_edt1.setError(null);
            email_sign_up_edt1.setError(null);
            return false;
        } else if (conf_pass_sign_up_edt.getText().toString().isEmpty()) {
            conf_pass_sign_up_edt1.setError("Please enter confirm password.");
            password_sign_up_edt1.setError(null);
            email_sign_up_edt1.setError(null);
            return false;
        } else if (!isValidEmail(email_sign_up_edt.getText().toString().trim())) {
            email_sign_up_edt1.setError("Please enter valid email.");
            conf_pass_sign_up_edt1.setError(null);
            password_sign_up_edt1.setError(null);
            return false;
        } else if (password_sign_up_edt.getText().equals(conf_pass_sign_up_edt.getText().toString().trim())) {
            conf_pass_sign_up_edt1.setError(null);
            password_sign_up_edt1.setError(null);
            email_sign_up_edt1.setError(null);
            return false;
        } else if (password_sign_up_edt.getText().toString().length() > 8 || password_sign_up_edt.getText().toString().length() < 6) {
            password_sign_up_edt1.setError("Password must be 6-8 characters long.");
            conf_pass_sign_up_edt1.setError(null);
            email_sign_up_edt1.setError(null);
            return false;
        } else if (!password_sign_up_edt.getText().toString().equals(conf_pass_sign_up_edt.getText().toString())) {
            conf_pass_sign_up_edt1.setError("Password mismatch.");
            password_sign_up_edt1.setError(null);
            email_sign_up_edt1.setError(null);
            return false;
        }
        return true;
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    @Override
    public void onPause() {
        super.onPause();
        conf_pass_sign_up_edt1.setError(null);
        password_sign_up_edt1.setError(null);
        email_sign_up_edt1.setError(null);
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
            p.setMessage("Please wait...Creating your account.");
            p.setIndeterminate(false);
            p.setCancelable(false);
            p.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            email = email_sign_up_edt.getText().toString().trim();
            pass = password_sign_up_edt.getText().toString().trim();

            mViewModel.setSignUp(email, pass, user_role);
            mViewModel.setSignUPLiveData().observe(getViewLifecycleOwner(), register -> {
                if (register.getSuccess() == true) {
                    Constant.USER_ID = register.getData();
                    p.dismiss();
                    Navigation.findNavController(getView()).navigate(R.id.action_sign_Up_Info_fragment_to_sign_Up_About_Fragment);
                } else {
                    p.dismiss();
                    Toasty.warning(getActivity(), "Already register", Toasty.LENGTH_SHORT,true).show();
                }
            });

        }
    }
}
