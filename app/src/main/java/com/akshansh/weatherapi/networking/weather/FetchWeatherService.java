package com.akshansh.weatherapi.networking.weather;

import com.akshansh.weatherapi.common.Constants;
import com.akshansh.weatherapi.common.utils.InternetConnectionTester;
import com.akshansh.weatherapi.common.utils.WeatherDataSyncHelper;
import com.akshansh.weatherapi.networking.NetworkException;
import com.akshansh.weatherapi.networking.weathermodels.CurrentWeatherData;
import com.akshansh.weatherapi.currentweather.FetchWeatherEndpoint;
import com.akshansh.weatherapi.networking.weathermodels.WeatherForecastData;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FetchWeatherService implements FetchWeatherEndpoint {
    private final Retrofit currentWeatherRetrofit;
    private final Retrofit weatherForecastRetrofit;
    private final WeatherDataSyncHelper weatherDataSyncHelper;
    private final InternetConnectionTester internetConnectionTester;

    public FetchWeatherService(Retrofit currentWeatherRetrofit,
                               Retrofit weatherForecastRetrofit,
                               WeatherDataSyncHelper weatherDataSyncHelper,
                               InternetConnectionTester internetConnectionTester) {
        this.currentWeatherRetrofit = currentWeatherRetrofit;
        this.weatherForecastRetrofit = weatherForecastRetrofit;
        this.weatherDataSyncHelper = weatherDataSyncHelper;
        this.internetConnectionTester = internetConnectionTester;
    }

    @Override
    public void fetchWeatherForecast(String city, String units, Callback callback)
            throws NetworkException {
        if(weatherDataSyncHelper.isSynced()){
            callback.OnFetchWeatherSuccessful(weatherDataSyncHelper.getWeatherDataSynced(),
                    weatherDataSyncHelper.getWeatherForecastDataSynced());
        }

        if (!internetConnectionTester.isConnected()){
            throw new NetworkException();
        }

        fetchCurrentWeather(city,units,callback);
    }

    private void fetchCurrentWeather(String city, String units, Callback callback) {
        OpenWeatherApi openWeatherApi = currentWeatherRetrofit.create(OpenWeatherApi.class);
        Call<CurrentWeatherData> call = openWeatherApi.getCurrentWeather(city, Constants.API_KEY,units);
        call.enqueue(new retrofit2.Callback<CurrentWeatherData>() {
            @Override
            public void onResponse(Call<CurrentWeatherData> call, Response<CurrentWeatherData> response) {
                if(!response.isSuccessful()) {
                    callback.OnFetchWeatherFailure();
                    return;
                }
                fetchForecast(city,units,callback,response.body());
            }

            @Override
            public void onFailure(Call<CurrentWeatherData> call, Throwable t) {
                callback.OnFetchWeatherFailure();
            }
        });
    }

    private void fetchForecast(String city, String units, Callback callback,
                               CurrentWeatherData currentWeatherData) {
        OpenWeatherApi openWeatherApi = weatherForecastRetrofit.create(OpenWeatherApi.class);
        Call<WeatherForecastData> call = openWeatherApi.getWeatherForecast(city,Constants.API_KEY,units);
        call.enqueue(new retrofit2.Callback<WeatherForecastData>() {
            @Override
            public void onResponse(Call<WeatherForecastData> call, Response<WeatherForecastData> response) {
                weatherDataSyncHelper.syncWeatherData(currentWeatherData,response.body());
                callback.OnFetchWeatherSuccessful(currentWeatherData,response.body());
            }

            @Override
            public void onFailure(Call<WeatherForecastData> call, Throwable t) {
                callback.OnFetchWeatherFailure();
            }
        });
    }
}
