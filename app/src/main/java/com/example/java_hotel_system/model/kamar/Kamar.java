package com.example.java_hotel_system.model.kamar;

public class Kamar {
    private String nama;
    private String kd_kamar;
    private String image_url;
    private String rating;
    private String harga;
    private String lokasi;
    private String kota;
    private String deskripsi;
    private String jmlh_kasur;
    private String jmlh_ruangan;

    public Kamar(String nama, String image_url, String rating, String harga, String lokasi, String deskripsi, String jmlh_kasur, String jmlh_ruangan, String kota, String kd_kamar) {
        this.nama = nama;
        this.image_url = image_url;
        this.rating = rating;
        this.harga = harga;
        this.lokasi = lokasi;
        this.kota = kota;
        this.deskripsi = deskripsi;
        this.jmlh_kasur = jmlh_kasur;
        this.jmlh_ruangan = jmlh_ruangan;
        this.kd_kamar = kd_kamar;
    }

    public Kamar() {
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getJmlh_kasur() {
        return jmlh_kasur;
    }

    public void setJmlh_kasur(String jmlh_kasur) {
        this.jmlh_kasur = jmlh_kasur;
    }

    public String getJmlh_ruangan() {
        return jmlh_ruangan;
    }

    public void setJmlh_ruangan(String jmlh_ruangan) {
        this.jmlh_ruangan = jmlh_ruangan;
    }

    public String getKota() {
        return kota;
    }

    public void setKota(String kota) {
        this.kota = kota;
    }

    public String getKd_kamar() {
        return kd_kamar;
    }

    public void setKd_kamar(String kd_kamar) {
        this.kd_kamar = kd_kamar;
    }
}
