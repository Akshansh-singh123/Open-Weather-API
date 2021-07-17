package com.akshansh.weatherapi.common.dependecyinjection;

import com.akshansh.weatherapi.common.CustomApplication;
import com.akshansh.weatherapi.common.utils.WeatherDataSyncHelper;
import com.google.gson.Gson;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.akshansh.weatherapi.common.Constants.ENDPOINT;

public class ApplicationModule {
    private final CustomApplication application;
    private WeatherDataSyncHelper weatherDataSyncHelper;
    private Retrofit currentWeatherRetrofitByCity;
    private Retrofit weatherForecastRetrofitByCity;
    private Retrofit currentWeatherRetrofitByLocation;
    private Retrofit weatherForecastRetrofitByLocation;
    private ExecutorService executor;

    public ApplicationModule(CustomApplication application) {
        this.application = application;
    }

    public ExecutorService getExecutor(){
        if(executor == null){
            executor = Executors
                    .newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        }
        return executor;
    }

    public Retrofit getCurrentWeatherRetrofitByCity() {
        if(currentWeatherRetrofitByCity == null){
            currentWeatherRetrofitByCity = new Retrofit.Builder()
                    .baseUrl(ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return currentWeatherRetrofitByCity;
    }

    public Retrofit getWeatherForecastRetrofitByCity() {
        if(weatherForecastRetrofitByCity == null){
            weatherForecastRetrofitByCity = new Retrofit.Builder()
                    .baseUrl(ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return weatherForecastRetrofitByCity;
    }

    public Retrofit getCurrentWeatherRetrofitByLocation(){
        if(currentWeatherRetrofitByLocation == null){
            currentWeatherRetrofitByCity = new Retrofit.Builder()
                    .baseUrl(ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return currentWeatherRetrofitByLocation;
    }

    public Retrofit getWeatherForecastRetrofitByLocation() {
        if(weatherForecastRetrofitByLocation == null){
            weatherForecastRetrofitByLocation = new Retrofit.Builder()
                    .baseUrl(ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return weatherForecastRetrofitByLocation;
    }

    public WeatherDataSyncHelper getWeatherDataSyncHelper() {
        if(weatherDataSyncHelper == null){
            weatherDataSyncHelper = new WeatherDataSyncHelper(application.getApplicationContext());
        }
        return weatherDataSyncHelper;
    }
}
