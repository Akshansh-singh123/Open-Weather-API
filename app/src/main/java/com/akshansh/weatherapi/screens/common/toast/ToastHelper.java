package com.akshansh.weatherapi.screens.common.toast;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ToastHelper {
    private final AppCompatActivity activity;

    public ToastHelper(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void makeToast(String message){
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }
}
