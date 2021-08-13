package com.akshansh.weatherapi.screens.common;

import androidx.fragment.app.DialogFragment;

import com.akshansh.weatherapi.common.dependecyinjection.controller.ControllerComponent;
import com.akshansh.weatherapi.common.dependecyinjection.controller.ControllerModule;

public class BaseDialog extends DialogFragment {
    private ControllerComponent injector;

    protected ControllerComponent getInjector(){
        if(injector == null){
            injector = ((BaseActivity)getActivity())
                    .getActivityComponent()
                    .newControllerComponent(new ControllerModule());
        }
        return injector;
    }
}
