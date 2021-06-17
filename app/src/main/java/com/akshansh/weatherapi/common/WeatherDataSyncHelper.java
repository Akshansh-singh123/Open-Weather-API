package com.akshansh.weatherapi.common;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import com.akshansh.weatherapi.networking.weathermodels.CurrentWeatherData;
import com.google.gson.Gson;

public class WeatherDataSyncHelper {
    private static final String FILE_KEY = "CURRENT_WEATHER_DATA";
    private static final String CURRENT_WEATHER_DATA = "CURRENT_WEATHER_DATA_JSON";

    @Nullable
    private CurrentWeatherData weatherDataSynced;
    private final SharedPreferences sharedPreferences;

    public WeatherDataSyncHelper(Context context) {
        weatherDataSynced = null;
        sharedPreferences = context.getSharedPreferences(FILE_KEY, Context.MODE_PRIVATE);
        loadData();
    }

    public void syncWeatherData(CurrentWeatherData weatherData){
        weatherDataSynced = weatherData;
        writeData();
    }

    @Nullable
    public CurrentWeatherData getWeatherDataSynced() {
        loadData();
        return weatherDataSynced;
    }

    private void writeData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(weatherDataSynced);
        editor.putString(CURRENT_WEATHER_DATA,json);
        editor.apply();
    }

    private void loadData(){
        String json = sharedPreferences.getString(CURRENT_WEATHER_DATA,null);
        Gson gson = new Gson();
        if(json != null)
            weatherDataSynced = gson.fromJson(json,CurrentWeatherData.class);
    }

    public void clearLocalWeatherData(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
