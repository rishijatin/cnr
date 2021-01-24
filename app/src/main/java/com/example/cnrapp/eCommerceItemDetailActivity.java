package com.example.cnrapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
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
import com.example.cnrapp.models.eCommerceItemDetail;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class eCommerceItemDetailActivity extends AppCompatActivity {

    int itemId;
    eCommerceItemDetail item;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_commerce_item_detail);
        itemId = getIntent().getIntExtra("itemId", 0);


        progressBar=findViewById(R.id.progressBar);

        getItem(new RetrofitCallBack() {
            @Override
            public void onComplete() {
                loadItem();
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure() {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    private void getItem(RetrofitCallBack callBack) {
        Call<eCommerceItemDetail> call = RetrofitBuilder.BuildRetrofit.build().getEcommerceItemDetail(itemId);
        call.enqueue(new Callback<eCommerceItemDetail>() {
            @Override
            public void onResponse(Call<eCommerceItemDetail> call, Response<eCommerceItemDetail> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(eCommerceItemDetailActivity.this, "Some error occured!!", Toast.LENGTH_SHORT).show();
                    callBack.onFailure();
                    return;
                }
                item = response.body();
                callBack.onComplete();
            }

            @Override
            public void onFailure(Call<eCommerceItemDetail> call, Throwable t) {
                Toast.makeText(eCommerceItemDetailActivity.this, "Some error occured!!", Toast.LENGTH_SHORT).show();
                callBack.onFailure();
            }
        });
    }

    private void loadItem()
    {
        TextView heading = findViewById(R.id.heading);
        ImageView image = findViewById(R.id.image);
        TextView price = findViewById(R.id.PriceText);
        TextView description= findViewById(R.id.description);
        TextView knowMore = findViewById(R.id.knowMore);
        Button contactUs = findViewById(R.id.contactUsButton);
        Button websiteLink = findViewById(R.id.websiteButton);
        websiteLink.setVisibility(View.VISIBLE);
        contactUs.setVisibility(View.VISIBLE);
        RequestOptions options = new RequestOptions().centerCrop()
                .placeholder(R.drawable.ic_baseline_error_outline_24)
                .error(R.drawable.ic_baseline_error_outline_24);

        heading.setText(item.getName());
        price.setText("Rs. "+item.getPrice());
        description.setText(item.getDescription());
        knowMore.setText("Have a question?");

        contactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO Make Transition
            }
        });

        websiteLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(item.getWebURL());
                Intent i= new Intent(Intent.ACTION_VIEW,uri);
                if(i.resolveActivity(getPackageManager())!=null)
                {
                    startActivity(i);
                }
            }
        });

        Glide.with(this).load(item.getImageUrl()).apply(options).into(image);





    }

}