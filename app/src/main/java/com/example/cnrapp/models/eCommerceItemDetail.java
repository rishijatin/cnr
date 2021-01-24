package com.example.cnrapp.models;

import com.google.gson.annotations.SerializedName;

public class eCommerceItemDetail {

    int id;
    String name;
    @SerializedName("large_image_url")
    String imageUrl;
    int price;
    String description;
    @SerializedName("website_url")
    String webURL;
    int category;

    public eCommerceItemDetail(int id, String name, String imageUrl, int price, String description, String webURL, int category) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.description = description;
        this.webURL = webURL;
        this.category = category;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getWebURL() {
        return webURL;
    }

    public int getCategory() {
        return category;
    }
}
