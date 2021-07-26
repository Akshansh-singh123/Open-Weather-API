package com.akshansh.weatherapi.common.dependecyinjection.application;

import com.akshansh.weatherapi.common.dependecyinjection.activity.ActivityComponent;
import com.akshansh.weatherapi.common.dependecyinjection.activity.ActivityModule;

import dagger.Component;

@AppScope
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {
    ActivityComponent newActivityComponent(ActivityModule activityModule);
}
