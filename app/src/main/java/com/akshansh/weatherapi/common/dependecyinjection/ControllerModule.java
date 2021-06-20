package com.akshansh.weatherapi.common.dependecyinjection;

import com.akshansh.weatherapi.common.graphics.IconLoaderHelper;
import com.akshansh.weatherapi.common.utils.InternetConnectionTester;
import com.akshansh.weatherapi.screens.common.screensnavigator.ScreensNavigator;
import com.akshansh.weatherapi.common.utils.WeatherDataSyncHelper;
import com.akshansh.weatherapi.common.graphics.ImageLoaderHelper;
import com.akshansh.weatherapi.common.ViewMvcFactory;
import com.akshansh.weatherapi.common.graphics.PaletteHelper;
import com.akshansh.weatherapi.common.graphics.WindowStatusBarHelper;
import com.akshansh.weatherapi.networking.weather.FetchWeatherService;
import com.akshansh.weatherapi.screens.common.toast.ToastHelper;
import com.akshansh.weatherapi.currentweather.FetchWeatherEndpoint;
import com.akshansh.weatherapi.currentweather.FetchWeatherUseCase;

import retrofit2.Retrofit;

public class ControllerModule {
    private final ActivityModule activityModule;

    public ControllerModule(ActivityModule activityModule) {
        this.activityModule = activityModule;
    }

    public ViewMvcFactory getViewMvcFactory(){
        return activityModule.getViewMvcFactory();
    }

    public Retrofit getCurrentWeatherRetrofit(){
        return activityModule.getCurrentWeatherRetrofit();
    }

    public Retrofit getWeatherForecastRetrofit(){
        return activityModule.getWeatherForecastRetrofit();
    }

    public ToastHelper getToastHelper(){
        return activityModule.getToastHelper();
    }

    public PaletteHelper getPaletteHelper(){
        return activityModule.getPaletteHelper();
    }

    public WeatherDataSyncHelper getWeatherDataSyncHelper(){
        return activityModule.getWeatherDataSyncHelper();
    }

    public WindowStatusBarHelper getWindowStatusBarHelper(){
        return activityModule.getWindowStatusBarHelper();
    }

    public ScreensNavigator getScreensNavigator(){
        return activityModule.getScreensNavigator();
    }

    public InternetConnectionTester getInternetConnectionTester(){
        return activityModule.getInternetConnectionTester();
    }

    public ImageLoaderHelper getImageLoaderHelper(){
        return new ImageLoaderHelper();
    }

    public FetchWeatherEndpoint getFetchWeatherService(){
        return new FetchWeatherService(getCurrentWeatherRetrofit(),getWeatherForecastRetrofit(),
                getWeatherDataSyncHelper(),getInternetConnectionTester());
    }

    public FetchWeatherUseCase getFetchWeatherUseCase(){
        return new FetchWeatherUseCase(getFetchWeatherService());
    }
}
