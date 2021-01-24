package com.example.cnrapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.cnrapp.R;
import com.example.cnrapp.models.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<Category> categories;


    public CategoryAdapter(Context mContext, List<Category> categories) {
        this.mContext = mContext;
        this.categories = categories;
    }

    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Category category = categories.get(position);

        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.category_card, null);
        }

        TextView categoryName = convertView.findViewById(R.id.category_name);
        ImageView categoryIcon = convertView.findViewById(R.id.category_icon);

        categoryName.setText(category.getName());

        RequestOptions options = new RequestOptions().centerCrop().
                placeholder(R.drawable.ic_baseline_error_outline_24).
                error(R.drawable.ic_baseline_error_outline_24);

        Glide.with(mContext).load(category.getUrl()).apply(options).into(categoryIcon);

        return convertView;


    }
}
