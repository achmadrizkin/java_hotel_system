package com.example.java_hotel_system.di;

import com.example.java_hotel_system.service.RetroService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {
    // localhost:3000/v1/products
    String BASE_URL = "http://10.0.2.2:3000/v1/";

    @Singleton
    @Provides
    public Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    public RetroService getRetroServiceInterface(Retrofit retrofit) {
        return retrofit.create(RetroService.class);
    }
}
