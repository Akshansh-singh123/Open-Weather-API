package com.akshansh.weatherapi.networking.currentweather;

import com.akshansh.weatherapi.common.Constants;
import com.akshansh.weatherapi.common.utils.InternetConnectionTester;
import com.akshansh.weatherapi.common.utils.WeatherDataSyncHelper;
import com.akshansh.weatherapi.networking.NetworkException;
import com.akshansh.weatherapi.networking.weathermodels.CurrentWeatherData;
import com.akshansh.weatherapi.currentweather.FetchWeatherEndpoint;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FetchWeatherService implements FetchWeatherEndpoint {
    private final Retrofit retrofit;
    private final WeatherDataSyncHelper weatherDataSyncHelper;
    private final InternetConnectionTester internetConnectionTester;


    public FetchWeatherService(Retrofit retrofit, WeatherDataSyncHelper weatherDataSyncHelper,
                               InternetConnectionTester internetConnectionTester) {
        this.retrofit = retrofit;
        this.weatherDataSyncHelper = weatherDataSyncHelper;
        this.internetConnectionTester = internetConnectionTester;
    }

    @Override
    public void fetchWeatherForecast(String city, String units, Callback callback)
            throws NetworkException {
        CurrentWeatherData syncedWeatherData = weatherDataSyncHelper.getWeatherDataSynced();
        if(syncedWeatherData != null){
            callback.OnFetchWeatherSuccessful(syncedWeatherData);
        }

        if (!internetConnectionTester.isConnected()){
            throw new NetworkException();
        }

        OpenWeatherApi openWeatherApi = retrofit.create(OpenWeatherApi.class);
        Call<CurrentWeatherData> call = openWeatherApi.getWeather(city, Constants.API_KEY,units);
        call.enqueue(new retrofit2.Callback<CurrentWeatherData>() {
            @Override
            public void onResponse(Call<CurrentWeatherData> call, Response<CurrentWeatherData> response) {
                if(!response.isSuccessful()) {
                    callback.OnFetchWeatherFailure();
                    return;
                }
                weatherDataSyncHelper.syncWeatherData(response.body());
                callback.OnFetchWeatherSuccessful(response.body());
            }

            @Override
            public void onFailure(Call<CurrentWeatherData> call, Throwable t) {
                callback.OnFetchWeatherFailure();
            }
        });
    }
}
