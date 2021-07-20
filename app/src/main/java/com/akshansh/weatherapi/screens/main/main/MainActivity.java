package com.akshansh.weatherapi.screens.main.main;

import android.Manifest;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.WorkerThread;

import com.akshansh.weatherapi.common.Constants;
import com.akshansh.weatherapi.common.graphics.ImageLoaderHelper;
import com.akshansh.weatherapi.common.graphics.PaletteHelper;
import com.akshansh.weatherapi.common.graphics.WindowStatusBarHelper;
import com.akshansh.weatherapi.common.utils.GPSActivationHelper;
import com.akshansh.weatherapi.common.utils.GPSLocationHelper;
import com.akshansh.weatherapi.common.utils.PermissionHelper;
import com.akshansh.weatherapi.common.utils.WeatherDataSyncHelper;
import com.akshansh.weatherapi.networking.weathermodels.CurrentWeatherData;
import com.akshansh.weatherapi.networking.weathermodels.WeatherForecastData;
import com.akshansh.weatherapi.screens.common.BaseActivity;
import com.akshansh.weatherapi.screens.common.toast.ToastHelper;
import com.akshansh.weatherapi.weather.FetchWeatherUseCase;

public class MainActivity extends BaseActivity implements MainViewMvc.Listener,
        FetchWeatherUseCase.Listener, PaletteHelper.Listener, GPSLocationHelper.Listener,
        GPSActivationHelper.Listener, PermissionHelper.Listener {
    private MainViewMvc viewMvc;
    private FetchWeatherUseCase fetchWeatherUseCase;
    private ToastHelper toastHelper;
    private ImageLoaderHelper imageLoaderHelper;
    private PaletteHelper paletteHelper;
    private WindowStatusBarHelper windowStatusBarHelper;
    private WeatherDataSyncHelper weatherDataSyncHelper;
    private GPSActivationHelper gpsActivationHelper;
    private GPSLocationHelper gpsLocationHelper;
    private PermissionHelper permissionHelper;

    private boolean initialized = false;

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
        weatherDataSyncHelper = getInjector().getWeatherDataSyncHelper();
        gpsActivationHelper = getInjector().getGPSActivationHelper();
        gpsLocationHelper = getInjector().getGPSLocationHelper();
        permissionHelper = getInjector().getPermissionHelper();
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewMvc.registerListener(this);
        fetchWeatherUseCase.registerListener(this);
        paletteHelper.registerListener(this);
        permissionHelper.registerListener(this);
        gpsLocationHelper.registerListener(this);
        gpsActivationHelper.registerListener(this);
        if(!initialized) {
            OnFetchWeatherSuccessful(weatherDataSyncHelper.getWeatherDataSynced(),
                    weatherDataSyncHelper.getWeatherForecastDataSynced());
            checkPermissionAndFetchLocation();
            initialized = true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewMvc.clearBinding();
        viewMvc.unregisterListener(this);
        fetchWeatherUseCase.unregisterListener(this);
        paletteHelper.unregisterListener(this);
        permissionHelper.unregisterListener(this);
        gpsActivationHelper.unregisterListener(this);
        gpsLocationHelper.unregisterListener(this);
        initialized = false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionHelper.onPermissionResult(requestCode,permissions,grantResults);
    }

    @Override
    public void OnRefresh() {
        checkPermissionAndFetchLocation();
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

    @Override
    public void OnLocationFetchSuccessful(double latitude, double longitude) {
        fetchWeatherUseCase.fetchWeatherForecastByLocation(latitude,longitude,Constants.METRIC);
    }

    @Override
    public void OnLocationFetchFailure() {
        fetchWeatherUpdatesByCachedInputs();
    }

    @Override
    public void OnGPSNotAvailable() {
        // TODO: 20-07-2021 Display Enable GPS button
        fetchWeatherUpdatesByCachedInputs();
    }

    @Override
    public void OnGPSActivated() {
        checkPermissionAndFetchLocation();
    }

    @WorkerThread
    @Override
    public void OnGPSActivationFailure() {

    }

    @Override
    public void onPermissionGranted(String permission, int requestCode) {
        if(permission.equals(Manifest.permission.ACCESS_FINE_LOCATION)){
            if(requestCode == Constants.LOCATION_REQUEST_CODE){
                gpsLocationHelper.getLocationCoordinates();
            }
        }
    }

    @Override
    public void onPermissionDenied(String permission, int requestCode) {
        fetchWeatherUpdatesByCachedInputs();
    }

    @Override
    public void onPermissionDeniedPermanent(String permission, int requestCode) {
        fetchWeatherUpdatesByCachedInputs();
    }

    private void checkPermissionAndFetchLocation() {
        if (permissionHelper.isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)){
            gpsLocationHelper.getLocationCoordinates();
        }else{
            permissionHelper.requestPermission(new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    Constants.LOCATION_REQUEST_CODE);
        }
    }

    private void fetchWeatherUpdatesByCachedInputs(){
        if(weatherDataSyncHelper.getLatitude() != null &&
                weatherDataSyncHelper.getLongitude() != null){
            double latitude = weatherDataSyncHelper.getLatitude();
            double longitude = weatherDataSyncHelper.getLongitude();
            fetchWeatherUseCase.fetchWeatherForecastByLocation(latitude,longitude,Constants.METRIC);
        }else if(weatherDataSyncHelper.getCity() != null){
            String city = weatherDataSyncHelper.getCity();
            fetchWeatherUseCase.fetchWeatherForecastByCityName(city,Constants.METRIC);
        }else{
            throw new RuntimeException("invalid state of sync");
        }
    }
}