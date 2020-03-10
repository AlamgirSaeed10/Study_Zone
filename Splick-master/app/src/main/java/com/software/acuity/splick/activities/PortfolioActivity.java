package com.software.acuity.splick.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.software.acuity.splick.R;
import com.software.acuity.splick.activities.home.CommentsActivity;
import com.software.acuity.splick.models.PortfolioModel;
import com.software.acuity.splick.models.home.UsersPost;
import com.software.acuity.splick.utils.Constants;
import com.software.acuity.splick.utils.SharedPreferenceClass;
import com.software.acuity.splick.utils.UtilsClass;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class PortfolioActivity extends AppCompatActivity {

    UsersPost currentUsersPost = new UsersPost();
    PortfolioModel portfolioModel = new PortfolioModel();
    SharedPreferenceClass sharedPreferenceClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);

        sharedPreferenceClass = SharedPreferenceClass.getInstance(getApplicationContext());

        try {
            if (new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT)).getString("user_role").equalsIgnoreCase("business")) {

                this.currentUsersPost = (UsersPost) getIntent().getSerializableExtra("post_obj");

                if (!currentUsersPost.getUser_profile().trim().isEmpty()) {
                    Picasso.with(getApplicationContext()).load(currentUsersPost.getUser_profile() + "")
                            //download URL
                            .placeholder(R.drawable.app_icon)//use defaul image//if failed
                            .into(((ImageView) findViewById(R.id.postAvatar)));
                }

                if (!currentUsersPost.getFile_url().trim().isEmpty()) {
                    Picasso.with(getApplicationContext()).load(currentUsersPost.getFile_url() + "")
                            //download URL
                            .placeholder(R.drawable.app_icon)//use defaul image//if failed
                            .into(((ImageView) findViewById(R.id.postMainImage)));
                }
            } else {
                this.portfolioModel = (PortfolioModel) getIntent().getSerializableExtra("portfolio_obj");
                findViewById(R.id.postCommentBtn).setVisibility(View.INVISIBLE);

//                if (!portfolioModel.getUser_profile().trim().isEmpty()) {
//                    Picasso.with(getApplicationContext()).load(currentPost.getUser_profile() + "")
//                            //download URL
//                            .placeholder(R.drawable.app_icon)//use defaul image//if failed
//                            .into(((ImageView) findViewById(R.id.postAvatar)));
//                }


                if (!portfolioModel.getFile_url().trim().isEmpty()) {
                    Picasso.with(getApplicationContext()).load(portfolioModel.getFile_url() + "")
                            //download URL
                            .placeholder(R.drawable.app_icon)//use defaul image//if failed
                            .into(((ImageView) findViewById(R.id.postMainImage)));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            if (!new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT)).getString("thumbnail_url").isEmpty()) {
                UtilsClass.setImageUsingPicasso(getApplicationContext(), new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT)).getString("thumbnail_url"),
                        ((ImageView) findViewById(R.id.postAvatar)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        ((TextView) findViewById(R.id.postFullName)).setText(currentUsersPost.getFull_name());
        ((TextView) findViewById(R.id.postDesc)).setText(currentUsersPost.getDescription());


        findViewById(R.id.postMainImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PortfolioActivity.this, CommentsActivity.class);
                intent.putExtra("post_id", currentUsersPost.getId());
                startActivity(intent);
            }
        });

        findViewById(R.id.postCommentBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PortfolioActivity.this, CommentsActivity.class);
                intent.putExtra("post_id", currentUsersPost.getId());
                startActivity(intent);
            }
        });

        findViewById(R.id.close_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PortfolioActivity.this, "close it!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
