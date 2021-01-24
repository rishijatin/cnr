package com.example.cnrapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.cnrapp.R;
import com.example.cnrapp.models.DoctorList;

import java.util.List;

public class DoctorListAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<DoctorList> doctors;

    public DoctorListAdapter(Context mContext, List<DoctorList> doctors) {
        this.mContext = mContext;
        this.doctors = doctors;
    }


    @Override
    public int getCount() {
        return doctors.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DoctorList doctor = doctors.get(position);
        if(convertView==null)
        {
            LayoutInflater layoutInflater =LayoutInflater.from(mContext);
            convertView= layoutInflater.inflate(R.layout.list_doctor_item,null);
        }

        TextView doctorName = convertView.findViewById(R.id.doctorName);
        ImageView doctorImage = convertView.findViewById(R.id.doctorImage);

        doctorName.setText(doctor.getName());

        RequestOptions options = new RequestOptions().centerCrop().
                placeholder(R.drawable.ic_baseline_error_outline_24).
                error(R.drawable.ic_baseline_error_outline_24);

        Glide.with(mContext).load(doctor.getPhoto_url()).apply(options).into(doctorImage);

        return  convertView;


    }
}
