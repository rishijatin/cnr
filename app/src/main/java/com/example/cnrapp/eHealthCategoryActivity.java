package com.example.cnrapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cnrapp.adapters.eHealthCategoryAdapter;
import com.example.cnrapp.api.RetrofitBuilder;
import com.example.cnrapp.callbacks.RecyclerViewItemClickListener;
import com.example.cnrapp.callbacks.RetrofitCallBack;
import com.example.cnrapp.models.eHealthCategory;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class eHealthCategoryActivity extends AppCompatActivity {

    int categoryId;
    List<eHealthCategory> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_health_category);
        getSupportActionBar().setTitle(getIntent().getStringExtra("CategoryName"));
        categoryId= getIntent().getIntExtra("CategoryId",0);
        getCategories(new RetrofitCallBack() {
            @Override
            public void onComplete() {
                loadCategories();
            }

            @Override
            public void onFailure() {
                Toast.makeText(eHealthCategoryActivity.this,"Something went wrong!!!",Toast.LENGTH_SHORT).show();
            }
        });


    }

    void getCategories(RetrofitCallBack callBack)
    {
        Call<List<eHealthCategory>> categoryCall = RetrofitBuilder.BuildRetrofit.build().getHealthCategories();

        categoryCall.enqueue(new Callback<List<eHealthCategory>>() {
            @Override
            public void onResponse(Call<List<eHealthCategory>> call, Response<List<eHealthCategory>> response) {
                if(!response.isSuccessful())
                {
                    Toast.makeText(eHealthCategoryActivity.this,"Some error occured",Toast.LENGTH_SHORT).show();
                    callBack.onFailure();
                    return;
                }

                categories=response.body();
                callBack.onComplete();

            }

            @Override
            public void onFailure(Call<List<eHealthCategory>> call, Throwable t) {
                callBack.onFailure();
            }
        });

    }

    void loadCategories()
    {
        RecyclerView recyclerView= findViewById(R.id.recyclerView);
        TextView description = findViewById(R.id.Description);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        eHealthCategoryAdapter adapter = new eHealthCategoryAdapter(categories);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);

        adapter.setClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onClick(int position) {
                int id = categories.get(position).getId();
                String name = categories.get(position).getName();
                Intent i = new Intent(eHealthCategoryActivity.this,DoctorListActivity.class);
                i.putExtra("CategoryName",name);
                i.putExtra("CategoryId",id);
                startActivity(i);
            }
        });



    }


}