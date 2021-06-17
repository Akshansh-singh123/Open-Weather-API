package com.akshansh.weatherapi.common;

import android.app.Application;

import com.akshansh.weatherapi.common.dependecyinjection.ApplicationModule;

import retrofit2.Retrofit;

public class CustomApplication extends Application {
    private ApplicationModule applicationModule;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationModule = new ApplicationModule(this);
    }

    public ApplicationModule getApplicationModule() {
        return applicationModule;
    }
}
