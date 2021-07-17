package com.akshansh.weatherapi.networking.weather;

import com.akshansh.weatherapi.networking.weathermodels.CurrentWeatherData;
import com.akshansh.weatherapi.networking.weathermodels.WeatherForecastData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherApi {
    @GET("weather")
    Call<CurrentWeatherData> getCurrentWeatherByCityName(
            @Query("q") String city,
            @Query("appid") String apiKey,
            @Query("units") String units);

    @GET("forecast")
    Call<WeatherForecastData> getWeatherForecastByCityName(
            @Query("q") String city,
            @Query("appid") String apiKey,
            @Query("units") String units);

    @GET("weather")
    Call<CurrentWeatherData> getCurrentWeatherByLocation(
            @Query("lat") String latitude,
            @Query("lon") String longitude,
            @Query("appid") String apiKey,
            @Query("units") String units
    );

    @GET("forecast")
    Call<WeatherForecastData> getWeatherForecastByLocation(
            @Query("lat") String latitude,
            @Query("lon") String longitude,
            @Query("appid") String apiKey,
            @Query("units") String units
    );
}
