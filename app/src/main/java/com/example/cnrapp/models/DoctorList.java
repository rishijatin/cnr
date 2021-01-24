package com.example.cnrapp.models;

import com.google.gson.annotations.SerializedName;

public class DoctorList {
    private int id;
    @SerializedName("doctor_name")
    private String name;
    @SerializedName("doctor_photo")
    private String photo_url;

    public DoctorList(int id, String name, String photo_url) {
        this.id = id;
        this.name = name;
        this.photo_url = photo_url;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoto_url() {
        return photo_url;
    }
}
