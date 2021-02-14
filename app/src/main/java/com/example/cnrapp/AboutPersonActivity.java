package com.example.cnrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cnrapp.adapters.SliderAdapter;
import com.example.cnrapp.api.RetrofitBuilder;
import com.example.cnrapp.callbacks.RetrofitCallBack;
import com.example.cnrapp.models.PersonModel;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutPersonActivity extends AppCompatActivity {

    PersonModel person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_person);
        getSupportActionBar().setTitle("About C.N.R");

        getPerson(new RetrofitCallBack() {
            @Override
            public void onComplete() {
                loadPerson();
            }

            @Override
            public void onFailure() {

            }
        });


    }


    private void loadPerson()
    {
        ImageView mainImage = findViewById(R.id.mainImage);
        mainImage.setVisibility(View.VISIBLE);
        TextView aboutHimHeading = findViewById(R.id.aboutHimHeading);
        aboutHimHeading.setVisibility(View.VISIBLE);
        TextView aboutHimText = findViewById(R.id.aboutHimText);
        aboutHimText.setVisibility(View.VISIBLE);
        TextView socialWorkHeading = findViewById(R.id.socialWorkHeading);
        socialWorkHeading.setVisibility(View.VISIBLE);
        TextView socialWorkText = findViewById(R.id.socialWorkText);
        socialWorkText.setVisibility(View.VISIBLE);
        SliderView sliderView = findViewById(R.id.sliderView);
        sliderView.setVisibility(View.VISIBLE);
        TextView personFamilyHeading = findViewById(R.id.personFamilyHeading);
        personFamilyHeading.setVisibility(View.VISIBLE);
        TextView personFamilyText = findViewById(R.id.personFamilyText);
        personFamilyText.setVisibility(View.VISIBLE);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        ImageView youtubeLink = findViewById(R.id.youtubeLink);

        if(person.getVideoUrl()!=null){
            youtubeLink.setVisibility(View.VISIBLE);
            youtubeLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse(person.getVideoUrl());
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
            youtubeLink.setVisibility(View.GONE);
        }


        aboutHimHeading.setText("About "+person.getName());
        aboutHimText.setText(person.getAboutPerson());
        socialWorkText.setText(person.getSocialWork());
        personFamilyText.setText(person.getFamilyMembers());

        sliderView.setSliderAdapter(new SliderAdapter(this,person.getPhotos()));
        sliderView.setIndicatorAnimation(IndicatorAnimationType.DROP);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);


    }



    private void getPerson(RetrofitCallBack callBack)
    {
        Call<PersonModel> call = RetrofitBuilder.BuildRetrofit.build().getPerson(1);

        call.enqueue(new Callback<PersonModel>() {
            @Override
            public void onResponse(Call<PersonModel> call, Response<PersonModel> response) {
                if(!response.isSuccessful())
                {
                    Toast.makeText(AboutPersonActivity.this,"Something went wrong!!!",Toast.LENGTH_SHORT).show();
                    callBack.onFailure();
                    return;
                }

                person=response.body();
                callBack.onComplete();


            }

            @Override
            public void onFailure(Call<PersonModel> call, Throwable t) {
                Toast.makeText(AboutPersonActivity.this,"Something went wrong!!!",Toast.LENGTH_SHORT).show();
                callBack.onFailure();
            }
        });

    }
}