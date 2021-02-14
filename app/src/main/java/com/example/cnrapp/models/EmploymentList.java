package com.example.cnrapp.models;

public class EmploymentList {
    int id;
    String heading;

    public EmploymentList(int id, String heading) {
        this.id = id;
        this.heading = heading;
    }

    public int getId() {
        return id;
    }

    public String getHeading() {
        return heading;
    }
}
