package com.example.cnrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cnrapp.adapters.DoctorListAdapter;
import com.example.cnrapp.api.RetrofitBuilder;
import com.example.cnrapp.callbacks.RetrofitCallBack;
import com.example.cnrapp.models.DoctorList;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorListActivity extends AppCompatActivity {
    private int categoryId;
    private List<DoctorList> doctors;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);
        getSupportActionBar().setTitle(getIntent().getStringExtra("CategoryName"));
        categoryId= getIntent().getIntExtra("CategoryId",0);

        getDoctors(new RetrofitCallBack() {
            @Override
            public void onComplete() {
                loadDoctors();
            }

            @Override
            public void onFailure() {

            }
        });

    }


    private void getDoctors(RetrofitCallBack callBack)
    {
        Call<List<DoctorList>> call = RetrofitBuilder.BuildRetrofit.build().getDoctors(categoryId);
        call.enqueue(new Callback<List<DoctorList>>() {
            @Override
            public void onResponse(Call<List<DoctorList>> call, Response<List<DoctorList>> response) {
                if(!response.isSuccessful())
                {
                    Toast.makeText(DoctorListActivity.this,"Some error occurred",Toast.LENGTH_SHORT).show();
                    callBack.onFailure();
                    return;
                }

                doctors = response.body();
                callBack.onComplete();

            }

            @Override
            public void onFailure(Call<List<DoctorList>> call, Throwable t) {
                Toast.makeText(DoctorListActivity.this,"Some error occurred",Toast.LENGTH_SHORT).show();
                callBack.onFailure();
            }
        });
    }

    private void loadDoctors()
    {
        GridView gridView = findViewById(R.id.GridView);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        DoctorListAdapter doctorListAdapter = new DoctorListAdapter(this,doctors);
        gridView.setAdapter(doctorListAdapter);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int doctorId = doctors.get(position).getId();
                String doctorName= doctors.get(position).getName();
                Intent i = new Intent(DoctorListActivity.this,DoctorDetailActivity.class);
                i.putExtra("doctorId",doctorId);
                i.putExtra("doctorName",doctorName);
                startActivity(i);
            }
        });

    }

}