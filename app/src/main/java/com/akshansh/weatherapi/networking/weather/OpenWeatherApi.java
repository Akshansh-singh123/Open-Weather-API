package com.akshansh.weatherapi.networking.weather;

import com.akshansh.weatherapi.networking.weathermodels.CurrentWeatherData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherApi {
    @GET("weather")
    Call<CurrentWeatherData> getWeather(
            @Query("q") String city,
            @Query("appid") String apiKey,
            @Query("units") String units);
}
