package com.akshansh.weatherapi.screens.views.forecastitem.dayselectitem;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DaySelectItemViewHolder extends RecyclerView.ViewHolder {
    public DaySelectListItemViewMvc viewMvc;

    public DaySelectItemViewHolder(@NonNull DaySelectListItemViewMvc viewMvc) {
        super(viewMvc.getRootView());
        this.viewMvc = viewMvc;
    }
}
