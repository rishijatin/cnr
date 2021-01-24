package com.example.cnrapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnrapp.R;
import com.example.cnrapp.callbacks.RecyclerViewItemClickListener;
import com.example.cnrapp.models.eCommerceCategory;

import java.util.List;

public class eCommerceListAdapter extends RecyclerView.Adapter<eCommerceListAdapter.MyViewHolder> {

    List<eCommerceCategory> categories;
    private RecyclerViewItemClickListener mListener;

    public eCommerceListAdapter(List<eCommerceCategory> categories) {
        this.categories = categories;
    }

    public void setClickListener(RecyclerViewItemClickListener mListener) {
        this.mListener = mListener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.e_commerce_category_list_item,
                parent, false);

        return new MyViewHolder(view, mListener);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        eCommerceCategory category = categories.get(position);

        holder.getTextView().setText(category.getName());

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView;

        public TextView getTextView() {
            return textView;
        }

        public MyViewHolder(@NonNull View itemView, final RecyclerViewItemClickListener mListener) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);


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
    }

}
