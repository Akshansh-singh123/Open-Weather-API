package com.akshansh.weatherapi.common.dependecyinjection;

import com.akshansh.weatherapi.common.CustomApplication;
import com.akshansh.weatherapi.common.utils.WeatherDataSyncHelper;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.akshansh.weatherapi.common.Constants.ENDPOINT;

public class ApplicationModule {
    private final CustomApplication application;
    private WeatherDataSyncHelper weatherDataSyncHelper;
    private Retrofit currentWeatherRetrofit;
    private Retrofit weatherForecastRetrofit;

    public ApplicationModule(CustomApplication application) {
        this.application = application;
    }

    public Retrofit getCurrentWeatherRetrofit() {
        if(currentWeatherRetrofit == null){
            currentWeatherRetrofit = new Retrofit.Builder()
                    .baseUrl(ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return currentWeatherRetrofit;
    }

    public Retrofit getWeatherForecastRetrofit() {
        if(weatherForecastRetrofit == null){
            weatherForecastRetrofit = new Retrofit.Builder()
                    .baseUrl(ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return weatherForecastRetrofit;
    }

    public WeatherDataSyncHelper getWeatherDataSyncHelper() {
        if(weatherDataSyncHelper == null){
            weatherDataSyncHelper = new WeatherDataSyncHelper(application.getApplicationContext());
        }
        return weatherDataSyncHelper;
    }
}
