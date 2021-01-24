package com.example.cnrapp.models;

import com.google.gson.annotations.SerializedName;

public class PostList {

    private int id;
    private int category;
    @SerializedName("photo_url_small")
    private String photoURL;
    private String heading;

    public PostList(int id, int category, String photoURL, String heading) {
        this.id = id;
        this.category = category;
        this.photoURL = photoURL;
        this.heading = heading;
    }

    public int getId() {
        return id;
    }

    public int getCategory() {
        return category;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public String getHeading() {
        return heading;
    }
}
