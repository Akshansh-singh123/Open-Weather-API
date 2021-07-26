package com.akshansh.weatherapi.screens.common;

import androidx.appcompat.app.AppCompatActivity;

import com.akshansh.weatherapi.common.CustomApplication;
import com.akshansh.weatherapi.common.dependecyinjection.activity.ActivityComponent;
import com.akshansh.weatherapi.common.dependecyinjection.activity.ActivityModule;
import com.akshansh.weatherapi.common.dependecyinjection.controller.ControllerComponent;
import com.akshansh.weatherapi.common.dependecyinjection.controller.ControllerModule;

public class BaseActivity extends AppCompatActivity {
    private ControllerComponent injector;
    private ActivityComponent activityComponent;

    public ActivityComponent getActivityComponent(){
        if(activityComponent == null){
            activityComponent = ((CustomApplication)getApplication())
                    .getApplicationComponent()
                    .newActivityComponent(new ActivityModule(this));
        }
        return activityComponent;
    }

    protected ControllerComponent getInjector(){
        if(injector == null){
            injector = getActivityComponent()
                    .newControllerComponent(new ControllerModule());
        }
        return injector;
    }
}
