package com.example.java_hotel_system.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.java_hotel_system.model.kamar.Kamar;
import com.example.java_hotel_system.model.kamar.KamarList;
import com.example.java_hotel_system.model.user.PostUserRequest;
import com.example.java_hotel_system.model.user.PostUserResponse;
import com.example.java_hotel_system.service.RetroService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepo {
    private RetroService retroServiceInterface;

    public LoginRepo(RetroService retroServiceInterface) {
        this.retroServiceInterface = retroServiceInterface;
    }

    public void postUserByLoginApiCall(MutableLiveData<PostUserRequest> liveData, PostUserRequest postUserRequest) {
        Call<PostUserRequest> call  = retroServiceInterface.postUserByLogin(postUserRequest);
        call.enqueue(new Callback<PostUserRequest>() {
            @Override
            public void onResponse(Call<PostUserRequest> call, Response<PostUserRequest> response) {
                if(response.isSuccessful()) {
                    liveData.postValue(response.body());
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<PostUserRequest> call, Throwable t) {
                liveData.postValue(null);
            }
        });
    }
}
