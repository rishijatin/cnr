package com.example.cnrapp.eVehicle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.cnrapp.R;
import com.example.cnrapp.adapters.VehicleCategoryAdapter;
import com.example.cnrapp.api.RetrofitBuilder;
import com.example.cnrapp.callbacks.RecyclerViewItemClickListener;
import com.example.cnrapp.callbacks.RetrofitCallBack;
import com.example.cnrapp.models.eVehicleCategory;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class eVehicleCategoryActivity extends AppCompatActivity {

    List<eVehicleCategory> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_vehicle_category);
        getSupportActionBar().setTitle(getIntent().getStringExtra("CategoryName"));

        getCategory(new RetrofitCallBack() {
            @Override
            public void onComplete() {
                loadCategories();
            }

            @Override
            public void onFailure() {

            }
        });

    }

    private void getCategory(RetrofitCallBack callBack) {
        Call<List<eVehicleCategory>> call = RetrofitBuilder.BuildRetrofit.build().getVehicleCategories();

        call.enqueue(new Callback<List<eVehicleCategory>>() {
            @Override
            public void onResponse(Call<List<eVehicleCategory>> call, Response<List<eVehicleCategory>> response) {
                if (!response.isSuccessful()) {
                    callBack.onFailure();
                    return;
                }

                categories = response.body();
                callBack.onComplete();

            }

            @Override
            public void onFailure(Call<List<eVehicleCategory>> call, Throwable t) {
                callBack.onFailure();
            }
        });
    }

    private void loadCategories() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        VehicleCategoryAdapter adapter = new VehicleCategoryAdapter(categories);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(dividerItemDecoration);

        adapter.setClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onClick(int position) {
                eVehicleCategory category = categories.get(position);
                int id = category.getId();
                String name = category.getName();

                Intent i = new Intent(eVehicleCategoryActivity.this, eVehicleDetailActivity.class);
                i.putExtra("Id", id);
                i.putExtra("Name", name);
                startActivity(i);

            }
        });

    }

}