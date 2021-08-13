package com.akshansh.weatherapi.common.dependecyinjection.controller;

import android.os.Handler;
import android.os.Looper;

import com.akshansh.weatherapi.city.FetchCitiesQueryUseCase;
import com.akshansh.weatherapi.city.GetCitiesQueryEndpoint;
import com.akshansh.weatherapi.common.dependecyinjection.activity.ActivityModule;
import com.akshansh.weatherapi.common.utils.GPSActivationHelper;
import com.akshansh.weatherapi.common.utils.GPSLocationHelper;
import com.akshansh.weatherapi.common.utils.InternetConnectionTester;
import com.akshansh.weatherapi.common.utils.PermissionHelper;
import com.akshansh.weatherapi.networking.city.CitiesAPI;
import com.akshansh.weatherapi.networking.city.GetCitiesService;
import com.akshansh.weatherapi.networking.weather.OpenWeatherApi;
import com.akshansh.weatherapi.screens.common.screensnavigator.ScreensNavigator;
import com.akshansh.weatherapi.common.utils.WeatherDataSyncHelper;
import com.akshansh.weatherapi.common.graphics.ImageLoaderHelper;
import com.akshansh.weatherapi.common.ViewMvcFactory;
import com.akshansh.weatherapi.common.graphics.PaletteHelper;
import com.akshansh.weatherapi.common.graphics.WindowStatusBarHelper;
import com.akshansh.weatherapi.networking.weather.FetchWeatherService;
import com.akshansh.weatherapi.screens.common.toast.ToastHelper;
import com.akshansh.weatherapi.weather.FetchWeatherEndpoint;
import com.akshansh.weatherapi.weather.FetchWeatherUseCase;

import java.util.concurrent.ExecutorService;

import dagger.Module;
import dagger.Provides;

@Module
public class ControllerModule {
    @Provides
    @ControllerScope
    public Handler getUiThread(){
        return new Handler(Looper.getMainLooper());
    }

    @Provides
    public ImageLoaderHelper getImageLoaderHelper(){
        return new ImageLoaderHelper();
    }

    @Provides
    public FetchWeatherEndpoint getFetchWeatherService(OpenWeatherApi openWeatherApi,
                                                       WeatherDataSyncHelper weatherDataSyncHelper,
                                                       InternetConnectionTester internetConnectionTester){
        return new FetchWeatherService(openWeatherApi,
                weatherDataSyncHelper,
                internetConnectionTester);
    }

    @Provides
    public FetchWeatherUseCase getFetchWeatherUseCase(FetchWeatherEndpoint fetchWeatherEndpoint){
        return new FetchWeatherUseCase(fetchWeatherEndpoint);
    }

    @Provides
    public GetCitiesQueryEndpoint getCitiesQueryEndpoint(CitiesAPI citiesAPI){
        return new GetCitiesService(citiesAPI);
    }

    @Provides
    public FetchCitiesQueryUseCase getFetchCitiesQueryUseCase(GetCitiesQueryEndpoint endpoint){
        return new FetchCitiesQueryUseCase(endpoint);
    }
}
