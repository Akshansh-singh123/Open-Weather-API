package com.akshansh.weatherapi.screens.main;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;

import com.akshansh.weatherapi.common.ViewMvcFactory;
import com.akshansh.weatherapi.networking.weather.OpenWeatherApi;
import com.akshansh.weatherapi.R;
import com.akshansh.weatherapi.networking.weathermodels.CurrentWeatherData;
import com.akshansh.weatherapi.screens.common.BaseActivity;
import com.akshansh.weatherapi.screens.common.toast.ToastHelper;
import com.akshansh.weatherapi.screens.common.toolbar.ToolbarViewMvc;
import com.akshansh.weatherapi.weather.FetchWeatherUseCase;

public class MainActivity extends BaseActivity implements MainViewMvc.Listener,
        FetchWeatherUseCase.Listener {
    private MainViewMvc viewMvc;
    private FetchWeatherUseCase fetchWeatherUseCase;
    private ToastHelper toastHelper;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewMvc = getInjector().getViewMvcFactory().getMainViewMvc(null);
        fetchWeatherUseCase = getInjector().getFetchWeatherUseCase();
        toastHelper = getInjector().getToastHelper();
        setContentView(viewMvc.getRootView());
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewMvc.registerListener(this);
        fetchWeatherUseCase.registerListener(this);
        fetchWeatherUseCase.fetchWeatherForecast("Jamshedpur","metric");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewMvc.unregisterListener(this);
        fetchWeatherUseCase.unregisterListener(this);
        viewMvc.clearBinding();
    }

    @Override
    public void OnRefresh() {
        fetchWeatherUseCase.fetchWeatherForecast("Jamshedpur","metric");
    }

    @Override
    public void OnFetchWeatherSuccessful(CurrentWeatherData weatherData) {
        Log.e(TAG, "OnFetchWeatherSuccessful: "+weatherData);
        viewMvc.bindView(weatherData);
        viewMvc.stopRefreshing();
    }

    @Override
    public void OnFetchWeatherFailure() {
        toastHelper.makeToast("Could not fetch the latest weather");
        viewMvc.stopRefreshing();
    }

    @Override
    public void OnFetchWeatherNetworkError() {
        toastHelper.makeToast("Could not connect to the internet");
        viewMvc.stopRefreshing();
    }
}