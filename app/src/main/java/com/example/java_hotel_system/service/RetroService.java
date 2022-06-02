package com.example.java_hotel_system.service;

import com.example.java_hotel_system.model.kamar.KamarList;
import com.example.java_hotel_system.model.user.PostUserRequest;
import com.example.java_hotel_system.model.user.PostUserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetroService {
    @POST("insert_user.php")
    Call<PostUserRequest> postUserByLogin(@Body PostUserRequest postUserRequest);

    @GET("select.php")
    Call<KamarList> getALlHotel();

    @GET("select_trending.php")
    Call<KamarList> getTrendingHotel();

    @GET("search_kamar.php")
    Call<KamarList> getSearchKamar(@Query("name") String name);
}
