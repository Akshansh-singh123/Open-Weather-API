package com.akshansh.weatherapi.screens.main;

import com.akshansh.weatherapi.common.ViewMvcFactory;
import com.akshansh.weatherapi.networking.weathermodels.CurrentWeatherData;
import com.akshansh.weatherapi.screens.common.views.ObservableViewMvc;
import com.akshansh.weatherapi.screens.common.views.ViewMvc;

public interface MainViewMvc extends ObservableViewMvc<MainViewMvc.Listener> {
    interface Listener{
        void OnRefresh();
    }
    void bindView(CurrentWeatherData weatherData);
    void clearBinding();
}
