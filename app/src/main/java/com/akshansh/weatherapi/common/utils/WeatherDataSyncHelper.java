package com.akshansh.weatherapi.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import com.akshansh.weatherapi.networking.weathermodels.CurrentWeatherData;
import com.akshansh.weatherapi.networking.weathermodels.WeatherForecastData;
import com.google.gson.Gson;

public class WeatherDataSyncHelper {
    private static final String FILE_KEY = "CURRENT_WEATHER_DATA";
    private static final String CURRENT_WEATHER_DATA = "CURRENT_WEATHER_DATA_JSON";
    private static final String WEATHER_FORECAST_DATA = "WEATHER_FORECAST_DATA_JSON";

    @Nullable
    private CurrentWeatherData weatherDataSynced;
    @Nullable
    private WeatherForecastData weatherForecastDataSynced;
    private final SharedPreferences sharedPreferences;

    public WeatherDataSyncHelper(Context context) {
        weatherDataSynced = null;
        weatherForecastDataSynced = null;
        sharedPreferences = context.getSharedPreferences(FILE_KEY, Context.MODE_PRIVATE);
        loadData();
    }

    public void syncWeatherData(CurrentWeatherData weatherData, WeatherForecastData weatherForecastData){
        weatherDataSynced = weatherData;
        weatherForecastDataSynced = weatherForecastData;
        writeData();
    }

    @Nullable
    public CurrentWeatherData getWeatherDataSynced() {
        loadData();
        return weatherDataSynced;
    }

    @Nullable
    public WeatherForecastData getWeatherForecastDataSynced() {
        loadData();
        return weatherForecastDataSynced;
    }

    private void writeData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String currentWeatherJson = gson.toJson(weatherDataSynced);
        String weatherForecastJson = gson.toJson(weatherForecastDataSynced);
        editor.putString(CURRENT_WEATHER_DATA,currentWeatherJson);
        editor.putString(WEATHER_FORECAST_DATA,weatherForecastJson);
        editor.apply();
    }

    private void loadData(){
        String currentWeatherJson = sharedPreferences.getString(CURRENT_WEATHER_DATA,null);
        String weatherForecastJson = sharedPreferences.getString(WEATHER_FORECAST_DATA, null);
        Gson gson = new Gson();
        if(currentWeatherJson != null)
            weatherDataSynced = gson.fromJson(currentWeatherJson,CurrentWeatherData.class);
        if(weatherForecastJson != null)
            weatherForecastDataSynced = gson.fromJson(weatherForecastJson,WeatherForecastData.class);
    }

    public void clearLocalWeatherData(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public boolean isSynced(){
        return weatherDataSynced != null && weatherForecastDataSynced != null;
    }
}
