package com.example.cnrapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DoctorDetail {
    private int id;
    @SerializedName("doctor_name")
    private String name;
    @SerializedName("doctor_photo")
    private String url;
    private String qualification;
    private int experience;
    private String description;
    @SerializedName("video_url")
    private String videoUrl;
    private List<PersonPics> photos;

    public DoctorDetail(int id, String name, String url, String qualification, int experience, String description, String videoUrl, List<PersonPics> photos) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.qualification = qualification;
        this.experience = experience;
        this.description = description;
        this.videoUrl = videoUrl;
        this.photos = photos;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getQualification() {
        return qualification;
    }

    public int getExperience() {
        return experience;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public List<PersonPics> getPhotos() {
        return photos;
    }
}
