package com.akshansh.weatherapi.networking.city;

import com.akshansh.weatherapi.networking.citymodels.CitiesSchema;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CitiesAPI {
    @GET("cities")
    Call<CitiesSchema> getQueriedCities(@Query("q") String queryString);
}
