package com.akshansh.weatherapi.screens.main;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akshansh.weatherapi.common.ViewMvcFactory;
import com.akshansh.weatherapi.networking.weathermodels.CurrentWeatherData;
import com.akshansh.weatherapi.screens.main.headeritem.HeaderListItemViewHolder;
import com.akshansh.weatherapi.screens.main.headeritem.HeaderListItemViewMvc;
import com.akshansh.weatherapi.screens.main.weatherdetailsitem.WeatherDetailsItemViewHolder;
import com.akshansh.weatherapi.screens.main.weatherdetailsitem.WeatherDetailsItemViewMvc;

public class MainAdapter extends RecyclerView.Adapter {
    private static final int WEATHER_HEADER = 1;
    private static final int WEATHER_DETAILS = 2;

    private CurrentWeatherData weatherData;
    private final ViewMvcFactory viewMvcFactory;

    public MainAdapter(ViewMvcFactory viewMvcFactory) {
        this.viewMvcFactory = viewMvcFactory;
    }

    public void bindView(CurrentWeatherData weatherData){
        this.weatherData = weatherData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        switch (position){
            case 0:
                return WEATHER_HEADER;
            case 1:
                return WEATHER_DETAILS;
            default:
                throw new RuntimeException("Invalid position");
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case WEATHER_HEADER:
                HeaderListItemViewMvc headerListItemViewMvc = viewMvcFactory
                        .getHeaderListItemViewMvc(parent);
                return new HeaderListItemViewHolder(headerListItemViewMvc);
            case WEATHER_DETAILS:
                WeatherDetailsItemViewMvc weatherDetailsItemViewMvc = viewMvcFactory
                        .getWeatherDetailsItemViewMvc(parent);
                return new WeatherDetailsItemViewHolder(weatherDetailsItemViewMvc);
            default:
                throw new RuntimeException("Invalid view type");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (position){
            case 0:
                HeaderListItemViewHolder headerListItemViewHolder = (HeaderListItemViewHolder)holder;
                headerListItemViewHolder.viewMvc.bindView(weatherData);
                break;
            case 1:
                WeatherDetailsItemViewHolder weatherDetailsItemViewHolder =
                        (WeatherDetailsItemViewHolder)holder;
                weatherDetailsItemViewHolder.viewMvc.bindView(weatherData);
                break;
            default:
                throw new RuntimeException("invalid position");
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
