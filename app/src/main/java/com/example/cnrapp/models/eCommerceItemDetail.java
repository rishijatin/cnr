package com.example.cnrapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class eCommerceItemDetail {

    int id;
    String name;
    @SerializedName("photos")
    List<PersonPics> photos;
    String price;
    String description;
    @SerializedName("website_url")
    String webURL;
    int category;

    public eCommerceItemDetail(int id, String name, List<PersonPics> imageUrl, String price, String description, String webURL, int category) {
        this.id = id;
        this.name = name;
        this.photos = imageUrl;
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

    public List<PersonPics> getPhotos() {
        return photos;
    }

    public String getPrice() {
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
