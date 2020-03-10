package com.software.acuity.splick.fragments.business;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.software.acuity.splick.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Business_EditMarketingMaterial extends AppCompatActivity {


    @BindView(R.id.insta_marketing_material)
    ImageButton insta_marketing_material;

    @BindView(R.id.insta_story_marketing_material)
    ImageButton insta_story_marketing_material;

    @BindView(R.id.facebook_marketing_material)
    ImageButton facebook_marketing_material;

    @BindView(R.id.create_new_folder)
    TextView create_new_folder;

    @BindView(R.id.cloud_upload)
    ImageButton cloud_upload;

    @BindView(R.id.back_btn)
    ImageButton back_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business__edit_marketing_material);

        ButterKnife.bind(this);

        insta_marketing_material.setOnClickListener(v -> Toast.makeText(this, "instagram marketing material".toUpperCase(), Toast.LENGTH_SHORT).show());
        insta_story_marketing_material.setOnClickListener(v-> Toast.makeText(this, "instagram story marketing material", Toast.LENGTH_SHORT).show());
        facebook_marketing_material.setOnClickListener(v-> Toast.makeText(this, "Facebook Marketing material", Toast.LENGTH_SHORT).show());
        create_new_folder.setOnClickListener(v-> Toast.makeText(this, "Create New Folder", Toast.LENGTH_SHORT).show());
        back_btn.setOnClickListener(v->finish());
        cloud_upload.setOnClickListener(v-> Toast.makeText(this, "Cloud Storage Upload.", Toast.LENGTH_SHORT).show());



    }
}
