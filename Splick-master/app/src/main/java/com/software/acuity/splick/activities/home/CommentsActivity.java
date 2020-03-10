package com.software.acuity.splick.activities.home;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.software.acuity.splick.R;
import com.software.acuity.splick.adapters.CommentsActivityRecyclerCustomeAdapter;
import com.software.acuity.splick.behaviours.RecyclerTouchListener;
import com.software.acuity.splick.interfaces.IRecyclerClickListener;
import com.software.acuity.splick.interfaces.IVolleyResponse;
import com.software.acuity.splick.models.home.Comment;
import com.software.acuity.splick.models.home.CommentUser;
import com.software.acuity.splick.models.home.UsersPost;
import com.software.acuity.splick.utils.Constants;
import com.software.acuity.splick.utils.SharedPreferenceClass;
import com.software.acuity.splick.utils.VolleyRequestClass;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CommentsActivity extends AppCompatActivity implements IVolleyResponse {

    String postId = "", requestTag = "comments_activity";

    @BindView(R.id.recyclerViewCommentsActivity)
    RecyclerView recyclerView;

    @BindView(R.id.avatarImage)
    CircleImageView circleImageView;

    @BindView(R.id.postTitle)
    TextView postTitle;

    @BindView(R.id.postDesc)
    TextView postDesc;

    @BindView(R.id.commentsActivityToolbar)
    Toolbar toolbar;

    @BindView(R.id.commentBtn)
    Button commentBtn;

    @BindView(R.id.commentBoxEt)
    EditText commentBoxEt;

    RecyclerView.LayoutManager layoutManager;
    CommentsActivityRecyclerCustomeAdapter commentsActivityRecyclerCustomeAdapter;

    private VolleyRequestClass volleyRequestClass;

    UsersPost usersPost;
    private SharedPreferenceClass sharedPreferenceClass;
    JSONObject userObject = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        ButterKnife.bind(this);

        sharedPreferenceClass = SharedPreferenceClass.getInstance(getApplicationContext());
        try {
            userObject = new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT));
        } catch (JSONException e) {

        }
        initToolbar();

        postId = getIntent().getStringExtra("post_id");

        requestData();

        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commentBoxEt.getText().toString().trim().isEmpty()) {
                    commentBoxEt.setError("Enter some text");
                } else {
                    postComment();
                }
            }
        });
    }

    public void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void initRecyclerView() {
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        commentsActivityRecyclerCustomeAdapter =
                new CommentsActivityRecyclerCustomeAdapter(getApplicationContext(),
                        this.usersPost.getComments());
        recyclerView.setAdapter(commentsActivityRecyclerCustomeAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(),
                recyclerView, new IRecyclerClickListener() {
            @Override
            public void onClick(View view, int position) {
//                Toast.makeText(getApplicationContext(), "Item Clicked", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
//                Toast.makeText(getApplicationContext(), "Item Long Clicked", Toast.LENGTH_SHORT).show();
            }
        }));
    }

    public void requestData() {

        HashMap<String, String> params = new HashMap<>();
        params.put("id", postId);

        volleyRequestClass = new VolleyRequestClass(CommentsActivity.this, CommentsActivity.this,
                params, "Posts", "Loading posts!", requestTag, Request.Method.POST);

        volleyRequestClass.volleyServiceCall(Constants.BASE_URL + Constants.API_AFFILIATE_POST_VIEW_SINGLE);
    }

    public void postComment() {
        HashMap<String, String> params = new HashMap<>();
        params.put("comment", commentBoxEt.getText().toString().trim() + "");
        params.put("post_id", postId);


        try {
            if (userObject.getString("user_role").trim().equalsIgnoreCase("business")) {
                params.put("business_id", new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT)).getString("id"));

                volleyRequestClass = new VolleyRequestClass(CommentsActivity.this, CommentsActivity.this,
                        params, "Comment", "Posting comment!", requestTag, Request.Method.POST);
                volleyRequestClass.volleyServiceCall(Constants.BASE_URL + Constants.API_BUSINESS_ADD_COMMENT);
            } else if (userObject.getString("user_role").trim().equalsIgnoreCase("affiliate")) {
                params.put("affiliate_id", new JSONObject(sharedPreferenceClass.getValues(Constants.USER_JSON_OBJECT)).getString("id") + "");

                volleyRequestClass = new VolleyRequestClass(CommentsActivity.this, CommentsActivity.this,
                        params, "Comment", "Posting comment!", requestTag, Request.Method.POST);
                volleyRequestClass.volleyServiceCall(Constants.BASE_URL + Constants.API_AFFILIATE_POST_COMMENT);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void networkResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);

            if (jsonObject.getBoolean("success")) {

                if (jsonObject.has("msg")) {
//                    new MaterialAlertDialogBuilder(CommentsActivity.this,
//                            R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
//                            .setTitle("Comment")
//                            .setMessage(jsonObject.getString("msg"))
//                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    commentBoxEt.setText("");
////                                    finish();
//                                    requestData();
//                                }
//                            })
//                            .show();
                    requestData();
                    commentBoxEt.setText("");

                } else {
                    UsersPost usersPost = new UsersPost();
                    List<Comment> commentList = new ArrayList<>();

                    //Data json object containing single post data
                    JSONObject postJsonObject = jsonObject.getJSONObject("data");

                    usersPost.setId(postJsonObject.getString("id"));
                    usersPost.setUser_id(postJsonObject.getString("user_id"));
                    usersPost.setTitle(postJsonObject.getString("title"));
                    usersPost.setDescription(postJsonObject.getString("description"));
                    usersPost.setMedia_id(postJsonObject.getString("media_id"));
                    usersPost.setAdd_time(postJsonObject.getString("add_time"));

                    JSONArray commentsArray = postJsonObject.getJSONArray("comments");
                    for (int i = 0; i < commentsArray.length(); i++) {
                        Comment comment = new Comment();
                        comment.setUser_id(commentsArray.getJSONObject(i).getString("user_id"));
                        comment.setComment(commentsArray.getJSONObject(i).getString("comment"));

                        CommentUser commentUser = new CommentUser();
                        commentUser.setImg_path("");
                        commentUser.setName("");
                        if (commentsArray.getJSONObject(i).getJSONObject("user").has("img_path")) {
                            JSONObject userJsonObject =
                                    commentsArray.getJSONObject(i).getJSONObject(
                                            "user");

                            commentUser.setName(userJsonObject.getString("name"));
                            commentUser.setImg_path(userJsonObject.getString("img_path"));

                        }
                        comment.setCommentUser(commentUser);

//                        try {
//                            JSONObject userJsonObject =
//                                    commentsArray.getJSONObject(i).getJSONObject(
//                                    "user");
//
//                            CommentUser commentUser = new CommentUser();
//                            commentUser.setName(userJsonObject.getString("name"));
//                            commentUser.setImg_path(userJsonObject.getString("img_path"));
//                            comment.setCommentUser(commentUser);
//                        } catch (JSONException e) {
//                            continue;
//                        }


                        commentList.add(comment);
                    }

                    usersPost.setComments(commentList);

                    this.usersPost = usersPost;
                    postTitle.setText(this.usersPost.getTitle() + "");
                    postDesc.setText(this.usersPost.getDescription() + "");

                    if (!usersPost.getMedia_id().trim().isEmpty()) {
                        Picasso.with(getApplicationContext()).load(usersPost.getMedia_id() + "")
                                //download URL
                                .placeholder(R.drawable.app_icon)//use defaul image//if failed
                                .into(circleImageView);
                    }

                    //Setup Recycler View
                    initRecyclerView();
                }
            } else {
                new MaterialAlertDialogBuilder(CommentsActivity.this,
                        R.style.ThemeOverlay_MaterialComponents_MaterialAlertDialog_Centered)
                        .setTitle("Posts")
                        .setMessage(jsonObject.getString("msg"))
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return true;
    }
}
