package com.example.cnrapp.models;

public class PersonPics {
    private String url;
    private int id;

    public PersonPics(String url, int id) {
        this.url = url;
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public int getId() {
        return id;
    }
}
