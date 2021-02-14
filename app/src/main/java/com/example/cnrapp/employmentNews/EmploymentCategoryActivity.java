package com.example.cnrapp.employmentNews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.cnrapp.R;
import com.example.cnrapp.adapters.EmploymentCategoryAdapter;
import com.example.cnrapp.api.RetrofitBuilder;
import com.example.cnrapp.callbacks.RecyclerViewItemClickListener;
import com.example.cnrapp.callbacks.RetrofitCallBack;
import com.example.cnrapp.models.EmploymentCategory;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmploymentCategoryActivity extends AppCompatActivity {

    List<EmploymentCategory> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employment_category);
        getSupportActionBar().setTitle(getIntent().getStringExtra("CategoryName"));

        getCategories(new RetrofitCallBack() {
            @Override
            public void onComplete() {
                loadCategories();
            }

            @Override
            public void onFailure() {

            }
        });

    }

    private void getCategories(RetrofitCallBack callBack)
    {
        Call<List<EmploymentCategory>> call = RetrofitBuilder.BuildRetrofit.build().getEmploymentCategories();

        call.enqueue(new Callback<List<EmploymentCategory>>() {
            @Override
            public void onResponse(Call<List<EmploymentCategory>> call, Response<List<EmploymentCategory>> response) {
                if(!response.isSuccessful())
                {
                    callBack.onFailure();
                    return;
                }

                categories = response.body();
                callBack.onComplete();

            }

            @Override
            public void onFailure(Call<List<EmploymentCategory>> call, Throwable t) {
                callBack.onFailure();
            }
        });

    }

    private void loadCategories()
    {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        EmploymentCategoryAdapter adapter = new EmploymentCategoryAdapter(categories);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(dividerItemDecoration);

        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        adapter.setClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onClick(int position) {
                int id = categories.get(position).getId();
                String name = categories.get(position).getName();

                Intent i = new Intent(EmploymentCategoryActivity.this,EmploymentListActivity.class);
                i.putExtra("Name",name);
                i.putExtra("Id",id);
                startActivity(i);


            }
        });


    }


}