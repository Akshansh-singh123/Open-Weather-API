package com.akshansh.weatherapi.common.dependecyinjection;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;

import com.akshansh.weatherapi.common.utils.GPSActivationHelper;
import com.akshansh.weatherapi.common.utils.GPSLocationHelper;
import com.akshansh.weatherapi.common.utils.InternetConnectionTester;
import com.akshansh.weatherapi.common.utils.PermissionHelper;
import com.akshansh.weatherapi.screens.common.screensnavigator.ScreensNavigator;
import com.akshansh.weatherapi.common.ViewMvcFactory;
import com.akshansh.weatherapi.common.utils.WeatherDataSyncHelper;
import com.akshansh.weatherapi.common.graphics.PaletteHelper;
import com.akshansh.weatherapi.common.graphics.WindowStatusBarHelper;
import com.akshansh.weatherapi.screens.common.toast.ToastHelper;

import java.util.concurrent.ExecutorService;

import retrofit2.Retrofit;

public class ActivityModule {
    private final AppCompatActivity activity;
    private final ApplicationModule applicationModule;
    private PermissionHelper permissionHelper;
    private GPSLocationHelper gpsLocationHelper;
    private GPSActivationHelper gpsActivationHelper;

    public ActivityModule(ApplicationModule applicationModule, AppCompatActivity activity) {
        this.applicationModule = applicationModule;
        this.activity = activity;
    }

    public ExecutorService getExecutor(){
        return applicationModule.getExecutor();
    }

    public Retrofit getCurrentWeatherRetrofitByCity(){
        return applicationModule.getCurrentWeatherRetrofitByCity();
    }

    public Retrofit getWeatherForecastRetrofitByCity(){
        return applicationModule.getWeatherForecastRetrofitByCity();
    }

    public Retrofit getCurrentWeatherRetrofitByLocation(){
        return applicationModule.getCurrentWeatherRetrofitByLocation();
    }

    public Retrofit getWeatherForecastRetrofitByLocation(){
        return applicationModule.getWeatherForecastRetrofitByLocation();
    }

    public WeatherDataSyncHelper getWeatherDataSyncHelper(){
        return applicationModule.getWeatherDataSyncHelper();
    }

    public PermissionHelper getPermissionHelper(){
        if(permissionHelper == null){
            permissionHelper = new PermissionHelper(activity);
        }
        return permissionHelper;
    }

    public Context getContext(){
        return activity;
    }

    private Resources getResources() {
        return activity.getResources();
    }

    public LayoutInflater getLayoutInflater(){
        return LayoutInflater.from(activity);
    }

    public WindowStatusBarHelper getWindowStatusBarHelper(){
        return new WindowStatusBarHelper(activity);
    }

    public ToastHelper getToastHelper(){
        return new ToastHelper(activity);
    }

    public ViewMvcFactory getViewMvcFactory(){
        return new ViewMvcFactory(getLayoutInflater());
    }

    public PaletteHelper getPaletteHelper(){
        return new PaletteHelper(getResources());
    }

    public ScreensNavigator getScreensNavigator(){
        return new ScreensNavigator(activity);
    }

    public InternetConnectionTester getInternetConnectionTester(){
        return new InternetConnectionTester(activity);
    }

    public GPSLocationHelper getGPSLocationHelper(){
        if(gpsLocationHelper == null) {
            gpsLocationHelper = new GPSLocationHelper(activity,getExecutor());
        }
        return gpsLocationHelper;
    }

    public GPSActivationHelper getGPSActivationHelper(){
        if(gpsActivationHelper == null){
            gpsActivationHelper = new GPSActivationHelper(activity);
        }
        return gpsActivationHelper;
    }
}
