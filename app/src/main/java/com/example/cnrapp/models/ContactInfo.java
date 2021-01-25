package com.example.cnrapp.models;

public class ContactInfo {

    String phoneNo1;
    String phoneNo2;
    String email;

    public ContactInfo(String phoneNo1, String phoneNo2, String email) {
        this.phoneNo1 = phoneNo1;
        this.phoneNo2 = phoneNo2;
        this.email = email;
    }

    public String getPhoneNo1() {
        return phoneNo1;
    }

    public String getPhoneNo2() {
        return phoneNo2;
    }

    public String getEmail() {
        return email;
    }
}
