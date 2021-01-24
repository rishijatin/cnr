package com.example.cnrapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostListModified {

    String description;
    @SerializedName("posts")
    List<PostList> posts;

    public PostListModified(String description, List<PostList> posts) {
        this.description = description;
        this.posts = posts;
    }

    public String getDescription() {
        return description;
    }

    public List<PostList> getPosts() {
        return posts;
    }
}
