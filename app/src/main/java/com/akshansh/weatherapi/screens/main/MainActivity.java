package com.akshansh.weatherapi.screens.main;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;

import com.akshansh.weatherapi.common.ViewMvcFactory;
import com.akshansh.weatherapi.networking.weather.OpenWeatherApi;
import com.akshansh.weatherapi.R;
import com.akshansh.weatherapi.screens.common.BaseActivity;
import com.akshansh.weatherapi.screens.common.toolbar.ToolbarViewMvc;

public class MainActivity extends BaseActivity implements MainViewMvc.Listener {
    private MainViewMvc viewMvc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewMvc = getInjector().getViewMvcFactory().getMainViewMvc(null);
        setContentView(viewMvc.getRootView());
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewMvc.registerListener(this);
        viewMvc.bindView(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewMvc.unregisterListener(this);
    }

    @Override
    public void OnRefresh() {

    }
}