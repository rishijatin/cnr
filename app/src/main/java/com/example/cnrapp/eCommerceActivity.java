package com.example.cnrapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.cnrapp.adapters.eCommerceListAdapter;
import com.example.cnrapp.api.RetrofitBuilder;
import com.example.cnrapp.callbacks.RecyclerViewItemClickListener;
import com.example.cnrapp.callbacks.RetrofitCallBack;
import com.example.cnrapp.models.eCommerceCategory;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class eCommerceActivity extends AppCompatActivity {

    List<eCommerceCategory> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_commerce);
        getSupportActionBar().setTitle("e-Commerce");

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


    private void getCategories(RetrofitCallBack callBack) {
        Call<List<eCommerceCategory>> categoriesRequest = RetrofitBuilder.BuildRetrofit.build().getEcommerceCategories();

        categoriesRequest.enqueue(new Callback<List<eCommerceCategory>>() {
            @Override
            public void onResponse(Call<List<eCommerceCategory>> call, Response<List<eCommerceCategory>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(eCommerceActivity.this, "Some error occured!!", Toast.LENGTH_SHORT).show();
                    callBack.onFailure();
                    return;
                }

                categories = response.body();
                callBack.onComplete();
            }

            @Override
            public void onFailure(Call<List<eCommerceCategory>> call, Throwable t) {
                Toast.makeText(eCommerceActivity.this, "Some error occured!!", Toast.LENGTH_SHORT).show();

                callBack.onFailure();
            }
        });

    }

    private void loadCategories() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        eCommerceListAdapter adapter = new eCommerceListAdapter(categories);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(dividerItemDecoration);

        adapter.setClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent i = new Intent(eCommerceActivity.this, eCommerceItemActivity.class);
                i.putExtra("categoryName", categories.get(position).getName());
                i.putExtra("categoryID", categories.get(position).getId());
                startActivity(i);
            }
        });
    }

}