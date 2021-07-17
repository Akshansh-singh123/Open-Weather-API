package com.akshansh.weatherapi.common.utils;

import android.annotation.SuppressLint;
import android.location.Location;

import androidx.appcompat.app.AppCompatActivity;

import com.akshansh.weatherapi.common.BaseObservable;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class GPSLocationHelper extends BaseObservable<GPSLocationHelper.Listener> {
    public interface Listener {
        void OnLocationFetchSuccessful(double latitude,double longitude);
        void OnLocationFetchFailure();
    }

    private final AppCompatActivity activity;

    public GPSLocationHelper(AppCompatActivity activity) {
        this.activity = activity;
    }

    @SuppressLint("MissingPermission")
    public void getLocationCoordinates() {
        FusedLocationProviderClient locationClient = LocationServices
                .getFusedLocationProviderClient(activity);
        locationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null){
                for(Listener listener: getListeners()){
                    listener.OnLocationFetchSuccessful(location.getLatitude(),
                            location.getLongitude());
                }
            }else{
                getLocationCoordinates();
            }
        });
    }
}
