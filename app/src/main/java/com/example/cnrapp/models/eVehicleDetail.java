package com.example.cnrapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class eVehicleDetail {

    private int id;
    private String description;
    private String services;
    @SerializedName("video_url")
    private String videoUrl;
    List<PersonPics> photos;

    public eVehicleDetail(int id, String description, String services, String videoUrl, List<PersonPics> photos) {
        this.id = id;
        this.description = description;
        this.services = services;
        this.videoUrl = videoUrl;
        this.photos = photos;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getServices() {
        return services;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public List<PersonPics> getPhotos() {
        return photos;
    }
}
