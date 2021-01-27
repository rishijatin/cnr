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
import com.example.cnrapp.models.eCommerceItem;

import java.util.List;

public class eCommerceItemAdapter extends RecyclerView.Adapter<eCommerceItemAdapter.MyViewHolder> {

    private List<eCommerceItem> item;
    private Context mContext;
    private RecyclerViewItemClickListener mListener;

    public void SetOnClickListener(RecyclerViewItemClickListener mListener) {
        this.mListener = mListener;
    }

    public eCommerceItemAdapter(List<eCommerceItem> posts, Context mContext) {
        this.item = posts;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.e_commerce_list, parent, false);

        return new MyViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        eCommerceItem post = item.get(position);

        holder.getHeadingText().setText(post.getHeading());
        holder.getPriceText().setText(post.getPrice());
        RequestOptions options = new RequestOptions().centerCrop()
                .placeholder(R.drawable.ic_baseline_error_outline_24)
                .error(R.drawable.ic_baseline_error_outline_24);

        Glide.with(mContext).load(post.getPhotoURL()).apply(options).into(holder.getImageView());

    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {


        private final TextView headingText;
        private final TextView priceText;
        private final ImageView imageView;

        public MyViewHolder(@NonNull View itemView, final RecyclerViewItemClickListener mListener) {
            super(itemView);
            headingText = itemView.findViewById(R.id.postText);
            imageView = itemView.findViewById(R.id.postImage);
            priceText = itemView.findViewById(R.id.postPrice);

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

        public TextView getPriceText() {
            return priceText;
        }

        public ImageView getImageView() {
            return imageView;
        }

    }


}