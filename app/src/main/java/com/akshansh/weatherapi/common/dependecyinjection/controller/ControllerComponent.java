package com.akshansh.weatherapi.common.dependecyinjection.controller;

import com.akshansh.weatherapi.screens.main.main.MainActivity;
import com.akshansh.weatherapi.screens.selectcity.SelectCityActivity;
import com.akshansh.weatherapi.screens.sync.SyncActivity;

import dagger.Subcomponent;

@ControllerScope
@Subcomponent(modules = {ControllerModule.class})
public interface ControllerComponent {
    void inject(MainActivity activity);
    void inject(SyncActivity activity);
    void inject(SelectCityActivity activity);
}
