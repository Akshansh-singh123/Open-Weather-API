package com.akshansh.weatherapi.common.dependecyinjection.activity;

import com.akshansh.weatherapi.common.dependecyinjection.controller.ControllerComponent;
import com.akshansh.weatherapi.common.dependecyinjection.controller.ControllerModule;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {ActivityModule.class})
public interface ActivityComponent {
    ControllerComponent newControllerComponent(ControllerModule controllerModule);
}
