package com.example.cnrapp.models;

import com.google.gson.annotations.SerializedName;

public class eCommerceItem {

    private int id;
    @SerializedName("small_image_url")
    private String photoURL;
    @SerializedName("name")
    private String heading;
    private String price;

    public eCommerceItem(int id, String photoURL, String heading, String price) {
        this.id = id;
        this.photoURL = photoURL;
        this.heading = heading;
        this.price = price;
    }



    public int getId() {
        return id;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public String getHeading() {
        return heading;
    }

    public String getPrice() {
        return price;
    }
}
