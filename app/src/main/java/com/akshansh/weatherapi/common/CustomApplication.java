package com.akshansh.weatherapi.common;

import android.app.Application;

import com.akshansh.weatherapi.common.dependecyinjection.application.ApplicationComponent;
import com.akshansh.weatherapi.common.dependecyinjection.application.ApplicationModule;
import com.akshansh.weatherapi.common.dependecyinjection.application.DaggerApplicationComponent;

public class CustomApplication extends Application {
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent
                                .builder()
                                .applicationModule(new ApplicationModule(this))
                                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
