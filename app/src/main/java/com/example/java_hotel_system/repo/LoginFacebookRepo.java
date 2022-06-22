package com.example.java_hotel_system.repo;

import androidx.lifecycle.MutableLiveData;

import com.example.java_hotel_system.model.user.UserRequest;
import com.example.java_hotel_system.service.RetroService;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginFacebookRepo {
    private RetroService retroServiceInterface;

    public LoginFacebookRepo(RetroService retroServiceInterface) {
        this.retroServiceInterface = retroServiceInterface;
    }

    public void postUserFacebookFromApiCall(UserRequest userRequest, MutableLiveData<UserRequest> liveData) {
        retroServiceInterface.postUserByLogin(userRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserRequest>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserRequest userRequest) {
                        liveData.postValue(userRequest);
                    }

                    @Override
                    public void onError(Throwable e) {
                        liveData.postValue(null);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
