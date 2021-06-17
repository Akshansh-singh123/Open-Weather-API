package com.akshansh.weatherapi.common.graphics;

import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class WindowStatusBarHelper {
    private final AppCompatActivity activity;

    public WindowStatusBarHelper(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void setStatusBarColor(int colorId){
        Window window = activity.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(colorId);
        window.setNavigationBarColor(colorId);
    }
}
