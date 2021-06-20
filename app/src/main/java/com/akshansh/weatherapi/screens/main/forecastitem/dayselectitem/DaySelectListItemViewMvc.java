package com.akshansh.weatherapi.screens.main.forecastitem.dayselectitem;

import com.akshansh.weatherapi.networking.weathermodels.WeatherForecastData;
import com.akshansh.weatherapi.networking.weathermodels.forecastdata.ForecastData;
import com.akshansh.weatherapi.screens.common.views.ObservableViewMvc;

import java.util.List;

public interface DaySelectListItemViewMvc extends ObservableViewMvc<DaySelectListItemViewMvc.Listener> {
    interface Listener{
        void OnDaySelected(String key);
    }
    void bindView(List<ForecastData> forecastData);
    void setSelected(boolean selected);
}
