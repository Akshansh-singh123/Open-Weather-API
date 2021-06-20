package com.akshansh.weatherapi.networking.weather;

import com.akshansh.weatherapi.networking.weathermodels.CurrentWeatherData;
import com.akshansh.weatherapi.networking.weathermodels.WeatherForecastData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherApi {
    @GET("weather")
    Call<CurrentWeatherData> getCurrentWeather(
            @Query("q") String city,
            @Query("appid") String apiKey,
            @Query("units") String units);

    @GET("forecast")
    Call<WeatherForecastData> getWeatherForecast(
            @Query("q") String city,
            @Query("appid") String apiKey,
            @Query("units") String units);
}
