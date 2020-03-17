package com.acuity.Splick.ui.fragments.authentication.signup;

import  android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.acuity.Splick.R;
import com.acuity.Splick.models.Register;
import com.acuity.Splick.ui.fragments.authentication.signup.userInfo.SignUpInfoFragmentViewModel;
import com.acuity.Splick.util.Constant;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;

public class Sign_Up_About_Fragment extends Fragment {
    private static final String TAG = "Sign_Up_About_Fragment";
    private SignUpInfoFragmentViewModel mViewModel;
    ProgressDialog p;
    @BindView(R.id.sign_up_about_next_btn)
    Button btnNext;
    @BindView(R.id.back_image)
    ImageView imgBack;

    @BindView(R.id.about_name_edt)
    EditText about_name_edt;
    @BindView(R.id.about_username_edt)
    EditText about_username_edt;
    @BindView(R.id.about_location_edt)
    EditText about_location_edt;

    @BindView(R.id.about_name_edt1)
    TextInputLayout about_name_edt1;
    @BindView(R.id.about_username_edt1)
    TextInputLayout about_username_edt1;
    @BindView(R.id.about_location_edt1)
    TextInputLayout about_location_edt1;


    public static Sign_Up_About_Fragment newInstance() {
        return new Sign_Up_About_Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign__up__about__fragment, container, false);
        ButterKnife.bind(this,view);
        mViewModel=new ViewModelProvider(getActivity()).get(SignUpInfoFragmentViewModel.class);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel
        imgBack.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_sign_Up_About_Fragment_to_sign_Up_Tags_Fragment);
        });
        btnNext.setOnClickListener(v -> {
            if(validateFunc()) {
                AsyncTaskExample example = new AsyncTaskExample();
                example.execute();
            }
        });
    }

    public boolean validateFunc(){
        if(about_name_edt.getText().toString().isEmpty()) {
            about_name_edt1.setError("Please enter name.");
            about_location_edt1.setError(null);
            about_username_edt1.setError(null);
            return false;
        }
        else if(about_name_edt.getText().toString().length() <3) {
            about_name_edt1.setError("Name must be greater then 3 characters.");
            about_location_edt1.setError(null);
            about_username_edt1.setError(null);
            return false;
        }else if(about_username_edt.getText().toString().length() < 3) {
            about_username_edt1.setError("Username must be grater then 3 characters.");
            about_name_edt1.setError(null);
            about_location_edt1.setError(null);
            return false;
        }else if(about_username_edt.getText().toString().isEmpty()) {
            about_username_edt1.setError("Please enter username");
            about_name_edt1.setError(null);
            about_location_edt1.setError(null);
            return false;
        }else if(about_location_edt.getText().toString().isEmpty()) {
            about_location_edt1.setError("Please enter Location.");
            about_name_edt1.setError(null);
            about_username_edt1.setError(null);
            return false;
        }else if(about_location_edt.getText().toString().length() < 4) {
            about_location_edt1.setError("Location must be greater then 4 characters.");
            about_name_edt1.setError(null);
            about_username_edt1.setError(null);
            return false;
        }
        return true;
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
            p.setMessage("Please wait...Profile is updating.");
            p.setIndeterminate(false);
            p.setCancelable(false);
            p.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            HashMap<String,Object> objectHashMap=new HashMap<>();
            objectHashMap.put("user_id",Constant.USER_ID);
            objectHashMap.put("full_name",about_name_edt.getText().toString().trim());
            //todo: FIX for future
            objectHashMap.put("user_name",about_username_edt.getText().toString().trim());
            objectHashMap.put("user_location",about_location_edt.getText().toString().trim());
            mViewModel.updateUser(objectHashMap);
            mViewModel.getUpdateUserLiveData().observe(getViewLifecycleOwner(), new Observer<Register>() {
                @Override
                public void onChanged(Register register) {
                    if(register.getSuccess()==true){
                        p.dismiss();
                        Navigation.findNavController(Objects.requireNonNull(getView())).navigate(R.id.action_sign_Up_About_Fragment_to_sign_Up_Tags_Fragment);
                    }
                    else {
                        //TODO: show error
                        p.dismiss();
                        Toasty.error(Objects.requireNonNull(getActivity()),"Error during updating profile.",Toast.LENGTH_LONG,true).show();
                    }
                }
            });
        }
    }
}
