package com.example.java_hotel_system.model.user;


import java.util.List;

public class ListUser {
    private List<UserRequest> data;

    public ListUser(List<UserRequest> data) {
        this.data = data;
    }

    public List<UserRequest> getData() {
        return data;
    }

    public void setData(List<UserRequest> data) {
        this.data = data;
    }
}
