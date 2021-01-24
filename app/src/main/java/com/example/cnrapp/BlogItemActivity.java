package com.example.cnrapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cnrapp.adapters.BlogListAdapter;
import com.example.cnrapp.api.RetrofitBuilder;
import com.example.cnrapp.callbacks.RecyclerViewItemClickListener;
import com.example.cnrapp.callbacks.RetrofitCallBack;
import com.example.cnrapp.models.PostList;
import com.example.cnrapp.models.PostListModified;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlogItemActivity extends AppCompatActivity {


    private int categoryId;
    ProgressBar progressBar;
    PostListModified posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_item);
        Intent i = getIntent();
        setTitle(i.getStringExtra("CategoryName"));
        categoryId = i.getIntExtra("CategoryId", 0);
        progressBar = findViewById(R.id.progressBar);
        getPosts(new RetrofitCallBack() {
            @Override
            public void onComplete() {
                loadPosts();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure() {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    private void getPosts(RetrofitCallBack callBack) {
        Call<PostListModified> blogRequest = RetrofitBuilder.BuildRetrofit.build().getPosts(categoryId);

        blogRequest.enqueue(new Callback<PostListModified>() {
            @Override
            public void onResponse(Call<PostListModified> call, Response<PostListModified> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(BlogItemActivity.this, "Some error occured!!", Toast.LENGTH_SHORT).show();
                    callBack.onFailure();
                    return;
                }
                posts = response.body();
                callBack.onComplete();
            }

            @Override
            public void onFailure(Call<PostListModified> call, Throwable t) {
                Toast.makeText(BlogItemActivity.this, "Some error occured!!", Toast.LENGTH_SHORT).show();
                callBack.onFailure();
            }
        });


    }

    private void loadPosts() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        BlogListAdapter blogListAdapter = new BlogListAdapter(posts.getPosts(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(blogListAdapter);
        TextView categoryDescription = findViewById(R.id.CategoryDescription);
        categoryDescription.setText(posts.getDescription());

        blogListAdapter.SetOnClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent i = new Intent(BlogItemActivity.this,BlogDetailActivity.class);
                i.putExtra("blogID",posts.getPosts().get(position).getId());
                startActivity(i);
            }
        });
    }


}