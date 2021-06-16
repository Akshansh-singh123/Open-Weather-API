package com.akshansh.weatherapi.common.dependecyinjection;

import com.akshansh.weatherapi.common.CustomApplication;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.akshansh.weatherapi.common.Constants.ENDPOINT;

public class ApplicationModule {
    private Retrofit retrofit;

    public Retrofit getRetrofit() {
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
