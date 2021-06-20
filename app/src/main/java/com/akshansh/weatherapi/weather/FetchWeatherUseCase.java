package com.akshansh.weatherapi.weather;

import com.akshansh.weatherapi.common.BaseObservable;
import com.akshansh.weatherapi.networking.NetworkException;
import com.akshansh.weatherapi.networking.weathermodels.CurrentWeatherData;
import com.akshansh.weatherapi.networking.weathermodels.WeatherForecastData;

public class FetchWeatherUseCase extends BaseObservable<FetchWeatherUseCase.Listener> {
    public interface Listener{
        void OnFetchWeatherSuccessful(CurrentWeatherData weatherData,
                                      WeatherForecastData weatherForecastData);
        void OnFetchWeatherFailure();
        void OnFetchWeatherNetworkError();
    }

    private final FetchWeatherEndpoint fetchWeatherEndpoint;

    public FetchWeatherUseCase(FetchWeatherEndpoint fetchWeatherEndpoint) {
        this.fetchWeatherEndpoint = fetchWeatherEndpoint;
    }

    public void fetchWeatherForecast(String city, String units){
        try {
            fetchWeatherEndpoint.fetchWeatherForecast(city,units,new FetchWeatherEndpoint.Callback() {
                @Override
                public void OnFetchWeatherSuccessful(CurrentWeatherData currentWeatherData,
                                                     WeatherForecastData weatherForecastData) {
                    for(Listener listener: getListeners()){
                        listener.OnFetchWeatherSuccessful(currentWeatherData,weatherForecastData);
                    }
                }

                @Override
                public void OnFetchWeatherFailure() {
                    for(Listener listener: getListeners()){
                        listener.OnFetchWeatherFailure();
                    }
                }
            });
        } catch (NetworkException e) {
            for(Listener listener: getListeners()){
                listener.OnFetchWeatherNetworkError();
            }
        }
    }
}
