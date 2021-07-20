package com.akshansh.weatherapi.screens.main.headeritem;

import com.akshansh.weatherapi.networking.weathermodels.CurrentWeatherData;
import com.akshansh.weatherapi.screens.common.views.ViewMvc;

public interface HeaderListItemViewMvc extends ViewMvc {
    void bindView(CurrentWeatherData weatherData);
}
