package com.akshansh.weatherapi.screens.main.forecastitem;

import androidx.recyclerview.widget.RecyclerView;

public class ForecastListItemViewHolder extends RecyclerView.ViewHolder {
    public ForecastListItemViewMvc viewMvc;

    public ForecastListItemViewHolder(ForecastListItemViewMvc viewMvc) {
        super(viewMvc.getRootView());
        this.viewMvc = viewMvc;
    }
}
