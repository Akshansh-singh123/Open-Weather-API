package com.akshansh.weatherapi.screens.main.headeritem;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HeaderListItemViewHolder extends RecyclerView.ViewHolder {
    public HeaderListItemViewMvc viewMvc;

    public HeaderListItemViewHolder(@NonNull HeaderListItemViewMvc viewMvc) {
        super(viewMvc.getRootView());
        this.viewMvc = viewMvc;
    }
}
