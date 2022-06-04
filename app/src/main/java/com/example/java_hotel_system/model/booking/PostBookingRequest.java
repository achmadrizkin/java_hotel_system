package com.example.java_hotel_system.model.booking;

public class PostBookingRequest {
    String kd_booking, uid, kd_kamar, tgl_check_in, tgl_check_out;

    public PostBookingRequest(String kd_booking, String uid, String kd_kamar, String tgl_check_out, String tgl_check_in) {
        this.kd_booking = kd_booking;
        this.uid = uid;
        this.kd_kamar = kd_kamar;
        this.tgl_check_in = tgl_check_in;
        this.tgl_check_out = tgl_check_out;
    }

    public String getTgl_check_in() {
        return tgl_check_in;
    }

    public void setTgl_check_in(String tgl_check_in) {
        this.tgl_check_in = tgl_check_in;
    }

    public String getTgl_check_out() {
        return tgl_check_out;
    }

    public void setTgl_check_out(String tgl_check_out) {
        this.tgl_check_out = tgl_check_out;
    }

    public String getKd_booking() {
        return kd_booking;
    }

    public void setKd_booking(String kd_booking) {
        this.kd_booking = kd_booking;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getKd_kamar() {
        return kd_kamar;
    }

    public void setKd_kamar(String kd_kamar) {
        this.kd_kamar = kd_kamar;
    }
}
