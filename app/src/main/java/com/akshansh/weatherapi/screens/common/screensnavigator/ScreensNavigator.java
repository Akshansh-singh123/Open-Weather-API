package com.akshansh.weatherapi.screens.common.screensnavigator;

import android.content.Intent;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.akshansh.weatherapi.screens.views.main.MainActivity;

public class ScreensNavigator {
    private final AppCompatActivity activity;

    public ScreensNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void syncToMainActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                activity.startActivity(new Intent(activity, MainActivity.class));
                activity.finish();
            }
        },500);
    }
}
