package com.example.java_hotel_system.model.user;

public class UserRequest {
    private String name, uid, image_url, role, log_via;

    public UserRequest(String name, String uid, String image_url, String role, String log_via) {
        this.name = name;
        this.uid = uid;
        this.image_url = image_url;
        this.role = role;
        this.log_via = log_via;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getLog_via() {
        return log_via;
    }

    public void setLog_via(String log_via) {
        this.log_via = log_via;
    }
}
