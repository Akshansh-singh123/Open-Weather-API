package com.akshansh.weatherapi.screens.main.forecastitem;

import com.akshansh.weatherapi.networking.weathermodels.WeatherForecastData;
import com.akshansh.weatherapi.screens.common.views.ViewMvc;

public interface ForecastListItemViewMvc extends ViewMvc {
    void bindView(WeatherForecastData weatherForecastData);
    void setProgressBarVisible(boolean visible);
}
