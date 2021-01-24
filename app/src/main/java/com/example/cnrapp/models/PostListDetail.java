package com.example.cnrapp.models;

import com.google.gson.annotations.SerializedName;

public class PostListDetail {

    int id;
    String heading;
    int category;
    @SerializedName("photo_url_large")
    String photoUrl;
    @SerializedName("webpage_url")
    String webPageUrl;
    @SerializedName("body")
    String description;

    public PostListDetail(int id, String heading, int category, String photoUrl, String webPageUrl, String description) {
        this.id = id;
        this.heading = heading;
        this.category = category;
        this.photoUrl = photoUrl;
        this.webPageUrl = webPageUrl;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getHeading() {
        return heading;
    }

    public int getCategory() {
        return category;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getWebPageUrl() {
        return webPageUrl;
    }

    public String getDescription() {
        return description;
    }
}
