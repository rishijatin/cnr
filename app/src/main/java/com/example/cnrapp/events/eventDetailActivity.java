package com.example.cnrapp.events;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.cnrapp.R;
import com.example.cnrapp.adapters.SliderAdapter;
import com.example.cnrapp.api.RetrofitBuilder;
import com.example.cnrapp.callbacks.RetrofitCallBack;
import com.example.cnrapp.models.EventDetail;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class eventDetailActivity extends AppCompatActivity {
    private int id;
    private EventDetail event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        getSupportActionBar().setTitle("");
        id = getIntent().getIntExtra("Id", 0);

        getEvent(new RetrofitCallBack() {
            @Override
            public void onComplete() {
                loadEvent();
            }

            @Override
            public void onFailure() {

            }
        });

    }

    void getEvent(RetrofitCallBack callBack) {
        Call<EventDetail> call = RetrofitBuilder.BuildRetrofit.build().getEvent(id);
        call.enqueue(new Callback<EventDetail>() {
            @Override
            public void onResponse(Call<EventDetail> call, Response<EventDetail> response) {
                if (!response.isSuccessful()) {
                    callBack.onFailure();
                    return;
                }

                event = response.body();
                callBack.onComplete();
            }

            @Override
            public void onFailure(Call<EventDetail> call, Throwable t) {
                callBack.onFailure();
            }
        });
    }

    void loadEvent() {
        TextView heading = findViewById(R.id.heading);
        heading.setVisibility(View.VISIBLE);
        heading.setText(event.getName());
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        ConstraintLayout layout = findViewById(R.id.invitationCard);
        if (event.getInvitationCard() != null) {
            layout.setVisibility(View.VISIBLE);
            ImageView cardImage = findViewById(R.id.cardImage);
            cardImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse(event.getInvitationCard());
                    Intent i= new Intent(Intent.ACTION_VIEW,uri);
                    if(i.resolveActivity(getPackageManager())!=null)
                    {
                        startActivity(i);
                    }
                }
            });
            Glide.with(getApplicationContext()).load(event.getInvitationCard()).into(cardImage);

        }
        else
        {
            layout.setVisibility(View.GONE);
            ImageView cardImage = findViewById(R.id.cardImage);
            cardImage.setVisibility(View.GONE);

        }

        ImageView youTubeLink = findViewById(R.id.youtubeLogo);
        if(event.getVideoUrl()!=null)
        {
            youTubeLink.setVisibility(View.VISIBLE);
            youTubeLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse(event.getVideoUrl());
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
            youTubeLink.setVisibility(View.GONE);
        }

        SliderView sliderView = findViewById(R.id.sliderView);
        if(event.getPhotos().size()>0)
        {
            sliderView.setVisibility(View.VISIBLE);
            sliderView.setSliderAdapter(new SliderAdapter(this,event.getPhotos()));
            sliderView.setIndicatorAnimation(IndicatorAnimationType.DROP);
            sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        }
        else
        {
            sliderView.setVisibility(View.GONE);
        }


    }


}