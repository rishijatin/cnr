package com.example.cnrapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.ConcurrentHashMap;

public class EnquiryFormActivity extends AppCompatActivity {

    private final String phoneNo1 = "<u>+91-8333899956</u>";
    private final String phoneNo2 = "<u>+91-8121166999</u>";
    private final String email = "<u>cnr@innohub.technology</u>";
    private EditText nameEdit;
    private EditText emailEdit;
    private EditText mobileEdit;
    private EditText enquiryEdit;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Queries");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquiry_form);
        getSupportActionBar().setTitle("Enquiry Form");

        setClickListeners();
        setInputFormElements();


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
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(EnquiryFormActivity.this, "Some error occured", Toast.LENGTH_SHORT).show();
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
        TextView phoneNumber1 = findViewById(R.id.phoneNumber1);
        TextView phoneNumber2 = findViewById(R.id.phoneNumber2);
        TextView emailAddress = findViewById(R.id.emailText);
        phoneNumber1.setText(Html.fromHtml(phoneNo1));
        phoneNumber2.setText(Html.fromHtml(phoneNo2));
        emailAddress.setText(Html.fromHtml(email));

        phoneNumber1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:" + phoneNumber1.getText().toString()));
                if (i.resolveActivity(getPackageManager()) != null) {
                    startActivity(i);
                }
            }
        });

        phoneNumber2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:" + phoneNumber2.getText().toString()));
                if (i.resolveActivity(getPackageManager()) != null) {
                    startActivity(i);
                }
            }
        });

        emailAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SENDTO);
                i.setData(Uri.parse("mailto:" + emailAddress.getText().toString()));
                if (i.resolveActivity(getPackageManager()) != null) {
                    startActivity(i);
                }
            }
        });
    }
}