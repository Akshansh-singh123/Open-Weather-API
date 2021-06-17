package com.akshansh.weatherapi.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.appcompat.app.AppCompatActivity;

public class InternetConnectionTester {
    private final AppCompatActivity activity;

    public InternetConnectionTester(AppCompatActivity activity) {
        this.activity = activity;
    }

    public boolean isConnected(){
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)activity.
                getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState()
                == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState()
                        == NetworkInfo.State.CONNECTED) {
            connected = true;
        }

        return connected;
    }
}
