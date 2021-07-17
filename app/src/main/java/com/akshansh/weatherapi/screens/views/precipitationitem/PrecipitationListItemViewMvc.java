package com.akshansh.weatherapi.screens.views.precipitationitem;

import androidx.annotation.Nullable;

import com.akshansh.weatherapi.networking.weathermodels.precipitation.Precipitation;
import com.akshansh.weatherapi.screens.common.views.ViewMvc;

public interface PrecipitationListItemViewMvc extends ViewMvc {
    void bindRainData(@Nullable Precipitation rain);
    void bindSnowData(@Nullable Precipitation snow);
}
