package com.software.acuity.splick.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.software.acuity.splick.R;
import com.software.acuity.splick.models.home.Comment;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.facebook.FacebookSdk.getApplicationContext;

public class CommentsActivityRecyclerCustomeAdapter extends RecyclerView.Adapter<CommentsActivityRecyclerCustomeAdapter.MyViewHolder> {

    private List<Comment> mDataSet;
    private LayoutInflater layoutInflater;
    private Context context;
    private ImageLoader mImageLoader;

    public CommentsActivityRecyclerCustomeAdapter(Context context, List<Comment> dataList) {
        this.mDataSet = dataList;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate a view and pass it to the view holder class
        View rowView = layoutInflater.inflate(R.layout.comments_row_view, null);

        MyViewHolder myViewHolder = new MyViewHolder(rowView);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.commentTextView.setText(mDataSet.get(position).getComment());

        holder.commentUserTitle.setText(mDataSet.get(position).getCommentUser().getName() + "");

        if (!mDataSet.get(position).getCommentUser().getImg_path().trim().isEmpty()) {
            Picasso.with(getApplicationContext()).load(mDataSet.get(position).getCommentUser().getImg_path() + "")
                    //download URL
                    .placeholder(R.drawable.app_icon)//use defaul image//if failed
                    .into(holder.commentsAvatar);
        }
//
//        ImageLoader mImageLoader = AppController.getInstance().getImageLoader();

//        if(!mDataSet.get(position).getCommentUser().getImg_path().isEmpty()){
//            holder.commentsAvatar.setImageUrl(mDataSet.get(position).getCommentUser().getImg_path() + ""
//                    , mImageLoader);
//        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.commentsTextView)
        TextView commentTextView;

        @BindView(R.id.commentAvatar)
        CircleImageView commentsAvatar;


        @BindView(R.id.commentUserTitle)
        TextView commentUserTitle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
