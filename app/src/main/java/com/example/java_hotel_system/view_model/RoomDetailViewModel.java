package com.example.java_hotel_system.view_model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.java_hotel_system.model.booking.PostBookingRequest;
import com.example.java_hotel_system.repository.RoomDetailRepo;
import com.example.java_hotel_system.service.RetroService;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class RoomDetailViewModel extends ViewModel {
    MutableLiveData<PostBookingRequest> liveData;

    @Inject
    RetroService retroService;

    @Inject
    public RoomDetailViewModel() {
        liveData = new MutableLiveData();
    }

    public MutableLiveData<PostBookingRequest> postBookingObservable() {
        return liveData;
    }

    public void postBookingOfData(PostBookingRequest postBookingRequest) {
        RoomDetailRepo roomDetailRepo = new RoomDetailRepo(retroService);
        roomDetailRepo.postBookingApiCall(liveData, postBookingRequest);
    }
}
