package com.akshansh.weatherapi.networking.city;

import com.akshansh.weatherapi.city.GetCitiesQueryEndpoint;
import com.akshansh.weatherapi.networking.NetworkException;
import com.akshansh.weatherapi.networking.citymodels.CitiesSchema;

import retrofit2.Call;
import retrofit2.Response;

public class GetCitiesService implements GetCitiesQueryEndpoint {
    private final CitiesAPI citiesAPI;

    public GetCitiesService(CitiesAPI citiesAPI) {
        this.citiesAPI = citiesAPI;
    }

    @Override
    public void fetchQueriedCities(String query, Callback callback) throws NetworkException {
        Call<CitiesSchema> result = citiesAPI.getQueriedCities(query);
        result.enqueue(new retrofit2.Callback<CitiesSchema>() {
            @Override
            public void onResponse(Call<CitiesSchema> call, Response<CitiesSchema> response) {
                if(response.isSuccessful()){
                    CitiesSchema result = response.body();
                    if(result != null) {
                        if (result.getDeliveryCode() == 100) {
                            callback.OnFetchSuccessful(result.getCities());
                        }else{
                            callback.OnQueriedCityNotFound();
                        }
                    }else{
                        callback.OnFetchFailure();
                    }
                }else{
                    callback.OnFetchFailure();
                }
            }

            @Override
            public void onFailure(Call<CitiesSchema> call, Throwable t) {
                callback.OnFetchFailure();
            }
        });
    }
}
