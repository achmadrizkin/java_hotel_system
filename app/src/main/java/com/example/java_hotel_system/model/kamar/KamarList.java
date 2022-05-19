package com.example.java_hotel_system.model.kamar;

import java.util.List;

public class KamarList {
    private List<Kamar> data;

    public KamarList(List<Kamar> data) {
        this.data = data;
    }

    public List<Kamar> getData() {
        return data;
    }

    public void setData(List<Kamar> data) {
        this.data = data;
    }
}
