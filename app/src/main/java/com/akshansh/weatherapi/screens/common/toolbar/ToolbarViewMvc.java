package com.akshansh.weatherapi.screens.common.toolbar;

import com.akshansh.weatherapi.screens.common.views.ObservableViewMvc;
import com.akshansh.weatherapi.screens.common.views.ViewMvc;

public interface ToolbarViewMvc extends ObservableViewMvc<ToolbarViewMvc.Listener> {
    interface Listener{
        void OnGPSActivateButtonClicked();
    }
    void setTitle(String title);
    void setLastUpdatedTime(long lastUpdatedTimestamp);
    void setGPSButtonVisible(boolean visible);
    void setProgressBarVisible(boolean visible);
}
