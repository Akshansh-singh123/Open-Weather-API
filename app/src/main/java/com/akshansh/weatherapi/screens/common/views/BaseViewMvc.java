package com.akshansh.weatherapi.screens.common.views;

import android.content.Context;
import android.view.View;

public abstract class BaseViewMvc implements ViewMvc{
    private View rootView;

    public void setRootView(View rootView) {
        this.rootView = rootView;
    }

    protected Context getContext(){
        return rootView.getContext();
    }

    protected String getString(int stringId){
        return getContext().getString(stringId);
    }

    protected int getColor(int resId){
        return getContext().getColor(resId);
    }

    @Override
    public View getRootView(){
        return rootView;
    }
}
