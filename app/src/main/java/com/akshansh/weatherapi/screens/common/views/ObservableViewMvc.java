package com.akshansh.weatherapi.screens.common.views;

public interface ObservableViewMvc<ListenerType> {
    void registerListener(ListenerType T);
    void unregisterListener(ListenerType T);
}
