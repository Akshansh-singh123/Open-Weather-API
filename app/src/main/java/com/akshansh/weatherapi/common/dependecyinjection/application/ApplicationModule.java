package com.akshansh.weatherapi.common.dependecyinjection.application;

import com.akshansh.weatherapi.common.Constants;
import com.akshansh.weatherapi.common.CustomApplication;
import com.akshansh.weatherapi.common.dependecyinjection.CitiesApiRetrofit;
import com.akshansh.weatherapi.common.dependecyinjection.OpenWeatherApiRetrofit;
import com.akshansh.weatherapi.common.utils.WeatherDataSyncHelper;
import com.akshansh.weatherapi.networking.city.CitiesAPI;
import com.akshansh.weatherapi.networking.weather.OpenWeatherApi;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.akshansh.weatherapi.common.Constants.OPEN_WEATHER_API_ENDPOINT;

@Module
public class ApplicationModule {
    private final CustomApplication application;

    public ApplicationModule(CustomApplication application) {
        this.application = application;
    }

    @Provides
    @AppScope
    public ExecutorService getExecutor(){
        return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    @Provides
    @AppScope
    @OpenWeatherApiRetrofit
    public Retrofit getOpenWeatherRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(OPEN_WEATHER_API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @AppScope
    @CitiesApiRetrofit
    public Retrofit getCitiesApiRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(Constants.CITIES_API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @AppScope
    public CitiesAPI getCitiesApi(@CitiesApiRetrofit Retrofit retrofit){
        return retrofit.create(CitiesAPI.class);
    }

    @Provides
    @AppScope
    public OpenWeatherApi getOpenWeatherApi(@OpenWeatherApiRetrofit Retrofit retrofit){
        return retrofit.create(OpenWeatherApi.class);
    }

    @Provides
    @AppScope
    public CustomApplication getApplication(){
        return application;
    }

    @Provides
    @AppScope
    public WeatherDataSyncHelper getWeatherDataSyncHelper(CustomApplication application) {
        return new WeatherDataSyncHelper(application);
    }
}
