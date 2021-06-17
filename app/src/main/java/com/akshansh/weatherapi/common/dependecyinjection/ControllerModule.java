package com.akshansh.weatherapi.common.dependecyinjection;

import com.akshansh.weatherapi.common.Constants;
import com.akshansh.weatherapi.common.CustomApplication;
import com.akshansh.weatherapi.common.ViewMvcFactory;
import com.akshansh.weatherapi.networking.weather.FetchWeatherService;
import com.akshansh.weatherapi.screens.common.toast.ToastHelper;
import com.akshansh.weatherapi.screens.common.views.ViewMvc;
import com.akshansh.weatherapi.weather.FetchWeatherEndpoint;
import com.akshansh.weatherapi.weather.FetchWeatherUseCase;

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

    public ToastHelper getToastHelper(){
        return activityModule.getToastHelper();
    }

    public FetchWeatherEndpoint getFetchWeatherService(){
        return new FetchWeatherService(getRetrofit());
    }

    public FetchWeatherUseCase getFetchWeatherUseCase(){
        return new FetchWeatherUseCase(getFetchWeatherService());
    }
}
