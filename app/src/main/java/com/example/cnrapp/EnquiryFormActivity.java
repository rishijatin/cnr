package com.example.cnrapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cnrapp.api.RetrofitBuilder;
import com.example.cnrapp.callbacks.RetrofitCallBack;
import com.example.cnrapp.models.ContactInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.ConcurrentHashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnquiryFormActivity extends AppCompatActivity {

    private ContactInfo contact;

    private EditText nameEdit;
    private EditText emailEdit;
    private EditText mobileEdit;
    private EditText enquiryEdit;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Queries");
    private ConstraintLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiry_form);
        getSupportActionBar().setTitle("Enquiry Form");



        getContact(new RetrofitCallBack() {
            @Override
            public void onComplete() {
                setClickListeners();
                setInputFormElements();
            }

            @Override
            public void onFailure() {

            }
        });

    }

    void getContact(RetrofitCallBack callBack)
    {
        Call<ContactInfo> call = RetrofitBuilder.BuildRetrofit.build().getContact();
        call.enqueue(new Callback<ContactInfo>() {
            @Override
            public void onResponse(Call<ContactInfo> call, Response<ContactInfo> response) {
                if(!response.isSuccessful())
                {
                    Toast.makeText(EnquiryFormActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                    callBack.onFailure();
                    return;
                }
                contact=response.body();
                callBack.onComplete();

            }

            @Override
            public void onFailure(Call<ContactInfo> call, Throwable t) {
                Toast.makeText(EnquiryFormActivity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                callBack.onFailure();
                return;
            }
        });
    }


    void setInputFormElements() {
        nameEdit = findViewById(R.id.nameEdit);
        emailEdit = findViewById(R.id.emailEdit);
        mobileEdit = findViewById(R.id.editContact);
        enquiryEdit = findViewById(R.id.editEnquiry);


        Button send = findViewById(R.id.button);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateForm() == true) {


                    DatabaseReference newQuery = mDatabase.push();
                    newQuery.child("Timestamp").setValue(System.currentTimeMillis());
                    newQuery.child("Name").setValue(nameEdit.getText().toString());
                    newQuery.child("Email").setValue(emailEdit.getText().toString());
                    newQuery.child("Phone Number").setValue(mobileEdit.getText().toString());
                    newQuery.child("Enquiry").setValue(enquiryEdit.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            emailEdit.setText("");
                            mobileEdit.setText("");
                            nameEdit.setText("");
                            enquiryEdit.setText("");
                            Toast.makeText(EnquiryFormActivity.this, "Submitted Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(EnquiryFormActivity.this, "Some error occurred", Toast.LENGTH_SHORT).show();
                                }
                            });

                }

            }
        });


    }


    private boolean validateForm() {
        if (nameEdit.getText().length() == 0) {
            nameEdit.setError("Empty field");
            return false;
        } else if (nameEdit.getText().length() == 0) {
            emailEdit.setError("Empty field");
            return false;
        } else if (mobileEdit.getText().length() == 0) {
            mobileEdit.setError("Empty field");
            return false;
        } else if (enquiryEdit.getText().length() == 0) {
            enquiryEdit.setError("Empty field");
            return false;
        }
        return true;
    }


    void setClickListeners() {
        TextView emailText= findViewById(R.id.emailText);
        TextView emailHead = findViewById(R.id.textView12);
        TextView phoneNo2 = findViewById(R.id.phoneNumber2);
        TextView phoneNo1 = findViewById(R.id.phoneNumber1);
        TextView phoneHead = findViewById(R.id.textView9);
        TextView addressHead = findViewById(R.id.addressHead);
        TextView addressText = findViewById(R.id.addressText);

        emailHead.setVisibility(View.VISIBLE);
        emailText.setVisibility(View.VISIBLE);
        phoneNo1.setVisibility(View.VISIBLE);
        phoneNo2.setVisibility(View.VISIBLE);
        phoneHead.setVisibility(View.VISIBLE);
        addressHead.setVisibility(View.VISIBLE);
        addressText.setVisibility(View.VISIBLE);


        phoneNo1.setText(Html.fromHtml("<u>"+contact.getPhoneNo1()+"</u>"));
        phoneNo2.setText(Html.fromHtml("<u>"+contact.getPhoneNo2()+"</u>"));
        emailText.setText(Html.fromHtml("<u>"+contact.getEmail()+"</u>"));
        addressText.setText(contact.getAddress());

        phoneNo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:" + phoneNo1.getText().toString()));
                if (i.resolveActivity(getPackageManager()) != null) {
                    startActivity(i);
                }
            }
        });

        phoneNo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:" + phoneNo2.getText().toString()));
                if (i.resolveActivity(getPackageManager()) != null) {
                    startActivity(i);
                }
            }
        });

        emailText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SENDTO);
                i.setData(Uri.parse("mailto:" + emailText.getText().toString()));
                if (i.resolveActivity(getPackageManager()) != null) {
                    startActivity(i);
                }
            }
        });
    }
}