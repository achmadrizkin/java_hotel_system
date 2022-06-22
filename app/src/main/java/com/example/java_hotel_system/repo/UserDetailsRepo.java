package com.example.java_hotel_system.repo;

import androidx.lifecycle.MutableLiveData;

import com.example.java_hotel_system.model.user.ListUser;
import com.example.java_hotel_system.model.user.UserRequest;
import com.example.java_hotel_system.service.RetroService;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class UserDetailsRepo {
    private RetroService retroServiceInterface;

    public UserDetailsRepo(RetroService retroServiceInterface) {
        this.retroServiceInterface = retroServiceInterface;
    }

    public void getAllUserFromApiCall(MutableLiveData<ListUser> liveData) {
        retroServiceInterface.getAllUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<ListUser>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ListUser listUser) {
                liveData.postValue(listUser);
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

    // use Schedulers.computation if u have like 10.000 data
    public void postUserFacebookFromApiCall(String uid, MutableLiveData<ListUser> liveData) {
        retroServiceInterface.getUserByUID(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ListUser>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ListUser userRequest) {
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
