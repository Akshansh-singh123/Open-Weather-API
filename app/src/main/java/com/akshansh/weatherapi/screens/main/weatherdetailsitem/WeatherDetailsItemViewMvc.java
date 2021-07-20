package com.akshansh.weatherapi.screens.main.weatherdetailsitem;

import com.akshansh.weatherapi.networking.weathermodels.CurrentWeatherData;
import com.akshansh.weatherapi.screens.common.views.ViewMvc;

public interface WeatherDetailsItemViewMvc extends ViewMvc {
    void bindView(CurrentWeatherData weatherData);
}
