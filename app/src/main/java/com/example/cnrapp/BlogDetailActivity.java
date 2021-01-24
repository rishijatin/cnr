package com.example.cnrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.cnrapp.api.RetrofitBuilder;
import com.example.cnrapp.callbacks.RetrofitCallBack;
import com.example.cnrapp.models.Category;
import com.example.cnrapp.models.PostListDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlogDetailActivity extends AppCompatActivity {
    int blogId;
    PostListDetail post;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_detail);
        getSupportActionBar().hide();
        blogId = getIntent().getIntExtra("blogID",0);

        progressBar=findViewById(R.id.progressBar);

        getBlog(new RetrofitCallBack() {
            @Override
            public void onComplete() {
                loadBlog();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure() {
                progressBar.setVisibility(View.GONE);
            }
        });



    }

    private void loadBlog()
    {
        TextView heading = findViewById(R.id.heading);
        TextView description = findViewById(R.id.description);
        TextView knowMore = findViewById(R.id.knowMore);
        ImageView imageView = findViewById(R.id.image);

        heading.setText(post.getHeading());
        description.setText(post.getDescription());
        knowMore.setText("Click Here To Know More");

        RequestOptions options = new RequestOptions().centerCrop()
                .placeholder(R.drawable.ic_baseline_error_outline_24)
                .error(R.drawable.ic_baseline_error_outline_24);

        Glide.with(this).load(post.getPhotoUrl()).apply(options).into(imageView);

        knowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(post.getWebPageUrl());
                Intent i= new Intent(Intent.ACTION_VIEW,uri);
                if(i.resolveActivity(getPackageManager())!=null)
                {
                    startActivity(i);
                }
            }
        });


    }


    private void getBlog(RetrofitCallBack callBack)
    {
        Call<PostListDetail> call = RetrofitBuilder.BuildRetrofit.build().getPost(blogId);

        call.enqueue(new Callback<PostListDetail>() {
            @Override
            public void onResponse(Call<PostListDetail> call, Response<PostListDetail> response) {
                if(!response.isSuccessful())
                {
                    Toast.makeText(BlogDetailActivity.this, "Some error occured!!", Toast.LENGTH_SHORT).show();
                    callBack.onFailure();
                    return;
                }
                post= response.body();
                callBack.onComplete();

            }

            @Override
            public void onFailure(Call<PostListDetail> call, Throwable t) {
                Toast.makeText(BlogDetailActivity.this, "Some error occured!!", Toast.LENGTH_SHORT).show();
                callBack.onFailure();
            }
        });
    }

}