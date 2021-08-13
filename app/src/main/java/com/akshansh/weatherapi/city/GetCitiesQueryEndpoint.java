package com.akshansh.weatherapi.city;

import com.akshansh.weatherapi.networking.NetworkException;
import com.akshansh.weatherapi.networking.citymodels.City;

import java.util.List;

public interface GetCitiesQueryEndpoint {
    interface Callback{
        void OnFetchSuccessful(List<City> citiesSchemas);
        void OnQueriedCityNotFound();
        void OnFetchFailure();
    }

    void fetchQueriedCities(String query,Callback callback) throws NetworkException;
}
