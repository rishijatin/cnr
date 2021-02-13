package com.example.cnrapp.events;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.cnrapp.R;
import com.example.cnrapp.adapters.EventsListAdapter;
import com.example.cnrapp.api.RetrofitBuilder;
import com.example.cnrapp.callbacks.RecyclerViewItemClickListener;
import com.example.cnrapp.callbacks.RetrofitCallBack;
import com.example.cnrapp.models.FunctionList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class eventListActivity extends AppCompatActivity {

    private int id;
    List<FunctionList> functions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        getSupportActionBar().setTitle(getIntent().getStringExtra("Name"));
        id = getIntent().getIntExtra("Id",0);
        getFunctions(new RetrofitCallBack() {
            @Override
            public void onComplete() {
                loadFunctions();
            }

            @Override
            public void onFailure() {

            }
        });


    }

    void getFunctions(RetrofitCallBack callBack)
    {
        Call<List<FunctionList>> call = RetrofitBuilder.BuildRetrofit.build().getFunctions(id);

        call.enqueue(new Callback<List<FunctionList>>() {
            @Override
            public void onResponse(Call<List<FunctionList>> call, Response<List<FunctionList>> response) {
                if(!response.isSuccessful())
                {
                    callBack.onFailure();
                    return;
                }

                functions=response.body();
                callBack.onComplete();

            }

            @Override
            public void onFailure(Call<List<FunctionList>> call, Throwable t) {
                callBack.onFailure();
            }
        });

    }

    void loadFunctions()
    {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        EventsListAdapter adapter = new EventsListAdapter(functions);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                linearLayoutManager.getOrientation());
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(dividerItemDecoration);

        adapter.setClickListener(new RecyclerViewItemClickListener() {
            @Override
            public void onClick(int position) {
                FunctionList function = functions.get(position);
                Intent i = new Intent(eventListActivity.this,eventDetailActivity.class);
                i.putExtra("Id",function.getId());
                startActivity(i);
            }
        });


    }


}