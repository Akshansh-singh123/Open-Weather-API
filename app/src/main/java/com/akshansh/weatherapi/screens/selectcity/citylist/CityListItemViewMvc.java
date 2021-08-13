package com.akshansh.weatherapi.screens.selectcity.citylist;

import com.akshansh.weatherapi.networking.citymodels.City;
import com.akshansh.weatherapi.screens.common.views.ObservableViewMvc;

public interface CityListItemViewMvc extends ObservableViewMvc<CityListItemViewMvc.Listener> {
    interface Listener{
        void OnItemSelected(City city);
    }
    void bindView(City city);
    void bindCityName(String city);
    void bindCountryName(String country);
}
