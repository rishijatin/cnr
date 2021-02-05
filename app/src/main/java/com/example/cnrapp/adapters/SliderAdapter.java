package com.example.cnrapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.cnrapp.R;
import com.example.cnrapp.models.PersonPics;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.MySliderHolder> {

    private Context context;
    private List<PersonPics> photos;

    public SliderAdapter(Context context, List<PersonPics> photos) {
        this.context = context;
        this.photos = photos;
    }

    @Override
    public MySliderHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item, null);
        return new MySliderHolder(view);
    }

    @Override
    public void onBindViewHolder(MySliderHolder viewHolder, int position) {

        Glide.with(viewHolder.itemView).load(photos.get(position).getUrl()).fitCenter().
                into(viewHolder.imageView);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(photos.get(position).getUrl());
                Intent i= new Intent(Intent.ACTION_VIEW,uri);
                if(i.resolveActivity(context.getPackageManager())!=null)
                {
                    context.startActivity(i);
                }
            }
        });

    }

    @Override
    public int getCount() {
        return photos.size();
    }

    class MySliderHolder extends SliderViewAdapter.ViewHolder {

        ImageView imageView;
        View itemView;

        public MySliderHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            this.itemView = itemView;

        }



    }
}
