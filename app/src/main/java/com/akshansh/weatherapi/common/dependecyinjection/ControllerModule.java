package com.akshansh.weatherapi.common.dependecyinjection;

import com.akshansh.weatherapi.common.Constants;
import com.akshansh.weatherapi.common.CustomApplication;
import com.akshansh.weatherapi.common.ViewMvcFactory;
import com.akshansh.weatherapi.screens.common.views.ViewMvc;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.akshansh.weatherapi.common.Constants.*;

public class ControllerModule {
    private final ActivityModule activityModule;

    public ControllerModule(ActivityModule activityModule) {
        this.activityModule = activityModule;
    }

    public ViewMvcFactory getViewMvcFactory(){
        return activityModule.getViewMvcFactory();
    }

    public Retrofit getRetrofit(){
        return activityModule.getRetrofit();
    }

}
