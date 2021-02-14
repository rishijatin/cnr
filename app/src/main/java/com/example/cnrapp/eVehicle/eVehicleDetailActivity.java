package com.example.cnrapp.eVehicle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.cnrapp.R;
import com.example.cnrapp.adapters.SliderAdapter;
import com.example.cnrapp.api.RetrofitBuilder;
import com.example.cnrapp.callbacks.RetrofitCallBack;
import com.example.cnrapp.models.eVehicleDetail;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class eVehicleDetailActivity extends AppCompatActivity {

    private int id ;
    private eVehicleDetail eVehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_vehicle_detail);
        getSupportActionBar().setTitle(getIntent().getStringExtra("Name"));
        id = getIntent().getIntExtra("Id",0);

        getDetails(new RetrofitCallBack() {
            @Override
            public void onComplete() {
                loadDetails();
            }

            @Override
            public void onFailure() {

            }
        });

    }

    private void getDetails(RetrofitCallBack callBack)
    {
        Call<eVehicleDetail> call = RetrofitBuilder.BuildRetrofit.build().getVehicleDetail(id);

        call.enqueue(new Callback<eVehicleDetail>() {
            @Override
            public void onResponse(Call<eVehicleDetail> call, Response<eVehicleDetail> response) {
                if(!response.isSuccessful())
                {
                    callBack.onFailure();
                    return;
                }

                eVehicle = response.body();
                callBack.onComplete();

            }

            @Override
            public void onFailure(Call<eVehicleDetail> call, Throwable t) {
                callBack.onFailure();
            }
        });

    }

    private void loadDetails()
    {
        SliderView sliderView = findViewById(R.id.sliderView);
        sliderView.setVisibility(View.VISIBLE);

        sliderView.setSliderAdapter(new SliderAdapter(this,eVehicle.getPhotos()));
        sliderView.setIndicatorAnimation(IndicatorAnimationType.DROP);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);

        ImageView youtubeLink = findViewById(R.id.youtubeLink);
        youtubeLink.setVisibility(View.VISIBLE);

        youtubeLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(eVehicle.getVideoUrl());
                Intent i= new Intent(Intent.ACTION_VIEW,uri);
                if(i.resolveActivity(getPackageManager())!=null)
                {
                    startActivity(i);
                }
            }
        });

        TextView descriptionHead = findViewById(R.id.descriptionHead);
        descriptionHead.setVisibility(View.VISIBLE);

        TextView descriptionText = findViewById(R.id.descriptionText);
        descriptionText.setVisibility(View.VISIBLE);
        descriptionText.setText(eVehicle.getDescription());

        TextView servicesHead = findViewById(R.id.servicesHead);
        servicesHead.setVisibility(View.VISIBLE);

        TextView servicesText = findViewById(R.id.servicesText);
        servicesText.setVisibility(View.VISIBLE);
        servicesText.setText(eVehicle.getServices());

        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);






    }


}