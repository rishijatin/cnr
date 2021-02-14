package com.example.cnrapp.models;

public class eVehicleCategory {
    private int id;
    private String name;

    public eVehicleCategory(int id, String name) {
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
