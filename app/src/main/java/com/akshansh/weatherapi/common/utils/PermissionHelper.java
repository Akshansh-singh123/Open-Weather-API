package com.akshansh.weatherapi.common.utils;

import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.akshansh.weatherapi.common.BaseObservable;

public class PermissionHelper extends BaseObservable<PermissionHelper.Listener> {
    public interface Listener{
        void onPermissionGranted(String permission, int requestCode);
        void onPermissionDenied(String permission, int requestCode);
        void onPermissionDeniedPermanent(String permission, int requestCode);
    }

    private final AppCompatActivity activity;

    public PermissionHelper(AppCompatActivity activity) {
        this.activity = activity;
    }

    public boolean isPermissionGranted(String permission){
        return ActivityCompat.checkSelfPermission(activity,permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    public void requestPermission(String[] permissions,int requestCode){
        ActivityCompat.requestPermissions(activity,permissions,requestCode);
    }

    public void onPermissionResult(int requestCode, @NonNull String[] permissions,
                                   @NonNull int[] grantResult){
        if(permissions.length < 1)
            throw new RuntimeException("no permission on request result");
        String permission = permissions[0];
        if(grantResult[0] == PackageManager.PERMISSION_GRANTED){
            notifyPermissionGranted(permission,requestCode);
        }else{
            if(ActivityCompat.shouldShowRequestPermissionRationale(activity,permissions[0])){
                notifyPermissionDenied(permission,requestCode);
            }else{
                notifyPermissionDeniedPermanently(permission,requestCode);
            }
        }
    }

    private void notifyPermissionDeniedPermanently(String permission, int requestCode) {
        for(Listener listener: getListeners()){
            listener.onPermissionDeniedPermanent(permission,requestCode);
        }
    }

    private void notifyPermissionDenied(String permission, int requestCode) {
        for(Listener listener: getListeners()){
            listener.onPermissionDenied(permission,requestCode);
        }
    }

    private void notifyPermissionGranted(String permission, int requestCode) {
        for(Listener listener: getListeners()){
            listener.onPermissionGranted(permission,requestCode);
        }
    }
}
