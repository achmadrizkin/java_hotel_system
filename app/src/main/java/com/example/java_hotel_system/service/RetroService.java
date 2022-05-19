package com.example.java_hotel_system.service;

import com.example.java_hotel_system.model.kamar.KamarList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetroService {
    @GET("kamar/{nama}")
    Call<KamarList> getSearchKamar(@Path("nama") String nama);
}
