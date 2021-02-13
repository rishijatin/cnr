package com.example.cnrapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventDetail {
    int id;
    String name;
    @SerializedName("invitation_card")
    String invitationCard;
    @SerializedName("video_url")
    String videoUrl;
    List<PersonPics> photos;


    public EventDetail(int id, String name, String invitationCard, String videoUrl, List<PersonPics> photos) {
        this.id = id;
        this.name = name;
        this.invitationCard = invitationCard;
        this.videoUrl = videoUrl;
        this.photos = photos;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getInvitationCard() {
        return invitationCard;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public List<PersonPics> getPhotos() {
        return photos;
    }
}
