package com.akshansh.weatherapi.screens.main.main;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    private Handler uiThread;

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
        uiThread = getInjector().getUiThread();
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        gpsActivationHelper.onActivityResult(requestCode,resultCode);
    }

    @Override
    public void OnRefresh() {
        checkPermissionAndFetchLocation();
    }

    @Override
    public void OnGPSActivateButtonClicked() {
        checkPermissionAndActivateGPS();
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
        viewMvc.setGPSButtonVisible(false);
        fetchWeatherUseCase.fetchWeatherForecastByLocation(latitude,longitude,Constants.METRIC);
    }

    @WorkerThread
    @Override
    public void OnLocationFetchFailure() {
        uiThread.post(this::fetchWeatherUpdatesByCachedInputs);
    }

    @Override
    public void OnGPSNotAvailable() {
        viewMvc.setGPSButtonVisible(true);
        fetchWeatherUpdatesByCachedInputs();
    }

    @Override
    public void OnGPSActivated() {
        viewMvc.setGPSButtonVisible(false);
        checkPermissionAndFetchLocation();
    }

    @Override
    public void OnGPSActivationFailure() {
        viewMvc.setGPSButtonVisible(true);
    }

    @Override
    public void onPermissionGranted(String permission, int requestCode) {
        if(permission.equals(Manifest.permission.ACCESS_FINE_LOCATION)){
            if(requestCode == Constants.LOCATION_REQUEST_CODE){
                gpsLocationHelper.getLocationCoordinates();
            }else if(requestCode == Constants.GPS_ACTIVATION_REQUEST_CODE){
                gpsActivationHelper.enableGPS();
                viewMvc.setGPSButtonVisible(false);
            }
        }
    }

    @Override
    public void onPermissionDenied(String permission, int requestCode) {
        fetchWeatherUpdatesByCachedInputs();
        if(requestCode == Constants.GPS_ACTIVATION_REQUEST_CODE){
            viewMvc.setGPSButtonVisible(true);
        }
    }

    @Override
    public void onPermissionDeniedPermanent(String permission, int requestCode) {
        fetchWeatherUpdatesByCachedInputs();
        if(requestCode == Constants.GPS_ACTIVATION_REQUEST_CODE){
            viewMvc.setGPSButtonVisible(false);
        }
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

    private void checkPermissionAndActivateGPS() {
        viewMvc.setGPSButtonVisible(false);
        if (permissionHelper.isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            gpsActivationHelper.enableGPS();
        } else {
            permissionHelper.requestPermission(new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    Constants.GPS_ACTIVATION_REQUEST_CODE);
        }
    }
}