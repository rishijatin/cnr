package com.example.cnrapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cnrapp.adapters.CategoryAdapter;
import com.example.cnrapp.api.RetrofitBuilder;
import com.example.cnrapp.callbacks.RetrofitCallBack;
import com.example.cnrapp.eVehicle.eVehicleCategoryActivity;
import com.example.cnrapp.employmentNews.EmploymentCategoryActivity;
import com.example.cnrapp.events.eventCategoryActivity;
import com.example.cnrapp.models.Category;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CategoryActivity extends AppCompatActivity {


    List<Category> categories = new ArrayList<Category>();
    ProgressBar progressBar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.person:

                Intent i = new Intent(CategoryActivity.this, AboutPersonActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        getSupportActionBar().setTitle("Categories");
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe);
        progressBar = findViewById(R.id.progressBar);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCategories(new RetrofitCallBack() {
                    @Override
                    public void onComplete() {
                        loadGrid();
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onFailure() {
                        progressBar.setVisibility(View.GONE);
                        swipeRefreshLayout.setRefreshing(false);

                    }
                });


            }
        });

        getCategories(new RetrofitCallBack() {
            @Override
            public void onComplete() {
                loadGrid();
            }

            @Override
            public void onFailure() {
                progressBar.setVisibility(View.GONE);

            }
        });


    }

    void getCategories(RetrofitCallBack mListener) {
        Call<List<Category>> categoriesList = RetrofitBuilder.BuildRetrofit.build().getCategories();

        categoriesList.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(CategoryActivity.this, "Some Error occured!!!", Toast.LENGTH_SHORT).show();
                    return;
                }

                categories = response.body();
                mListener.onComplete();
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                mListener.onFailure();
            }
        });
    }


    private void loadGrid() {
        progressBar.setVisibility(View.GONE);
        GridView gridView = findViewById(R.id.GridView);
        CategoryAdapter categoryAdapter = new CategoryAdapter(CategoryActivity.this, categories);
        gridView.setAdapter(categoryAdapter);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setVisibility(View.VISIBLE);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CategoryActivity.this, EnquiryFormActivity.class));
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i;
                if (categories.get(position).getId() == 5) {
                    i = new Intent(CategoryActivity.this, eCommerceActivity.class);
                } else if (categories.get(position).getId() == 3) {
                    i = new Intent(CategoryActivity.this, eHealthCategoryActivity.class);
                } else if (categories.get(position).getId() == 6) {
                    i = new Intent(CategoryActivity.this, eventCategoryActivity.class);
                } else if (categories.get(position).getId() == 4) {
                    i = new Intent(CategoryActivity.this, eVehicleCategoryActivity.class);
                } else if (categories.get(position).getId() == 2) {
                    i = new Intent(CategoryActivity.this, EmploymentCategoryActivity.class);
                } else {
                    i = new Intent(CategoryActivity.this, BlogItemActivity.class);
                }

                i.putExtra("CategoryName", categories.get(position).getName());
                i.putExtra("CategoryId", categories.get(position).getId());
                startActivity(i);
            }
        });
    }


}