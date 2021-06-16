package com.akshansh.weatherapi.networking.weather;

import com.akshansh.weatherapi.networking.NetworkException;
import com.akshansh.weatherapi.weather.FetchWeatherEndpoint;

public class FetchWeatherService implements FetchWeatherEndpoint {

    @Override
    public void fetchWeatherForecast(String city, String units, Callback callback)
            throws NetworkException {

    }
}
