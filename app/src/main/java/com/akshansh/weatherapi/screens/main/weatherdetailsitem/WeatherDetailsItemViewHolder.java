package com.akshansh.weatherapi.screens.main.weatherdetailsitem;

import androidx.recyclerview.widget.RecyclerView;

public class WeatherDetailsItemViewHolder extends RecyclerView.ViewHolder {
    public WeatherDetailsItemViewMvc viewMvc;

    public WeatherDetailsItemViewHolder(WeatherDetailsItemViewMvc viewMvc) {
        super(viewMvc.getRootView());
        this.viewMvc = viewMvc;
    }
}
