package com.example.java_hotel_system.model.booking;

public class Booking {
    private String kd_booking, total, user_buy, rating, nama_kamar, kota, check_in, check_out, lokasi, image_url;

    public Booking(String kd_booking, String total, String user_buy, String rating, String nama_kamar, String kota, String check_in, String check_out, String lokasi, String image_url) {
        this.kd_booking = kd_booking;
        this.total = total;
        this.user_buy = user_buy;
        this.rating = rating;
        this.nama_kamar = nama_kamar;
        this.kota = kota;
        this.check_in = check_in;
        this.check_out = check_out;
        this.lokasi = lokasi;
        this.image_url = image_url;
    }

    public Booking() {

    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getKd_booking() {
        return kd_booking;
    }

    public void setKd_booking(String kd_booking) {
        this.kd_booking = kd_booking;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getUser_buy() {
        return user_buy;
    }

    public void setUser_buy(String user_buy) {
        this.user_buy = user_buy;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getNama_kamar() {
        return nama_kamar;
    }

    public void setNama_kamar(String nama_kamar) {
        this.nama_kamar = nama_kamar;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getCheck_in() {
        return check_in;
    }

    public void setCheck_in(String check_in) {
        this.check_in = check_in;
    }

    public String getCheck_out() {
        return check_out;
    }

    public void setCheck_out(String check_out) {
        this.check_out = check_out;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }
}
