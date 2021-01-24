package com.example.cnrapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cnrapp.adapters.eCommerceItemAdapter;
import com.example.cnrapp.api.RetrofitBuilder;
import com.example.cnrapp.callbacks.RecyclerViewItemClickListener;
import com.example.cnrapp.callbacks.RetrofitCallBack;
import com.example.cnrapp.models.eCommerceItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class eCommerceItemActivity extends AppCompatActivity {

    int categoryId;
    ProgressBar progressBar;
    List<eCommerceItem> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_commerce_item);
        getSupportActionBar().setTitle(getIntent().getStringExtra("categoryName"));
        categoryId = getIntent().getIntExtra("categoryID", 0);

        progressBar = findViewById(R.id.progressBar);
        getItem(new RetrofitCallBack() {
            @Override
            public void onComplete() {
                loadItems();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure() {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    private void getItem(RetrofitCallBack callBack) {
        Call<List<eCommerceItem>> itemRequest = RetrofitBuilder.BuildRetrofit.build().getEcommerceItem(categoryId);

        itemRequest.enqueue(new Callback<List<eCommerceItem>>() {
            @Override
            public void onResponse(Call<List<eCommerceItem>> call, Response<List<eCommerceItem>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(eCommerceItemActivity.this, "Some error occured!!", Toast.LENGTH_SHORT).show();
                    callBack.onFailure();
                    return;
                }

                items = response.body();
                callBack.onComplete();

            }

            @Override
            public void onFailure(Call<List<eCommerceItem>> call, Throwable t) {
                Toast.makeText(eCommerceItemActivity.this, "Some error occured!!", Toast.LENGTH_SHORT).show();
                callBack.onFailure();
            }
        });

    }

    private void loadItems()
    {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        eCommerceItemAdapter adapter = new eCommerceItemAdapter(items,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        adapter.SetOnClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onClick(int position) {

                Intent i= new Intent(eCommerceItemActivity.this,eCommerceItemDetailActivity.class);
                i.putExtra("itemId",items.get(position).getId());
                startActivity(i);

            }
        });

    }



}