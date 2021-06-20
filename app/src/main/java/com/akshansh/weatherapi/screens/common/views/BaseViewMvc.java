package com.akshansh.weatherapi.screens.common.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.appcompat.content.res.AppCompatResources;

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

    protected Drawable getDrawable(int resId){
        return AppCompatResources.getDrawable(getContext(),resId);
    }

    @Override
    public View getRootView(){
        return rootView;
    }
}
