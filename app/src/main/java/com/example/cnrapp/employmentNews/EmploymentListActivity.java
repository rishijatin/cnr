package com.example.cnrapp.employmentNews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.cnrapp.R;
import com.example.cnrapp.adapters.EmploymentListAdapter;
import com.example.cnrapp.api.RetrofitBuilder;
import com.example.cnrapp.callbacks.RecyclerViewItemClickListener;
import com.example.cnrapp.callbacks.RetrofitCallBack;
import com.example.cnrapp.models.EmploymentList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmploymentListActivity extends AppCompatActivity {
    private int id;
    List<EmploymentList> employmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employment_list);
        getSupportActionBar().setTitle(getIntent().getStringExtra("Name"));
        id= getIntent().getIntExtra("Id",0);

        getEmploymentList(new RetrofitCallBack() {
            @Override
            public void onComplete() {
                loadEmploymentList();
            }

            @Override
            public void onFailure() {

            }
        });

    }

    private void getEmploymentList(RetrofitCallBack callBack)
    {
        Call<List<EmploymentList>> call = RetrofitBuilder.BuildRetrofit.build().getEmploymentList(id);

        call.enqueue(new Callback<List<EmploymentList>>() {
            @Override
            public void onResponse(Call<List<EmploymentList>> call, Response<List<EmploymentList>> response) {
                if(!response.isSuccessful())
                {

                    callBack.onFailure();
                    return;
                }

                employmentList=response.body();
                callBack.onComplete();

            }

            @Override
            public void onFailure(Call<List<EmploymentList>> call, Throwable t) {
                callBack.onFailure();
            }
        });
    }

    private void loadEmploymentList()
    {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        EmploymentListAdapter adapter = new EmploymentListAdapter(employmentList);
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
                int id = employmentList.get(position).getId();

                Intent i = new Intent(EmploymentListActivity.this,EmploymentDetailActivity.class);
                i.putExtra("Id",id);
                startActivity(i);


            }
        });

    }

}