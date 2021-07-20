package com.akshansh.weatherapi.common.utils;

import android.annotation.SuppressLint;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.akshansh.weatherapi.common.BaseObservable;
import com.akshansh.weatherapi.common.Constants;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

public class GPSLocationHelper extends BaseObservable<GPSLocationHelper.Listener> {
    public interface Listener {
        void OnLocationFetchSuccessful(double latitude,double longitude);
        void OnLocationFetchFailure();
        void OnGPSNotAvailable();
    }

    private FusedLocationProviderClient locationClient;
    private LocationCallback locationCallback;
    private final AppCompatActivity activity;
    private final ExecutorService executor;
    private final AtomicBoolean locationFetched;
    private long locationFetchStart;

    public GPSLocationHelper(AppCompatActivity activity, ExecutorService executor) {
        this.activity = activity;
        this.executor = executor;
        locationFetched = new AtomicBoolean(false);
    }

    @SuppressLint("MissingPermission")
    public void getLocationCoordinates() {
        locationFetched.getAndSet(false);
        locationClient = LocationServices
                .getFusedLocationProviderClient(activity);

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location location = locationResult.getLastLocation();
                locationFetched.getAndSet(true);
                for(Listener listener: getListeners()){
                    listener.OnLocationFetchSuccessful(location.getLatitude(),
                            location.getLongitude());
                }
                clearUpdates();
            }

            @Override
            public void onLocationAvailability(@NonNull LocationAvailability locationAvailability) {
                super.onLocationAvailability(locationAvailability);
                if(!locationAvailability.isLocationAvailable()){
                    locationFetched.getAndSet(true);
                    for(Listener listener: getListeners()){
                        listener.OnGPSNotAvailable();
                    }
                    clearUpdates();
                }
            }
        };

        locationFetchStart = System.currentTimeMillis();
        locationClient.requestLocationUpdates(locationRequest, locationCallback,null);
        listenForTimeout();
    }

    public void clearUpdates(){
        if(locationClient != null) {
            locationClient.removeLocationUpdates(locationCallback);
        }
    }

    private void listenForTimeout() {
        executor.execute(()->{
            while (System.currentTimeMillis() - locationFetchStart <=
                    Constants.LOCATION_FETCH_TIMEOUT && !locationFetched.get()){
            }
            if(!locationFetched.get()) {
                for (Listener listener : getListeners()) {
                    listener.OnLocationFetchFailure();
                }
                clearUpdates();
            }
        });
    }
}
