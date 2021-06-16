package com.akshansh.weatherapi.screens.main;

import com.akshansh.weatherapi.networking.weathermodels.CurrentWeatherData;

public interface MainViewMvc {
    interface Listener{
        void OnRefresh();
    }
    void bindView(CurrentWeatherData weatherData);
    void clearBinding();
}
