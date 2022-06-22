package com.example.java_hotel_system.view_model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.java_hotel_system.model.user.ListUser;
import com.example.java_hotel_system.model.user.UserRequest;
import com.example.java_hotel_system.repo.LoginFacebookRepo;
import com.example.java_hotel_system.repo.UserDetailsRepo;
import com.example.java_hotel_system.service.RetroService;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class UserDetailsViewModel extends ViewModel {
    MutableLiveData<ListUser> liveData;

    @Inject
    RetroService retroService;

    @Inject
    public UserDetailsViewModel() {
        liveData = new MutableLiveData<>();
    }

    public MutableLiveData<ListUser> getUserDetailsByUIDObservable() {
        return liveData;
    }

    public void getUserDetailsByUIDOfData(String uid) {
        UserDetailsRepo userDetailsRepo = new UserDetailsRepo(retroService);
        userDetailsRepo.postUserFacebookFromApiCall(uid, liveData);
    }
}
