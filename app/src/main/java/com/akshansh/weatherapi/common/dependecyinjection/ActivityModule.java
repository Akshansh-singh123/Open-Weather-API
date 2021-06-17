package com.akshansh.weatherapi.common.dependecyinjection;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;

import com.akshansh.weatherapi.common.ViewMvcFactory;
import com.akshansh.weatherapi.common.graphics.PaletteHelper;
import com.akshansh.weatherapi.common.graphics.WindowStatusBarHelper;
import com.akshansh.weatherapi.screens.common.toast.ToastHelper;

import retrofit2.Retrofit;

public class ActivityModule {
    private final AppCompatActivity activity;
    private final ApplicationModule applicationModule;

    public ActivityModule(ApplicationModule applicationModule, AppCompatActivity activity) {
        this.applicationModule = applicationModule;
        this.activity = activity;
    }

    public Retrofit getRetrofit(){
        return applicationModule.getRetrofit();
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
}
