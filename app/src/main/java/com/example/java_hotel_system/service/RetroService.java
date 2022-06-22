package com.example.java_hotel_system.service;

import com.example.java_hotel_system.model.user.UserRequest;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetroService {
    @POST("insert_user.php")
    Observable<UserRequest> postUserByLogin(@Body UserRequest userRequest);
}
