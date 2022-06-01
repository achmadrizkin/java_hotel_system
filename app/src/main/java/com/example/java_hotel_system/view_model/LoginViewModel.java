package com.example.java_hotel_system.view_model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.java_hotel_system.model.kamar.Kamar;
import com.example.java_hotel_system.model.user.PostUserRequest;
import com.example.java_hotel_system.model.user.PostUserResponse;
import com.example.java_hotel_system.repository.HomeRepo;
import com.example.java_hotel_system.repository.LoginRepo;
import com.example.java_hotel_system.service.RetroService;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LoginViewModel extends ViewModel {
    MutableLiveData<PostUserRequest> liveData;

    @Inject
    RetroService retroService;

    @Inject
    public LoginViewModel() {
        liveData = new MutableLiveData();
    }

    public MutableLiveData<PostUserRequest> postUserByLoginObservable() {
        return liveData;
    }

    public void postUserByLoginOfData(PostUserRequest postUserRequest) {
        LoginRepo loginRepo = new LoginRepo(retroService);
        loginRepo.postUserByLoginApiCall(liveData, postUserRequest);
    }
}
