package com.example.java_hotel_system.view_model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.java_hotel_system.model.user.ListUser;
import com.example.java_hotel_system.model.user.UserRequest;
import com.example.java_hotel_system.repo.LoginFacebookRepo;
import com.example.java_hotel_system.repo.UserDetailsRepo;
import com.example.java_hotel_system.service.RetroService;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class UserDetailsViewModel extends ViewModel {
    MutableLiveData<ListUser> liveData;
    MutableLiveData<ListUser> getAllUserLiveData;

    @Inject
    RetroService retroService;

    @Inject
    public UserDetailsViewModel() {
        liveData = new MutableLiveData<>();
        getAllUserLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<ListUser> getUserDetailsByUIDObservable() {
        return liveData;
    }

    public MutableLiveData<ListUser> getAllUserObservable() {
        return getAllUserLiveData;
    }

    public void getUserDetailsByUIDOfData(String uid) {
        UserDetailsRepo userDetailsRepo = new UserDetailsRepo(retroService);
        userDetailsRepo.postUserFacebookFromApiCall(uid, liveData);
    }

    public void getAllUserOfData() {
        UserDetailsRepo userDetailsRepo = new UserDetailsRepo(retroService);
        userDetailsRepo.getAllUserFromApiCall(getAllUserLiveData);
    }
}
