package com.example.java_hotel_system.model.user;

public class PostUserRequest {
    private String name, uid, image_url;

    public PostUserRequest(String name, String uid, String image_url) {
        this.name = name;
        this.uid = uid;
        this.image_url = image_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
