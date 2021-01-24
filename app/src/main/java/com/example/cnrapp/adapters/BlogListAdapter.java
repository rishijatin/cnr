package com.example.cnrapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.cnrapp.R;
import com.example.cnrapp.callbacks.RecyclerViewItemClickListener;
import com.example.cnrapp.models.PostList;

import java.util.List;

public class BlogListAdapter extends RecyclerView.Adapter<BlogListAdapter.MyViewHolder> {

    private List<PostList> posts;
    private Context mContext;
    private RecyclerViewItemClickListener mListener;

    public void SetOnClickListener(RecyclerViewItemClickListener mListener) {
        this.mListener = mListener;
    }

    public BlogListAdapter(List<PostList> posts, Context mContext) {
        this.posts = posts;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.blog_post_card, parent, false);

        return new MyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PostList post = posts.get(position);

        holder.getHeadingText().setText(post.getHeading());

        RequestOptions options = new RequestOptions().centerCrop()
                .placeholder(R.drawable.ic_baseline_error_outline_24)
                .error(R.drawable.ic_baseline_error_outline_24);

        Glide.with(mContext).load(post.getPhotoURL()).apply(options).into(holder.getImageView());

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        private final TextView headingText;
        private final ImageView imageView;

        public MyViewHolder(@NonNull View itemView, final RecyclerViewItemClickListener mListener) {
            super(itemView);
            headingText = itemView.findViewById(R.id.postText);
            imageView = itemView.findViewById(R.id.postImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onClick(position);
                        }
                    }
                }
            });


        }

        public TextView getHeadingText() {
            return headingText;
        }

        public ImageView getImageView() {
            return imageView;
        }

    }


}
