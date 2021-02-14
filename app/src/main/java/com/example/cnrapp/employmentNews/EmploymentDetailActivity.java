package com.example.cnrapp.employmentNews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cnrapp.R;
import com.example.cnrapp.adapters.SliderAdapter;
import com.example.cnrapp.api.RetrofitBuilder;
import com.example.cnrapp.callbacks.RetrofitCallBack;
import com.example.cnrapp.models.EmploymentDetails;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmploymentDetailActivity extends AppCompatActivity {
    private int id;
    EmploymentDetails employmentDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employment_detail);
        getSupportActionBar().setTitle("");

        id = getIntent().getIntExtra("Id",0);
        getEmploymentDetail(new RetrofitCallBack() {
            @Override
            public void onComplete() {
                loadEmploymentDetail();
            }

            @Override
            public void onFailure() {

            }
        });


    }

    private void getEmploymentDetail(RetrofitCallBack callBack)
    {
        Call<EmploymentDetails> call = RetrofitBuilder.BuildRetrofit.build().getEmploymentDetails(id);

        call.enqueue(new Callback<EmploymentDetails>() {
            @Override
            public void onResponse(Call<EmploymentDetails> call, Response<EmploymentDetails> response) {
                if(!response.isSuccessful())
                {
                    callBack.onFailure();
                    return;
                }

                employmentDetails = response.body();
                callBack.onComplete();

            }

            @Override
            public void onFailure(Call<EmploymentDetails> call, Throwable t) {
                Log.i("Jatin",t.getMessage());
                callBack.onFailure();
            }
        });
    }

    private void loadEmploymentDetail()
    {
        TextView heading = findViewById(R.id.heading);
        heading.setVisibility(View.VISIBLE);
        heading.setText(employmentDetails.getHeading());

        TextView jobDescriptionHead = findViewById(R.id.jobDescriptionHead);
        jobDescriptionHead.setVisibility(View.VISIBLE);

        TextView jobDescriptionText = findViewById(R.id.jobDescriptionText);
        jobDescriptionText.setVisibility(View.VISIBLE);
        jobDescriptionText.setText(employmentDetails.getDescription());

        TextView jobLocationHead = findViewById(R.id.jobLocationHead);
        jobLocationHead.setVisibility(View.VISIBLE);

        TextView jobLocationText = findViewById(R.id.jobLocationText);
        jobLocationText.setVisibility(View.VISIBLE);
        jobLocationText.setText(employmentDetails.getLocation());

        TextView jobDetailsHead = findViewById(R.id.jobDetailsHead);
        jobDetailsHead.setVisibility(View.VISIBLE);

        TextView jobDetailsText = findViewById(R.id.jobDetailsText);
        jobDetailsText.setVisibility(View.VISIBLE);
        jobDetailsText.setText(employmentDetails.getDetails());

        if(employmentDetails.getVideoUrl()!=null)
        {
            ImageView youtubeLink = findViewById(R.id.youtubeLink);
            youtubeLink.setVisibility(View.VISIBLE);
            youtubeLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse(employmentDetails.getVideoUrl());
                    Intent i= new Intent(Intent.ACTION_VIEW,uri);
                    if(i.resolveActivity(getPackageManager())!=null)
                    {
                        startActivity(i);
                    }
                }
            });

        }
        else
        {
            ImageView youtubeLink = findViewById(R.id.youtubeLink);
            youtubeLink.setVisibility(View.GONE);
        }

        if(employmentDetails.getPhotos().size()>0)
        {
            SliderView sliderView = findViewById(R.id.sliderView);
            sliderView.setVisibility(View.VISIBLE);
            sliderView.setSliderAdapter(new SliderAdapter(this,employmentDetails.getPhotos()));
            sliderView.setIndicatorAnimation(IndicatorAnimationType.DROP);
            sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        }
        else
        {

            SliderView sliderView = findViewById(R.id.sliderView);
            sliderView.setVisibility(View.GONE);

        }


    }

}