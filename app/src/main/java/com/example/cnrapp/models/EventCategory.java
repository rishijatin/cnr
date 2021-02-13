package com.example.cnrapp.models;

public class EventCategory {

    int id;
    String name;

    public EventCategory(int id, String name) {
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
