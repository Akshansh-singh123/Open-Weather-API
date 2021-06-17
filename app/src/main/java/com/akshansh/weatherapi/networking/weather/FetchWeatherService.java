package com.akshansh.weatherapi.networking.weather;

import com.akshansh.weatherapi.common.Constants;
import com.akshansh.weatherapi.networking.NetworkException;
import com.akshansh.weatherapi.networking.weathermodels.CurrentWeatherData;
import com.akshansh.weatherapi.weather.FetchWeatherEndpoint;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FetchWeatherService implements FetchWeatherEndpoint {
    private final Retrofit retrofit;

    public FetchWeatherService(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    @Override
    public void fetchWeatherForecast(String city, String units, Callback callback)
            throws NetworkException {
        OpenWeatherApi openWeatherApi = retrofit.create(OpenWeatherApi.class);
        Call<CurrentWeatherData> call = openWeatherApi.getWeather(city, Constants.API_KEY,units);
        call.enqueue(new retrofit2.Callback<CurrentWeatherData>() {
            @Override
            public void onResponse(Call<CurrentWeatherData> call, Response<CurrentWeatherData> response) {
                if(!response.isSuccessful()) {
                    callback.OnFetchWeatherFailure();
                    return;
                }
                callback.OnFetchWeatherSuccessful(response.body());
            }

            @Override
            public void onFailure(Call<CurrentWeatherData> call, Throwable t) {
                callback.OnFetchWeatherFailure();
            }
        });
    }
}
