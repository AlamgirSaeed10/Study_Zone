package com.acuity.Splick.ui.fragments.authentication.login;

import android.annotation.SuppressLint;
import android.app.*;
import android.content.Intent;
import android.os.*;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.*;
import android.widget.*;
import androidx.annotation.*;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.acuity.Splick.*;
import com.acuity.Splick.ui.activities.Dashboard.Main_Dashboard;
import com.acuity.Splick.util.*;
import com.google.android.material.textfield.*;
import java.util.Objects;
import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class Sign_In_Fragment extends Fragment {
    private ProgressDialog p;
    private SignInViewModel mViewModel;
    @BindView(R.id.back_image)
    ImageView imgBack;

    @BindView(R.id.email_sign_in_edt)
    EditText email_sign_in_edt;
    @BindView(R.id.password_sign_in_edt)
    EditText password_sign_in_edt;
    @BindView(R.id.email_sign_in_edt1)
    TextInputLayout email_sign_in_edt1;
    @BindView(R.id.password_sign_in_edt1)
    TextInputLayout password_sign_in_edt1;
    @BindView(R.id.sign_in_next_btn)
    Button sign_in_next_btn;

    public static Sign_In_Fragment newInstance() {
        return new Sign_In_Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign__in__fragment, container, false);
        mViewModel=new ViewModelProvider(Objects.requireNonNull(getActivity())).get(SignInViewModel.class);
        ButterKnife.bind(this, view);
        return view;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imgBack.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_sign_In_Fragment_to_select_User_Type_Fragment));
        sign_in_next_btn.setOnClickListener(this::verifyUser);
    }

    private boolean validateFunc() {
        if (email_sign_in_edt.getText().toString().trim().isEmpty()) {
            email_sign_in_edt1.setError("Email is required");
            password_sign_in_edt1.setError(null);
            return false;
        } else if (!isValidEmail(email_sign_in_edt.getText().toString())) {
            email_sign_in_edt1.setError("Invalid Email");
            password_sign_in_edt1.setError(null);
            return false;
        } else if (password_sign_in_edt.getText().toString().trim().isEmpty()) {
            password_sign_in_edt1.setError("Password is required");
            email_sign_in_edt1.setError(null);
            return false;
        }
        else{
            email_sign_in_edt1.setError(null);
            password_sign_in_edt1.setError(null);
        }
        return true;
    }

    private static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    private void verifyUser (View v) {
        if (validateFunc()) {
            AsyncTaskExample example = new AsyncTaskExample();
            example.execute();
        }
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
