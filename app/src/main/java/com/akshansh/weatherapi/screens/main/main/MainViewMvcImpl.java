package com.akshansh.weatherapi.screens.main.main;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.akshansh.weatherapi.common.ViewMvcFactory;
import com.akshansh.weatherapi.databinding.ActivityMainBinding;
import com.akshansh.weatherapi.networking.weathermodels.CurrentWeatherData;
import com.akshansh.weatherapi.networking.weathermodels.WeatherForecastData;
import com.akshansh.weatherapi.screens.common.toolbar.ToolbarViewMvc;
import com.akshansh.weatherapi.screens.common.views.BaseObservableViewMvc;

public class MainViewMvcImpl extends BaseObservableViewMvc<MainViewMvc.Listener>
        implements MainViewMvc,ToolbarViewMvc.Listener {
    private ActivityMainBinding binding;
    private final Toolbar toolbar;
    private final SwipeRefreshLayout swipeRefreshLayout;
    private final RecyclerView recyclerView;
    private final ToolbarViewMvc toolbarViewMvc;
    private final MainAdapter adapter;
    private final ImageFilterView wallpaper;

    public MainViewMvcImpl(@NonNull LayoutInflater inflater, ViewGroup parent,
                           ViewMvcFactory viewMvcFactory) {
        binding = ActivityMainBinding.inflate(inflater,parent,false);
        toolbarViewMvc = viewMvcFactory.getToolbarViewMvc(parent);
        setRootView(binding.getRoot());
        swipeRefreshLayout = binding.swipeRefreshLayout;
        recyclerView = binding.recyclerView;
        toolbar = binding.toolbar;
        wallpaper = binding.wallpaper;

        adapter = new MainAdapter(viewMvcFactory);
        toolbar.addView(toolbarViewMvc.getRootView(),ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void bindView(CurrentWeatherData weatherData, WeatherForecastData weatherForecastData) {
        adapter.bindView(weatherData,weatherForecastData);
        toolbarViewMvc.setTitle(weatherData.getCityName());
        toolbarViewMvc.registerListener(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                for(Listener listener: getListeners()){
                    listener.OnRefresh();
                }
            }
        });
        toolbarViewMvc.setLastUpdatedTime(weatherData.getWeatherTimestamp());
    }

    @Override
    public void clearBinding() {
        binding = null;
        toolbarViewMvc.unregisterListener(this);
    }

    @Override
    public void stopRefreshing() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setBackgroundImage(int resId) {
        wallpaper.setImageResource(resId);
    }

    @Override
    public void setGPSButtonVisible(boolean visible) {
        toolbarViewMvc.setGPSButtonVisible(visible);
    }

    @Override
    public void OnGPSActivateButtonClicked() {
        for(Listener listener: getListeners()){
            listener.OnGPSActivateButtonClicked();
        }
    }
}
