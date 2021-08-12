package com.akshansh.weatherapi.screens.sync;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.akshansh.weatherapi.common.Constants;
import com.akshansh.weatherapi.common.utils.GPSActivationHelper;
import com.akshansh.weatherapi.common.utils.GPSLocationHelper;
import com.akshansh.weatherapi.common.utils.PermissionHelper;
import com.akshansh.weatherapi.networking.weathermodels.WeatherForecastData;
import com.akshansh.weatherapi.screens.common.screensnavigator.ScreensNavigator;
import com.akshansh.weatherapi.common.utils.WeatherDataSyncHelper;
import com.akshansh.weatherapi.databinding.ActivitySyncBinding;
import com.akshansh.weatherapi.networking.weathermodels.CurrentWeatherData;
import com.akshansh.weatherapi.screens.common.BaseActivity;
import com.akshansh.weatherapi.screens.common.toast.ToastHelper;
import com.akshansh.weatherapi.weather.FetchWeatherUseCase;

import javax.inject.Inject;

public class SyncActivity extends BaseActivity implements FetchWeatherUseCase.Listener,
        GPSActivationHelper.Listener, GPSLocationHelper.Listener, PermissionHelper.Listener {
    private ActivitySyncBinding binding;
    @Inject public FetchWeatherUseCase fetchWeatherUseCase;
    @Inject public ToastHelper toastHelper;
    @Inject public WeatherDataSyncHelper weatherDataSyncHelper;
    @Inject public ScreensNavigator screensNavigator;
    @Inject public PermissionHelper permissionHelper;
    @Inject public GPSLocationHelper gpsLocationHelper;
    @Inject public GPSActivationHelper gpsActivationHelper;
    @Inject public Handler uiThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySyncBinding.inflate(getLayoutInflater(),null,false);
        setContentView(binding.getRoot());
        getInjector().inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        fetchWeatherUseCase.registerListener(this);
        gpsActivationHelper.registerListener(this);
        gpsLocationHelper.registerListener(this);
        permissionHelper.registerListener(this);
        if(weatherDataSyncHelper.isSynced()) {
            screensNavigator.syncToMainActivity();
            return;
        }
        seekPermissionAndEnableGPS();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        gpsActivationHelper.onActivityResult(Constants.LOCATION_REQUEST_CODE,resultCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionHelper.onPermissionResult(requestCode,permissions,grantResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fetchWeatherUseCase.unregisterListener(this);
        gpsLocationHelper.unregisterListener(this);
        gpsActivationHelper.unregisterListener(this);
        permissionHelper.unregisterListener(this);
        binding = null;
    }

    @Override
    public void OnFetchWeatherSuccessful(CurrentWeatherData weatherData,
                                         WeatherForecastData weatherForecastData) {
        screensNavigator.syncToMainActivity();
        finish();
    }

    @Override
    public void OnFetchWeatherFailure() {
        toastHelper.makeToast("Could not load weather data. Try again after sometime.");
    }

    @Override
    public void OnFetchWeatherNetworkError() {
        toastHelper.makeToast("Could not load weather data. Check the internet connection.");
    }

    @Override
    public void OnGPSActivated() {
        gpsLocationHelper.getLocationCoordinates();
        showProgressBar();
    }

    @Override
    public void OnGPSNotAvailable() {
        seekPermissionAndEnableGPS();
    }

    @Override
    public void OnGPSActivationFailure() {
        screensNavigator.toCitySelectActivity();
        toastHelper.makeToast("User denied GPS activation");
    }

    @Override
    public void OnLocationFetchSuccessful(double latitude, double longitude) {
        fetchWeatherUseCase.fetchWeatherForecastByLocation(latitude,longitude,Constants.METRIC);
        hideProgressBar();
    }

    @Override
    public void OnLocationFetchFailure() {
        screensNavigator.toCitySelectActivity();
        hideProgressBar();
        toastHelper.makeToast("Failed to fetch location.");
        finish();
    }

    @Override
    public void onPermissionGranted(String permission, int requestCode) {
        gpsActivationHelper.enableGPS();
    }

    @Override
    public void onPermissionDenied(String permission, int requestCode) {
        screensNavigator.toCitySelectActivity();
        toastHelper.makeToast("Permission denied");
        finish();
    }

    @Override
    public void onPermissionDeniedPermanent(String permission, int requestCode) {
        screensNavigator.toCitySelectActivity();
        toastHelper.makeToast("Permission denied. Go to app settings to grant permission");
        finish();
    }

    private void showProgressBar(){
        uiThread.post(()->{
            binding.fetchingLocationTextView.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.VISIBLE);
        });
    }

    private void hideProgressBar(){
        uiThread.post(()->{
            binding.fetchingLocationTextView.setVisibility(View.GONE);
            binding.progressBar.setVisibility(View.GONE);
        });
    }

    private void seekPermissionAndEnableGPS() {
        if (permissionHelper.isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            gpsActivationHelper.enableGPS();
        } else {
            permissionHelper.requestPermission(new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, Constants.LOCATION_REQUEST_CODE);
        }
    }
}