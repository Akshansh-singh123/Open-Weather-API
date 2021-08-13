package com.akshansh.weatherapi.screens.main.main;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;

import com.akshansh.weatherapi.common.Constants;
import com.akshansh.weatherapi.common.ViewMvcFactory;
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
import com.akshansh.weatherapi.screens.common.screensnavigator.ScreensNavigator;
import com.akshansh.weatherapi.screens.common.toast.ToastHelper;
import com.akshansh.weatherapi.weather.FetchWeatherUseCase;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainViewMvc.Listener,
        FetchWeatherUseCase.Listener, PaletteHelper.Listener, GPSLocationHelper.Listener,
        GPSActivationHelper.Listener, PermissionHelper.Listener {
    @Inject public ViewMvcFactory viewMvcFactory;
    @Inject public FetchWeatherUseCase fetchWeatherUseCase;
    @Inject public ToastHelper toastHelper;
    @Inject public ImageLoaderHelper imageLoaderHelper;
    @Inject public PaletteHelper paletteHelper;
    @Inject public WindowStatusBarHelper windowStatusBarHelper;
    @Inject public WeatherDataSyncHelper weatherDataSyncHelper;
    @Inject public GPSActivationHelper gpsActivationHelper;
    @Inject public GPSLocationHelper gpsLocationHelper;
    @Inject public PermissionHelper permissionHelper;
    @Inject public Handler uiThread;
    @Inject public ScreensNavigator screensNavigator;

    private boolean initialized = false;
    private MainViewMvc viewMvc;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getInjector().inject(this);
        viewMvc = viewMvcFactory.getMainViewMvc(null);
        setContentView(viewMvc.getRootView());
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
        Log.e(TAG, "onStart: "+initialized);
        if(!initialized) {
            fetchWeatherUpdatesByCachedInputs();
            checkPermissionAndFetchLocation();
            initialized = true;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: ");
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
        Log.e(TAG, "onDestroy: ");
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
    public void OnEditCityButtonClicked() {
        screensNavigator.toCitySelectActivity();
    }

    @Override
    public void OnFetchWeatherSuccessful(CurrentWeatherData weatherData,
                                         WeatherForecastData weatherForecastData) {
        viewMvc.setProgressBarVisible(false);
        int resId = imageLoaderHelper.getBackgroundDrawableResource(weatherData);
        paletteHelper.getPaletteColor(resId);
        viewMvc.setBackgroundImage(resId);
        viewMvc.bindView(weatherData,weatherForecastData);
        viewMvc.stopRefreshing();
    }

    @Override
    public void OnFetchWeatherFailure() {
        viewMvc.setProgressBarVisible(false);
        toastHelper.makeToast("Could not fetch the latest weather");
        viewMvc.stopRefreshing();
    }

    @Override
    public void OnFetchWeatherNetworkError() {
        viewMvc.setProgressBarVisible(false);
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
        fetchWeatherUseCase.fetchWeatherForecastByLocation(latitude,longitude,Constants.METRIC,false);
    }

    @WorkerThread
    @Override
    public void OnLocationFetchFailure() {
        viewMvc.setProgressBarVisible(false);
        uiThread.post(this::fetchWeatherUpdatesByCachedInputs);
    }

    @Override
    public void OnGPSNotAvailable() {
        viewMvc.setGPSButtonVisible(true);
        viewMvc.setProgressBarVisible(false);
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
            viewMvc.disableEditCityButton();
            if(requestCode == Constants.LOCATION_REQUEST_CODE){
                viewMvc.setProgressBarVisible(true);
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
        viewMvc.enableEditCityButton();
        fetchWeatherUpdatesByCachedInputs();
        if(requestCode == Constants.GPS_ACTIVATION_REQUEST_CODE){
            viewMvc.setGPSButtonVisible(false);
        }
    }

    private void checkPermissionAndFetchLocation() {
        if (permissionHelper.isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)){
            viewMvc.setProgressBarVisible(true);
            viewMvc.disableEditCityButton();
            gpsLocationHelper.getLocationCoordinates();
        }else{
            permissionHelper.requestPermission(new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    Constants.LOCATION_REQUEST_CODE);
        }
    }

    private void fetchWeatherUpdatesByCachedInputs(){
        viewMvc.setProgressBarVisible(true);
        if(weatherDataSyncHelper.getLatitude() != null &&
                weatherDataSyncHelper.getLongitude() != null){
            double latitude = weatherDataSyncHelper.getLatitude();
            double longitude = weatherDataSyncHelper.getLongitude();
            fetchWeatherUseCase.fetchWeatherForecastByLocation(latitude,longitude,Constants.METRIC,true);
        }else if(weatherDataSyncHelper.getCity() != null){
            String city = weatherDataSyncHelper.getCity();
            fetchWeatherUseCase.fetchWeatherForecastByCityName(city,Constants.METRIC,true);
            viewMvc.enableEditCityButton();
        }else{
            throw new RuntimeException("invalid state of sync");
        }
    }

    private void checkPermissionAndActivateGPS() {
        viewMvc.setGPSButtonVisible(false);
        if (permissionHelper.isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            gpsActivationHelper.enableGPS();
            viewMvc.disableEditCityButton();
        } else {
            permissionHelper.requestPermission(new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    Constants.GPS_ACTIVATION_REQUEST_CODE);
        }
    }
}