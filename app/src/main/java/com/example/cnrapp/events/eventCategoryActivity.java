package com.example.cnrapp.events;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cnrapp.R;
import com.example.cnrapp.adapters.EventsCategoriesAdapter;
import com.example.cnrapp.adapters.eCommerceListAdapter;
import com.example.cnrapp.api.RetrofitBuilder;
import com.example.cnrapp.callbacks.RecyclerViewItemClickListener;
import com.example.cnrapp.callbacks.RetrofitCallBack;
import com.example.cnrapp.models.EventCategory;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class eventCategoryActivity extends AppCompatActivity {

    List<EventCategory> events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_category);
        getSupportActionBar().setTitle(getIntent().getStringExtra("CategoryName"));

        getCategory(new RetrofitCallBack() {
            @Override
            public void onComplete() {
                loadCategory();
            }

            @Override
            public void onFailure() {

            }
        });


    }

    private void getCategory(RetrofitCallBack callBack) {
        Call<List<EventCategory>> call = RetrofitBuilder.BuildRetrofit.build().getEvents();

        call.enqueue(new Callback<List<EventCategory>>() {
            @Override
            public void onResponse(Call<List<EventCategory>> call, Response<List<EventCategory>> response) {
                if (!response.isSuccessful()) {
                    callBack.onFailure();
                    return;
                }

                events = response.body();
                callBack.onComplete();

            }

            @Override
            public void onFailure(Call<List<EventCategory>> call, Throwable t) {
                callBack.onFailure();
            }
        });
    }

    private void loadCategory()
    {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        EventsCategoriesAdapter adapter =  new EventsCategoriesAdapter(events);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(dividerItemDecoration);

        adapter.setClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onClick(int position) {
                EventCategory event = events.get(position);
                Intent i = new Intent(eventCategoryActivity.this,eventListActivity.class);
                i.putExtra("Name",event.getName());
                i.putExtra("Id",event.getId());
                startActivity(i);
            }
        });

    }

}