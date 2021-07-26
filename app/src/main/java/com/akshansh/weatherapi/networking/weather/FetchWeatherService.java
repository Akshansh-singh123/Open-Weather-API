package com.akshansh.weatherapi.networking.weather;

import com.akshansh.weatherapi.common.Constants;
import com.akshansh.weatherapi.common.utils.InternetConnectionTester;
import com.akshansh.weatherapi.common.utils.WeatherDataSyncHelper;
import com.akshansh.weatherapi.networking.NetworkException;
import com.akshansh.weatherapi.networking.weathermodels.CurrentWeatherData;
import com.akshansh.weatherapi.weather.FetchWeatherEndpoint;
import com.akshansh.weatherapi.networking.weathermodels.WeatherForecastData;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FetchWeatherService implements FetchWeatherEndpoint {
    private OpenWeatherApi openWeatherApi;
    private final WeatherDataSyncHelper weatherDataSyncHelper;
    private final InternetConnectionTester internetConnectionTester;

    public FetchWeatherService(OpenWeatherApi openWeatherApi,
                               WeatherDataSyncHelper weatherDataSyncHelper,
                               InternetConnectionTester internetConnectionTester) {
        this.openWeatherApi = openWeatherApi;
        this.weatherDataSyncHelper = weatherDataSyncHelper;
        this.internetConnectionTester = internetConnectionTester;
    }

    @Override
    public void fetchWeatherForecastByCity(String city, String units, Callback callback)
            throws NetworkException {
        if(weatherDataSyncHelper.isSynced()){
            callback.OnFetchWeatherSuccessful(weatherDataSyncHelper.getWeatherDataSynced(),
                    weatherDataSyncHelper.getWeatherForecastDataSynced());
        }

        if (!internetConnectionTester.isConnected()){
            throw new NetworkException();
        }

        fetchCurrentWeatherByCity(city,units,callback);
    }

    @Override
    public void fetchWeatherForecastByLocation(double latitude, double longitude,
                                               String units,
                                               Callback callback) throws NetworkException {
        if(weatherDataSyncHelper.isSynced()){
            callback.OnFetchWeatherSuccessful(weatherDataSyncHelper.getWeatherDataSynced(),
                    weatherDataSyncHelper.getWeatherForecastDataSynced());
        }

        if (!internetConnectionTester.isConnected()){
            throw new NetworkException();
        }

        fetchCurrentWeatherByLocation(latitude,longitude,units,callback);
    }

    private void fetchCurrentWeatherByLocation(double latitude, double longitude,
                                               String units, Callback callback) {
        Call<CurrentWeatherData> call = openWeatherApi.getCurrentWeatherByLocation(
                String.valueOf(latitude),
                String.valueOf(longitude),
                Constants.API_KEY,units);
        call.enqueue(new retrofit2.Callback<CurrentWeatherData>() {
            @Override
            public void onResponse(Call<CurrentWeatherData> call, Response<CurrentWeatherData> response) {
                if(!response.isSuccessful()) {
                    callback.OnFetchWeatherFailure();
                    return;
                }
                fetchForecastByLocation(latitude,longitude,
                        units,callback,response.body());
            }

            @Override
            public void onFailure(Call<CurrentWeatherData> call, Throwable t) {
                callback.OnFetchWeatherFailure();
            }
        });
    }

    private void fetchForecastByLocation(double latitude, double longitude,
                                         String units, Callback callback,
                                         CurrentWeatherData currentWeatherData) {
        Call<WeatherForecastData> call = openWeatherApi
                .getWeatherForecastByLocation(String.valueOf(latitude),
                        String.valueOf(longitude),
                        Constants.API_KEY,units);
        call.enqueue(new retrofit2.Callback<WeatherForecastData>() {
            @Override
            public void onResponse(Call<WeatherForecastData> call,
                                   Response<WeatherForecastData> response) {
                weatherDataSyncHelper.syncWeatherData(currentWeatherData,response.body(),
                        latitude,longitude,null);
                callback.OnFetchWeatherSuccessful(currentWeatherData,response.body());
            }

            @Override
            public void onFailure(Call<WeatherForecastData> call, Throwable t) {
                callback.OnFetchWeatherFailure();
            }
        });
    }

    private void fetchCurrentWeatherByCity(String city, String units, Callback callback) {
        Call<CurrentWeatherData> call = openWeatherApi.getCurrentWeatherByCityName(city, Constants.API_KEY,units);
        call.enqueue(new retrofit2.Callback<CurrentWeatherData>() {
            @Override
            public void onResponse(Call<CurrentWeatherData> call, Response<CurrentWeatherData> response) {
                if(!response.isSuccessful()) {
                    callback.OnFetchWeatherFailure();
                    return;
                }
                fetchForecastByCityName(city,units,callback,response.body());
            }

            @Override
            public void onFailure(Call<CurrentWeatherData> call, Throwable t) {
                callback.OnFetchWeatherFailure();
            }
        });
    }

    private void fetchForecastByCityName(String city, String units, Callback callback,
                                         CurrentWeatherData currentWeatherData) {
        Call<WeatherForecastData> call = openWeatherApi.getWeatherForecastByCityName(city,Constants.API_KEY,units);
        call.enqueue(new retrofit2.Callback<WeatherForecastData>() {
            @Override
            public void onResponse(Call<WeatherForecastData> call, Response<WeatherForecastData> response) {
                weatherDataSyncHelper.syncWeatherData(currentWeatherData,response.body(),
                        null,null,city);
                callback.OnFetchWeatherSuccessful(currentWeatherData,response.body());
            }

            @Override
            public void onFailure(Call<WeatherForecastData> call, Throwable t) {
                callback.OnFetchWeatherFailure();
            }
        });
    }
}
