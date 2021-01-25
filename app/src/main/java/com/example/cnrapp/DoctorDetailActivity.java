package com.example.cnrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.cnrapp.api.RetrofitBuilder;
import com.example.cnrapp.callbacks.RetrofitCallBack;
import com.example.cnrapp.models.DoctorDetail;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorDetailActivity extends AppCompatActivity {

    private DoctorDetail doctor;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_detail);
        getSupportActionBar().setTitle(getIntent().getStringExtra("doctorName"));
        id = getIntent().getIntExtra("doctorId",0);
        getDoctor(new RetrofitCallBack() {
            @Override
            public void onComplete() {
                loadDoctor();
            }

            @Override
            public void onFailure() {

            }
        });

    }

    private void getDoctor(RetrofitCallBack callBack)
    {
        Call<DoctorDetail> call = RetrofitBuilder.BuildRetrofit.build().getDoctor(id);

        call.enqueue(new Callback<DoctorDetail>() {
            @Override
            public void onResponse(Call<DoctorDetail> call, Response<DoctorDetail> response) {
                if(!response.isSuccessful())
                {
                    Toast.makeText(DoctorDetailActivity.this,"Something went wrong!!!",Toast.LENGTH_SHORT).show();
                    callBack.onFailure();
                    return;
                }

                doctor=response.body();
                callBack.onComplete();

            }

            @Override
            public void onFailure(Call<DoctorDetail> call, Throwable t) {
                Toast.makeText(DoctorDetailActivity.this,"Something went wrong!!!",Toast.LENGTH_SHORT).show();
                callBack.onFailure();
            }
        });

    }

    private void loadDoctor()
    {
        TextView doctorName= findViewById(R.id.doctorName);
        TextView experience = findViewById(R.id.experienceEntry);
        TextView qualification = findViewById(R.id.qualificationEntry);
        TextView description = findViewById(R.id.doctorDescription);
        TextView experienceText = findViewById(R.id.experienceText);
        TextView qualificationText = findViewById(R.id.qualificationText);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        experienceText.setVisibility(View.VISIBLE);
        qualificationText.setVisibility(View.VISIBLE);


        ImageView doctorImage = findViewById(R.id.doctorImage);
        Button sendMailButton = findViewById(R.id.mailButton);
        sendMailButton.setVisibility(View.VISIBLE);

        doctorName.setText(doctor.getName());
        experience.setText(String.valueOf(doctor.getExperience()+" years"));
        qualification.setText(String.valueOf(doctor.getQualification()));
        description.setText(doctor.getDescription());

        RequestOptions options = new RequestOptions().centerCrop().
                placeholder(R.drawable.ic_baseline_error_outline_24).
                error(R.drawable.ic_baseline_error_outline_24);

        Glide.with(DoctorDetailActivity.this).load(doctor.getUrl()).apply(options).into(doctorImage);

        sendMailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"cnr@innohub.technology"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Appointment with "+doctor.getName());

                if(i.resolveActivity(getPackageManager())!=null)
                {
                    startActivity(i);
                }

            }
        });



    }

}