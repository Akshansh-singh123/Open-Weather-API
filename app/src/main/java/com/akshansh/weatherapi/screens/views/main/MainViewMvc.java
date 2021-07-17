package com.akshansh.weatherapi.screens.views.main;

import com.akshansh.weatherapi.networking.weathermodels.CurrentWeatherData;
import com.akshansh.weatherapi.networking.weathermodels.WeatherForecastData;
import com.akshansh.weatherapi.screens.common.views.ObservableViewMvc;

public interface MainViewMvc extends ObservableViewMvc<MainViewMvc.Listener> {
    interface Listener{
        void OnRefresh();
    }
    void bindView(CurrentWeatherData weatherData, WeatherForecastData weatherForecastData);
    void clearBinding();
    void stopRefreshing();
    void setBackgroundImage(int resId);
}
