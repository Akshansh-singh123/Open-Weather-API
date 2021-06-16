package com.akshansh.weatherapi.screens.main;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;

import com.akshansh.weatherapi.networking.OpenWeatherApi;
import com.akshansh.weatherapi.R;
import com.akshansh.weatherapi.screens.common.BaseActivity;

public class MainActivity extends BaseActivity{
    private OpenWeatherApi openWeatherApi;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}