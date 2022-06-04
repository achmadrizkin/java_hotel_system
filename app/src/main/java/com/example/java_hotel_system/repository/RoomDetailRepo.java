package com.example.java_hotel_system.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.java_hotel_system.model.booking.PostBookingRequest;
import com.example.java_hotel_system.model.user.PostUserRequest;
import com.example.java_hotel_system.service.RetroService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoomDetailRepo {
    private RetroService retroServiceInterface;

    public RoomDetailRepo(RetroService retroServiceInterface) {
        this.retroServiceInterface = retroServiceInterface;
    }

    public void postBookingApiCall(MutableLiveData<PostBookingRequest> liveData, PostBookingRequest postBookingRequest) {
        Call<PostBookingRequest> call  = retroServiceInterface.postBooking(postBookingRequest);
        call.enqueue(new Callback<PostBookingRequest>() {
            @Override
            public void onResponse(Call<PostBookingRequest> call, Response<PostBookingRequest> response) {
                if(response.isSuccessful()) {
                    liveData.postValue(response.body());
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<PostBookingRequest> call, Throwable t) {
                liveData.postValue(null);
            }
        });
    }
}
