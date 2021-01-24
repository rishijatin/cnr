package com.example.cnrapp.models;

public class eHealthCategory {

    int id;
    String name;

    public eHealthCategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
