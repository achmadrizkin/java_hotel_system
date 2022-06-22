package com.example.java_hotel_system.view_model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.java_hotel_system.model.user.UserRequest;
import com.example.java_hotel_system.repo.LoginFacebookRepo;
import com.example.java_hotel_system.service.RetroService;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LoginFacebookViewModel extends ViewModel {
    MutableLiveData<UserRequest> liveData;

    @Inject
    RetroService retroService;

    @Inject
    public LoginFacebookViewModel() {
        liveData = new MutableLiveData<>();
    }

    public MutableLiveData<UserRequest> postUserByFacebookObservable() {
        return liveData;
    }

    public void postUserByFacebookOfData(UserRequest userRequest) {
        LoginFacebookRepo loginFacebookRepo = new LoginFacebookRepo(retroService);
        loginFacebookRepo.postUserFacebookFromApiCall(userRequest, liveData);
    }
}
