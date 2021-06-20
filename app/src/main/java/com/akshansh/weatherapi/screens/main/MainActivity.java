package com.akshansh.weatherapi.screens.main;

import android.graphics.Color;
import android.os.Bundle;

import com.akshansh.weatherapi.common.graphics.ImageLoaderHelper;
import com.akshansh.weatherapi.common.graphics.PaletteHelper;
import com.akshansh.weatherapi.common.graphics.WindowStatusBarHelper;
import com.akshansh.weatherapi.networking.weathermodels.CurrentWeatherData;
import com.akshansh.weatherapi.networking.weathermodels.WeatherForecastData;
import com.akshansh.weatherapi.screens.common.BaseActivity;
import com.akshansh.weatherapi.screens.common.toast.ToastHelper;
import com.akshansh.weatherapi.weather.FetchWeatherUseCase;

public class MainActivity extends BaseActivity implements MainViewMvc.Listener,
        FetchWeatherUseCase.Listener, PaletteHelper.Listener {
    private MainViewMvc viewMvc;
    private FetchWeatherUseCase fetchWeatherUseCase;
    private ToastHelper toastHelper;
    private ImageLoaderHelper imageLoaderHelper;
    private PaletteHelper paletteHelper;
    private WindowStatusBarHelper windowStatusBarHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewMvc = getInjector().getViewMvcFactory().getMainViewMvc(null);
        setContentView(viewMvc.getRootView());
        fetchWeatherUseCase = getInjector().getFetchWeatherUseCase();
        toastHelper = getInjector().getToastHelper();
        imageLoaderHelper = getInjector().getImageLoaderHelper();
        paletteHelper = getInjector().getPaletteHelper();
        windowStatusBarHelper = getInjector().getWindowStatusBarHelper();
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewMvc.registerListener(this);
        fetchWeatherUseCase.registerListener(this);
        paletteHelper.registerListener(this);
        fetchWeatherUseCase.fetchWeatherForecast("Jamshedpur","metric");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewMvc.unregisterListener(this);
        fetchWeatherUseCase.unregisterListener(this);
        viewMvc.clearBinding();
        paletteHelper.unregisterListener(this);
    }

    @Override
    public void OnRefresh() {
        fetchWeatherUseCase.fetchWeatherForecast("Jamshedpur","metric");
    }

    @Override
    public void OnFetchWeatherSuccessful(CurrentWeatherData weatherData,
                                         WeatherForecastData weatherForecastData) {
        int resId = imageLoaderHelper.getBackgroundDrawableResource(weatherData);
        paletteHelper.getPaletteColor(resId);
        viewMvc.setBackgroundImage(resId);
        viewMvc.bindView(weatherData,weatherForecastData);
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

    @Override
    public void OnSwatchGenerated(int swatchColor) {
        windowStatusBarHelper.setStatusBarColor(swatchColor);
    }

    @Override
    public void OnSwatchGenerationFailed() {
        windowStatusBarHelper.setStatusBarColor(Color.argb(200,0,0,0));
    }
}