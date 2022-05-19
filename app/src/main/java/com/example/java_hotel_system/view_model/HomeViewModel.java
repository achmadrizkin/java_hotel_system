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
    MutableLiveData<List<Kamar>> liveData;

    @Inject
    RetroService retroService;

    @Inject
    public HomeViewModel() {
        liveData = new MutableLiveData();
    }

    public MutableLiveData<List<Kamar>> getSearchKamarObservable() {
        return liveData;
    }

    public void getSearchKamar(String nama) {
        HomeRepo homeRepo = new HomeRepo(retroService);
        homeRepo.makeAPICall(liveData, nama);
    }
}
