package com.example.cnrapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PersonModel {
    private int id;
    private String name;
    @SerializedName("description")
    private String aboutPerson;
    @SerializedName("social_work")
    private String socialWork;
    @SerializedName("family_members")
    private String familyMembers;
    List<PersonPics> photos;
    @SerializedName("video_url")
    private String videoUrl;

    public PersonModel(int id, String name, String aboutPerson, String socialWork, String familyMembers, List<PersonPics> photos,String videoUrl) {
        this.id = id;
        this.name = name;
        this.aboutPerson = aboutPerson;
        this.socialWork = socialWork;
        this.familyMembers = familyMembers;
        this.photos = photos;
        this.videoUrl=videoUrl;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAboutPerson() {
        return aboutPerson;
    }

    public String getSocialWork() {
        return socialWork;
    }

    public String getFamilyMembers() {
        return familyMembers;
    }

    public List<PersonPics> getPhotos() {
        return photos;
    }

    public String getVideoUrl(){return videoUrl;}
}
