package com.akshansh.weatherapi.screens.common.toolbar;

import com.akshansh.weatherapi.screens.common.views.ViewMvc;

public interface ToolbarViewMvc extends ViewMvc {
    void setTitle(String title);
    void setLastUpdatedTime(long lastUpdatedTimestamp);
    void clearBinding();
}
