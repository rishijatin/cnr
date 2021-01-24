package com.example.cnrapp.models;

import com.google.gson.annotations.SerializedName;

public class Category {

    int id;
    String name;
    @SerializedName("image_url")
    String url;

    public Category(int id, String name, String url) {
        this.id = id;
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public int getId() {
        return id;
    }
}
