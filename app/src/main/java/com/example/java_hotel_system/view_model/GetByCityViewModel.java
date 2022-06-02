package com.example.java_hotel_system.view_model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.java_hotel_system.model.kamar.Kamar;
import com.example.java_hotel_system.repository.GetByCityRepo;
import com.example.java_hotel_system.repository.HomeRepo;
import com.example.java_hotel_system.service.RetroService;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class GetByCityViewModel extends ViewModel {
    MutableLiveData<List<Kamar>> kamarByCityLiveData;

    @Inject
    RetroService retroService;

    @Inject
    public GetByCityViewModel() {
        kamarByCityLiveData = new MutableLiveData();
    }

    public MutableLiveData<List<Kamar>> getByCityKamarObservable() {
        return kamarByCityLiveData;
    }

    public void  getByCityKamarOfData(String city) {
        GetByCityRepo getByCityRepo = new GetByCityRepo(retroService);
        getByCityRepo.getByCityKamarApiCall(kamarByCityLiveData, city);
    }
}
