package com.akshansh.weatherapi.screens.common;

import androidx.appcompat.app.AppCompatActivity;

import com.akshansh.weatherapi.common.CustomApplication;
import com.akshansh.weatherapi.common.dependecyinjection.ActivityModule;
import com.akshansh.weatherapi.common.dependecyinjection.ControllerModule;

public class BaseActivity extends AppCompatActivity {
    private ControllerModule injector;
    private ActivityModule activityModule;

    public ActivityModule getActivityModule(){
        if(activityModule == null){
            activityModule = new ActivityModule(((CustomApplication)getApplication())
                    .getApplicationModule(),this);
        }
        return activityModule;
    }

    protected ControllerModule getInjector(){
        if(injector == null){
            injector = new ControllerModule(getActivityModule());
        }
        return injector;
    }
}
