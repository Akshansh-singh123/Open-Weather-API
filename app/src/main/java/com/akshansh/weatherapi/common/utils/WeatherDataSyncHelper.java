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
    private static final String LATITUDE_DATA = "LATITUDE_DATA";
    private static final String LONGITUDE_DATA = "LONGITUDE_DATA";
    private static final String CITY_NAME_DATA = "CITY_NAME_DATA";

    @Nullable
    private CurrentWeatherData weatherDataSynced;
    @Nullable
    private WeatherForecastData weatherForecastDataSynced;
    @Nullable
    private Double latitude;
    @Nullable
    private Double longitude;
    @Nullable
    private String city;

    private final SharedPreferences sharedPreferences;

    public WeatherDataSyncHelper(Context context) {
        weatherDataSynced = null;
        weatherForecastDataSynced = null;
        latitude = null;
        longitude = null;
        city = null;
        sharedPreferences = context.getSharedPreferences(FILE_KEY, Context.MODE_PRIVATE);
        loadData();
    }

    public void syncWeatherData(CurrentWeatherData weatherData,
                                WeatherForecastData weatherForecastData,
                                @Nullable Double latitude,@Nullable Double longitude,
                                @Nullable String city){
        weatherDataSynced = weatherData;
        weatherForecastDataSynced = weatherForecastData;
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
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

    @Nullable
    public Double getLatitude(){
        loadData();
        return latitude;
    }

    @Nullable
    public Double getLongitude() {
        loadData();
        return longitude;
    }

    @Nullable
    public String getCity() {
        loadData();
        return city;
    }

    private void writeData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String currentWeatherJson = gson.toJson(weatherDataSynced);
        String weatherForecastJson = gson.toJson(weatherForecastDataSynced);
        if(currentWeatherJson != null)
            editor.putString(CURRENT_WEATHER_DATA,currentWeatherJson);
        if(weatherForecastJson != null)
            editor.putString(WEATHER_FORECAST_DATA,weatherForecastJson);
        if(latitude != null)
            editor.putString(LATITUDE_DATA, String.valueOf(latitude));
        else
            editor.putString(LATITUDE_DATA,"");
        if(longitude != null)
            editor.putString(LONGITUDE_DATA, String.valueOf(longitude));
        else
            editor.putString(LONGITUDE_DATA, "");
        if(city != null)
            editor.putString(CITY_NAME_DATA, city);
        else
            editor.putString(CITY_NAME_DATA, "");
        editor.apply();
    }

    private void loadData(){
        String currentWeatherJson = sharedPreferences.getString(CURRENT_WEATHER_DATA,null);
        String weatherForecastJson = sharedPreferences.getString(WEATHER_FORECAST_DATA, null);
        String storedLatitude = sharedPreferences.getString(LATITUDE_DATA,null);
        String storedLongitude = sharedPreferences.getString(LONGITUDE_DATA,null);
        String storedCity = sharedPreferences.getString(CITY_NAME_DATA, null);
        Gson gson = new Gson();
        if(currentWeatherJson != null)
            weatherDataSynced = gson.fromJson(currentWeatherJson,CurrentWeatherData.class);
        if(weatherForecastJson != null)
            weatherForecastDataSynced = gson.fromJson(weatherForecastJson,WeatherForecastData.class);
        if(storedLatitude != null) {
            latitude = storedLatitude.equals("")?null:Double.parseDouble(storedLatitude);
        }
        if(storedLongitude != null) {
            longitude = storedLongitude.equals("")?null:Double.parseDouble(storedLongitude);
        }
        if(storedCity != null) {
            city = storedCity.equals("") ? null : storedCity;
        }
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
