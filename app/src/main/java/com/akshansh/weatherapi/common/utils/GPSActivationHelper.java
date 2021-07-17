package com.akshansh.weatherapi.common.utils;

import android.content.IntentSender;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.akshansh.weatherapi.common.BaseObservable;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import static android.app.Activity.RESULT_OK;
import static com.google.android.gms.common.api.CommonStatusCodes.RESOLUTION_REQUIRED;
import static com.google.android.gms.location.LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE;

public class GPSActivationHelper extends BaseObservable<GPSActivationHelper.Listener> {
    public interface Listener{
        void OnGPSActivated();
        void OnGPSActivationFailure();
    }

    private final AppCompatActivity activity;

    public GPSActivationHelper(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void enableGPS(){
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(activity)
                .checkLocationSettings(builder.build());
        result.addOnCompleteListener(task -> {
            try {
                LocationSettingsResponse response = task.getResult(ApiException.class);
                for(Listener listener: getListeners()){
                    listener.OnGPSActivated();
                }
            }catch (ApiException e){
                switch (e.getStatusCode()){
                    case RESOLUTION_REQUIRED:
                        try {
                            ResolvableApiException resolvable = (ResolvableApiException)e;
                            resolvable.startResolutionForResult(activity,
                                    LocationRequest.PRIORITY_HIGH_ACCURACY);
                        }catch (IntentSender.SendIntentException | ClassCastException sendIntentException){
                            sendIntentException.printStackTrace();
                        }
                        break;
                    case SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                    default:
                        throw new RuntimeException("invalid status code");
                }
            }
        });
    }

    public void onActivityResult(int requestCode,int resultCode){
        if(requestCode == LocationRequest.PRIORITY_HIGH_ACCURACY){
            if(resultCode == RESULT_OK){
                for(Listener listener: getListeners()){
                    listener.OnGPSActivated();
                }
            }else{
                for(Listener listener: getListeners()){
                    listener.OnGPSActivationFailure();
                }
            }
        }
    }
}
