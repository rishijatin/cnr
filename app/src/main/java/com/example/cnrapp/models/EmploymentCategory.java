package com.example.cnrapp.models;

public class EmploymentCategory {
    int id;
    String name;

    public EmploymentCategory(int id, String name) {
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
