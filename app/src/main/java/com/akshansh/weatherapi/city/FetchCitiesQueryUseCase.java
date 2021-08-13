package com.akshansh.weatherapi.city;


import com.akshansh.weatherapi.common.BaseObservable;
import com.akshansh.weatherapi.networking.NetworkException;
import com.akshansh.weatherapi.networking.citymodels.City;

import java.util.List;

public class FetchCitiesQueryUseCase extends BaseObservable<FetchCitiesQueryUseCase.Listener> {
    public interface Listener{
        void OnCityFetchSuccessful(List<City> cities);
        void OnQueriedCityNotFound();
        void OnFetchFailure();
        void OnNetworkError();
    }

    private final GetCitiesQueryEndpoint endpoint;

    public FetchCitiesQueryUseCase(GetCitiesQueryEndpoint endpoint) {
        this.endpoint = endpoint;
    }

    public void fetchQueriedCities(String query){
        try {
            endpoint.fetchQueriedCities(query, new GetCitiesQueryEndpoint.Callback() {
                @Override
                public void OnFetchSuccessful(List<City> cities) {
                    for(Listener listener: getListeners()){
                        listener.OnCityFetchSuccessful(cities);
                    }
                }

                @Override
                public void OnQueriedCityNotFound() {
                    for(Listener listener: getListeners()){
                        listener.OnQueriedCityNotFound();
                    }
                }

                @Override
                public void OnFetchFailure() {
                    for(Listener listener: getListeners()){
                        listener.OnFetchFailure();
                    }
                }
            });
        } catch (NetworkException e) {
            for(Listener listener: getListeners()){
                listener.OnNetworkError();
            }
        }
    }
}
