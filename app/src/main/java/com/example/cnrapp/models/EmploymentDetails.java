package com.example.cnrapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EmploymentDetails {

    int id;
    String heading;
    String description;
    String location;
    String details;
    @SerializedName("video_url")
    String videoUrl;
    List<PersonPics> photos;

    public EmploymentDetails(int id, String heading, String description, String location, String details, String videoUrl, List<PersonPics> photos) {
        this.id = id;
        this.heading = heading;
        this.description = description;
        this.location = location;
        this.details = details;
        this.videoUrl = videoUrl;
        this.photos = photos;
    }

    public int getId() {
        return id;
    }

    public String getHeading() {
        return heading;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getDetails() {
        return details;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public List<PersonPics> getPhotos() {
        return photos;
    }
}
