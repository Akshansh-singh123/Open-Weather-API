package com.akshansh.weatherapi.currentweather;

import com.akshansh.weatherapi.networking.NetworkException;
import com.akshansh.weatherapi.networking.weathermodels.CurrentWeatherData;

public interface FetchWeatherEndpoint {
    interface Callback{
        void OnFetchWeatherSuccessful(CurrentWeatherData currentWeatherData);
        void OnFetchWeatherFailure();
    }

    void fetchWeatherForecast(String city, String units, Callback callback) throws NetworkException;
}
