package com.akshansh.weatherapi.common.dependecyinjection.activity;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;

import com.akshansh.weatherapi.common.dependecyinjection.application.ApplicationModule;
import com.akshansh.weatherapi.common.utils.GPSActivationHelper;
import com.akshansh.weatherapi.common.utils.GPSLocationHelper;
import com.akshansh.weatherapi.common.utils.InternetConnectionTester;
import com.akshansh.weatherapi.common.utils.PermissionHelper;
import com.akshansh.weatherapi.networking.weather.OpenWeatherApi;
import com.akshansh.weatherapi.screens.common.screensnavigator.ScreensNavigator;
import com.akshansh.weatherapi.common.ViewMvcFactory;
import com.akshansh.weatherapi.common.utils.WeatherDataSyncHelper;
import com.akshansh.weatherapi.common.graphics.PaletteHelper;
import com.akshansh.weatherapi.common.graphics.WindowStatusBarHelper;
import com.akshansh.weatherapi.screens.common.toast.ToastHelper;

import java.util.concurrent.ExecutorService;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private final AppCompatActivity activity;

    public ActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    public AppCompatActivity getActivity(){
        return activity;
    }

    @Provides
    @ActivityScope
    public PermissionHelper getPermissionHelper(AppCompatActivity activity){
        return new PermissionHelper(activity);
    }

    @Provides
    public Context getContext(AppCompatActivity activity){
        return activity;
    }

    @Provides
    public Resources getResources(AppCompatActivity activity) {
        return activity.getResources();
    }

    @Provides
    public LayoutInflater getLayoutInflater(AppCompatActivity activity){
        return LayoutInflater.from(activity);
    }

    @Provides
    public WindowStatusBarHelper getWindowStatusBarHelper(AppCompatActivity activity){
        return new WindowStatusBarHelper(activity);
    }

    @Provides
    public ToastHelper getToastHelper(AppCompatActivity activity){
        return new ToastHelper(activity);
    }

    @Provides
    public ViewMvcFactory getViewMvcFactory(LayoutInflater inflater){
        return new ViewMvcFactory(inflater);
    }

    @Provides
    public PaletteHelper getPaletteHelper(Resources resources){
        return new PaletteHelper(resources);
    }

    @Provides
    public ScreensNavigator getScreensNavigator(AppCompatActivity activity){
        return new ScreensNavigator(activity);
    }

    @Provides
    public InternetConnectionTester getInternetConnectionTester(AppCompatActivity activity){
        return new InternetConnectionTester(activity);
    }

    @Provides
    @ActivityScope
    public GPSLocationHelper getGPSLocationHelper(AppCompatActivity activity,ExecutorService executorService){
        return new GPSLocationHelper(activity,executorService);
    }

    @Provides
    @ActivityScope
    public GPSActivationHelper getGPSActivationHelper(AppCompatActivity activity){
        return new GPSActivationHelper(activity);
    }
}
