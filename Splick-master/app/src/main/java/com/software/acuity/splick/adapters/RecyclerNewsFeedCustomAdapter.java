package com.software.acuity.splick.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.software.acuity.splick.R;
import com.software.acuity.splick.activities.home.CommentsActivity;
import com.software.acuity.splick.models.home.UsersPost;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.facebook.FacebookSdk.getApplicationContext;

public class RecyclerNewsFeedCustomAdapter extends RecyclerView.Adapter<RecyclerNewsFeedCustomAdapter.MyViewHolder> {

    private List<UsersPost> mDataSet;
    private LayoutInflater layoutInflater;
    private Context context;
    private ImageLoader mImageLoader;

    public RecyclerNewsFeedCustomAdapter(Context context, List<UsersPost> dataList) {
        this.mDataSet = dataList;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate a view and pass it to the view holder class
        View rowView = layoutInflater.inflate(R.layout.row_view_news_feed, null);
        MyViewHolder myViewHolder = new MyViewHolder(rowView);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.postFullName.setText(mDataSet.get(position).getFull_name());
        holder.postDesc.setText(mDataSet.get(position).getDescription());


        if (!mDataSet.get(position).getUser_profile().trim().isEmpty()) {
            Picasso.with(getApplicationContext()).load(mDataSet.get(position).getUser_profile() + "")
                    .placeholder(R.drawable.app_icon)//use default image//if failed
                    .into(holder.postAvatar);
        }


        if (!mDataSet.get(position).getMedia_id().trim().isEmpty()) {
            Picasso.with(getApplicationContext()).load(mDataSet.get(position).getMedia_id() + "")
                    //download URL
                    .placeholder(R.drawable.app_icon)//use defaul image//if failed
                    .into(holder.postMainImage);
        }

        holder.postMainImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommentsActivity.class);
                intent.putExtra("post_id", mDataSet.get(position).getId());

                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                context.startActivity(intent);
            }
        });
        holder.postCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommentsActivity.class);
                intent.putExtra("post_id", mDataSet.get(position).getId());
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return this.mDataSet.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.postAvatar)
        CircleImageView postAvatar;

        @BindView(R.id.postFullName)
        TextView postFullName;

        @BindView(R.id.postMainImage)
        ImageView postMainImage;

        @BindView(R.id.postDesc)
        TextView postDesc;

        @BindView(R.id.postCommentBtn)
        ImageButton postCommentBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    public void stopShimmer() {

    }
}
