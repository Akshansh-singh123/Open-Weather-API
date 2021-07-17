package com.akshansh.weatherapi.screens.sync;

import android.os.Bundle;

import com.akshansh.weatherapi.networking.weathermodels.WeatherForecastData;
import com.akshansh.weatherapi.screens.common.screensnavigator.ScreensNavigator;
import com.akshansh.weatherapi.common.utils.WeatherDataSyncHelper;
import com.akshansh.weatherapi.databinding.ActivitySyncBinding;
import com.akshansh.weatherapi.networking.weathermodels.CurrentWeatherData;
import com.akshansh.weatherapi.screens.common.BaseActivity;
import com.akshansh.weatherapi.screens.common.toast.ToastHelper;
import com.akshansh.weatherapi.weather.FetchWeatherUseCase;

public class SyncActivity extends BaseActivity implements FetchWeatherUseCase.Listener {
    private ActivitySyncBinding binding;
    private FetchWeatherUseCase fetchWeatherUseCase;
    private ToastHelper toastHelper;
    private WeatherDataSyncHelper weatherDataSyncHelper;
    private ScreensNavigator screensNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySyncBinding.inflate(getLayoutInflater(),null,false);
        setContentView(binding.getRoot());

        fetchWeatherUseCase = getInjector().getFetchWeatherUseCase();
        weatherDataSyncHelper = getInjector().getWeatherDataSyncHelper();
        toastHelper = getInjector().getToastHelper();
        screensNavigator = getInjector().getScreensNavigator();
    }

    @Override
    protected void onStart() {
        super.onStart();
        fetchWeatherUseCase.registerListener(this);
        if(weatherDataSyncHelper.isSynced()) {
            screensNavigator.syncToMainActivity();
            return;
        }
        // TODO: 14-07-2021 Change method calls here,fetch location here
        fetchWeatherUseCase.fetchWeatherForecastByCityName("Jamshedpur", "metric");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fetchWeatherUseCase.unregisterListener(this);
        binding = null;
    }

    @Override
    public void OnFetchWeatherSuccessful(CurrentWeatherData weatherData,
                                         WeatherForecastData weatherForecastData) {
        screensNavigator.syncToMainActivity();
    }

    @Override
    public void OnFetchWeatherFailure() {
        toastHelper.makeToast("Could not load weather data. Try again after sometime.");
    }

    @Override
    public void OnFetchWeatherNetworkError() {
        toastHelper.makeToast("Could not load weather data. Check the internet connection.");
    }
}