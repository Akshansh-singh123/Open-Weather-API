package com.akshansh.weatherapi.screens.main;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.akshansh.weatherapi.common.ViewMvcFactory;
import com.akshansh.weatherapi.databinding.ActivityMainBinding;
import com.akshansh.weatherapi.networking.weathermodels.CurrentWeatherData;
import com.akshansh.weatherapi.screens.common.toolbar.ToolbarViewMvc;
import com.akshansh.weatherapi.screens.common.views.BaseObservableViewMvc;

public class MainViewMvcImpl extends BaseObservableViewMvc<MainViewMvc.Listener>
        implements MainViewMvc {
    private ActivityMainBinding binding;
    private Toolbar toolbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private ToolbarViewMvc toolbarViewMvc;
    private final MainAdapter adapter;

    public MainViewMvcImpl(@NonNull LayoutInflater inflater, ViewGroup parent,
                           ViewMvcFactory viewMvcFactory) {
        binding = ActivityMainBinding.inflate(inflater,parent,false);
        toolbarViewMvc = viewMvcFactory.getToolbarViewMvc(parent);
        setRootView(binding.getRoot());
        swipeRefreshLayout = binding.swipeRefreshLayout;
        recyclerView = binding.recyclerView;
        toolbar = binding.toolbar;

        adapter = new MainAdapter(viewMvcFactory);
        toolbar.addView(toolbarViewMvc.getRootView());
        toolbarViewMvc.setTitle("Jamshedpur");
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void bindView(CurrentWeatherData weatherData) {
        adapter.bindView(weatherData);
    }

    @Override
    public void clearBinding() {
        binding = null;
    }
}
