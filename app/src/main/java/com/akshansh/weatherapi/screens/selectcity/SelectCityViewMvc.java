package com.akshansh.weatherapi.screens.selectcity;

import com.akshansh.weatherapi.networking.citymodels.City;
import com.akshansh.weatherapi.screens.common.views.ObservableViewMvc;

import java.util.List;

public interface SelectCityViewMvc extends ObservableViewMvc<SelectCityViewMvc.Listener> {
    interface Listener{
        void onTextTyped(String text);
        void onCitySelected(City city);
    }
    void bindView(List<City> cities);
    void cityNotFound();
    void showProgressBar();
    void hideProgressBar();
}
