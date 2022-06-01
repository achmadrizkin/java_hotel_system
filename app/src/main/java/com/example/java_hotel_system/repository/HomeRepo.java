package com.example.java_hotel_system.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.java_hotel_system.model.kamar.Kamar;
import com.example.java_hotel_system.model.kamar.KamarList;
import com.example.java_hotel_system.service.RetroService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeRepo {
    private RetroService retroServiceInterface;

    public HomeRepo(RetroService retroServiceInterface) {
        this.retroServiceInterface = retroServiceInterface;
    }

    public void getALlHotelApiCall(MutableLiveData<List<Kamar>> liveData) {
        Call<KamarList> call  = retroServiceInterface.getALlHotel();
        call.enqueue(new Callback<KamarList>() {
            @Override
            public void onResponse(Call<KamarList> call, Response<KamarList> response) {
                if(response.isSuccessful()) {
                    liveData.postValue(response.body().getData());
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<KamarList> call, Throwable t) {
                liveData.postValue(null);
            }
        });
    }

    public void getTrendingHotelApiCall(MutableLiveData<List<Kamar>> liveData) {
        Call<KamarList> call  = retroServiceInterface.getTrendingHotel();
        call.enqueue(new Callback<KamarList>() {
            @Override
            public void onResponse(Call<KamarList> call, Response<KamarList> response) {
                if(response.isSuccessful()) {
                    liveData.postValue(response.body().getData());
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<KamarList> call, Throwable t) {
                liveData.postValue(null);
            }
        });
    }
}
