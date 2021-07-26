package com.akshansh.weatherapi.common.dependecyinjection.application;

import com.akshansh.weatherapi.common.CustomApplication;
import com.akshansh.weatherapi.common.utils.WeatherDataSyncHelper;
import com.akshansh.weatherapi.networking.weather.OpenWeatherApi;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.akshansh.weatherapi.common.Constants.ENDPOINT;

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
    public Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @AppScope
    public OpenWeatherApi getOpenWeatherApi(Retrofit retrofit){
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
