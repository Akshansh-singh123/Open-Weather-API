package com.akshansh.weatherapi.common.dependecyinjection;

import android.os.Handler;
import android.os.Looper;

import com.akshansh.weatherapi.common.utils.GPSActivationHelper;
import com.akshansh.weatherapi.common.utils.GPSLocationHelper;
import com.akshansh.weatherapi.common.utils.InternetConnectionTester;
import com.akshansh.weatherapi.common.utils.PermissionHelper;
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

import retrofit2.Retrofit;

public class ControllerModule {
    private final ActivityModule activityModule;
    private Handler uiThread;

    public ControllerModule(ActivityModule activityModule) {
        this.activityModule = activityModule;
    }

    public ViewMvcFactory getViewMvcFactory(){
        return activityModule.getViewMvcFactory();
    }

    public Retrofit getCurrentWeatherRetrofitByCity(){
        return activityModule.getCurrentWeatherRetrofitByCity();
    }

    public Retrofit getWeatherForecastRetrofitByCity(){
        return activityModule.getWeatherForecastRetrofitByCity();
    }

    public Retrofit getCurrentWeatherRetrofitByLocation(){
        return activityModule.getCurrentWeatherRetrofitByLocation();
    }

    public Retrofit getWeatherForecastRetrofitByLocation(){
        return activityModule.getWeatherForecastRetrofitByLocation();
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

    public ExecutorService getExecutor(){
        return activityModule.getExecutor();
    }

    public Handler getUiThread(){
        if(uiThread == null){
            uiThread = new Handler(Looper.getMainLooper());
        }
        return uiThread;
    }

    public PermissionHelper getPermissionHelper(){
        return activityModule.getPermissionHelper();
    }

    public GPSLocationHelper getGPSLocationHelper(){
        return activityModule.getGPSLocationHelper();
    }

    public GPSActivationHelper getGPSActivationHelper(){
        return activityModule.getGPSActivationHelper();
    }

    public ImageLoaderHelper getImageLoaderHelper(){
        return new ImageLoaderHelper();
    }

    public FetchWeatherEndpoint getFetchWeatherService(){
        return new FetchWeatherService(getCurrentWeatherRetrofitByCity(),
                getWeatherForecastRetrofitByCity(),
                getCurrentWeatherRetrofitByLocation(),
                getWeatherForecastRetrofitByLocation(),
                getWeatherDataSyncHelper(),
                getInternetConnectionTester());
    }

    public FetchWeatherUseCase getFetchWeatherUseCase(){
        return new FetchWeatherUseCase(getFetchWeatherService());
    }
}
