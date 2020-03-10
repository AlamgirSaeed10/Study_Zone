package com.software.acuity.splick.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.software.acuity.splick.R;
import com.software.acuity.splick.utils.Constants;
import com.software.acuity.splick.utils.SharedPreferenceClass;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectUserType extends AppCompatActivity {

    @BindView(R.id.affiliateBtn)
    Button affiliateBtn;

    @BindView(R.id.businessBtn)
    Button businessBtn;

    @BindView(R.id.tvSignIn)
    TextView tvSignIn;

    SharedPreferenceClass sharedPreferenceClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_selection);
        ButterKnife.bind(this);

        sharedPreferenceClass = SharedPreferenceClass.getInstance(getApplicationContext());

        //Click Listeners
        businessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectUserType.this, SignUpActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                sharedPreferenceClass.setValues(Constants.USER_TYPE, "business");
                startActivity(intent);
            }
        });

        affiliateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectUserType.this, SignUpActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                sharedPreferenceClass.setValues(Constants.USER_TYPE, "affiliate");
                startActivity(intent);
            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectUserType.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }
}
