package com.akshansh.weatherapi.screens.main.precipitationitem;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PrecipitationListItemViewHolder extends RecyclerView.ViewHolder {
    public PrecipitationListItemViewMvc viewMvc;

    public PrecipitationListItemViewHolder(@NonNull PrecipitationListItemViewMvc viewMvc) {
        super(viewMvc.getRootView());
        this.viewMvc = viewMvc;
    }
}
