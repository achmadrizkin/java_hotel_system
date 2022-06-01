package com.example.java_hotel_system.view_model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.java_hotel_system.model.kamar.Kamar;
import com.example.java_hotel_system.repository.HomeRepo;
import com.example.java_hotel_system.service.RetroService;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class HomeViewModel extends ViewModel {
    MutableLiveData<List<Kamar>> allKamarLiveData;
    MutableLiveData<List<Kamar>> trendingKamarLiveData;

    @Inject
    RetroService retroService;

    @Inject
    public HomeViewModel() {
        allKamarLiveData = new MutableLiveData();
        trendingKamarLiveData = new MutableLiveData();
    }

    public MutableLiveData<List<Kamar>> getALlHotelObservable() {
        return allKamarLiveData;
    }

    public MutableLiveData<List<Kamar>> getTrendingHotelObservable() {
        return trendingKamarLiveData;
    }

    public void getALlHotelOfData() {
        HomeRepo homeRepo = new HomeRepo(retroService);
        homeRepo.getALlHotelApiCall(allKamarLiveData);
    }

    public void getTrendingHotelOfData() {
        HomeRepo homeRepo = new HomeRepo(retroService);
        homeRepo.getTrendingHotelApiCall(trendingKamarLiveData);
    }
}
