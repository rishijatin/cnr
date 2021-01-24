package com.example.cnrapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cnrapp.R;
import com.example.cnrapp.callbacks.RecyclerViewItemClickListener;
import com.example.cnrapp.models.eHealthCategory;

import java.util.List;

public class eHealthCategoryAdapter extends RecyclerView.Adapter<eHealthCategoryAdapter.MyViewHolder> {

    private List<eHealthCategory> category;
    private RecyclerViewItemClickListener mListener;

    public eHealthCategoryAdapter(List<eHealthCategory> category) {
        this.category = category;
    }

    public void setClickListener(RecyclerViewItemClickListener mListener)
    {
        this.mListener=mListener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.e_health_category_item,
                parent, false);

        return new MyViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        eHealthCategory cat = category.get(position);

        holder.textView.setText(cat.getName());

    }

    @Override
    public int getItemCount() {
        return category.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public MyViewHolder(@NonNull View itemView, RecyclerViewItemClickListener mListener) {
            super(itemView);
            textView=itemView.findViewById(R.id.category_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(getAdapterPosition());
                }
            });
        }


    }

}
