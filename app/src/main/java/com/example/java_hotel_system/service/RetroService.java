package com.example.java_hotel_system.service;

import com.example.java_hotel_system.model.user.ListUser;
import com.example.java_hotel_system.model.user.UserRequest;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetroService {
    @POST("insert_user.php")
    Observable<UserRequest> postUserByLogin(@Body UserRequest userRequest);

    // call user details
    // example: https://achmadrizkin.my.id/mobprog_hotel/select_user.php?uid=E5WuhBcN8wOYRcfj5HwRVVzfj203
    @GET("select_user.php")
    Observable<ListUser> getUserByUID(@Query("uid") String uid);

    @GET("select_all_user.php")
    Observable<ListUser> getAllUser();
}
