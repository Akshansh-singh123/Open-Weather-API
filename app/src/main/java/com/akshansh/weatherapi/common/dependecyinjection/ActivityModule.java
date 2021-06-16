package com.akshansh.weatherapi.common.dependecyinjection;

import android.content.Context;
import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;

import com.akshansh.weatherapi.common.ViewMvcFactory;

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

    public LayoutInflater getLayoutInflater(){
        return LayoutInflater.from(activity);
    }

    public ViewMvcFactory getViewMvcFactory(){
        return new ViewMvcFactory(getLayoutInflater());
    }
}
