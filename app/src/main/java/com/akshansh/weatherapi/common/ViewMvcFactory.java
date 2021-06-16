package com.akshansh.weatherapi.common;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.akshansh.weatherapi.screens.common.toolbar.ToolbarViewMvc;
import com.akshansh.weatherapi.screens.common.toolbar.ToolbarViewMvcImpl;
import com.akshansh.weatherapi.screens.main.MainViewMvc;
import com.akshansh.weatherapi.screens.main.MainViewMvcImpl;
import com.akshansh.weatherapi.screens.main.headeritem.HeaderListItemViewMvc;
import com.akshansh.weatherapi.screens.main.headeritem.HeaderListItemViewMvcImpl;
import com.akshansh.weatherapi.screens.main.weatherdetailsitem.WeatherDetailsItemViewMvc;
import com.akshansh.weatherapi.screens.main.weatherdetailsitem.WeatherDetailsViewMvcImpl;

public class ViewMvcFactory {
    private LayoutInflater inflater;

    public ViewMvcFactory(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public MainViewMvc getMainViewMvc(ViewGroup parent){
        return new MainViewMvcImpl(inflater,parent,this);
    }

    public HeaderListItemViewMvc getHeaderListItemViewMvc(ViewGroup parent){
        return new HeaderListItemViewMvcImpl(inflater,parent);
    }

    public WeatherDetailsItemViewMvc getWeatherDetailsItemViewMvc(ViewGroup parent){
        return new WeatherDetailsViewMvcImpl(inflater,parent);
    }

    public ToolbarViewMvc getToolbarViewMvc(ViewGroup parent){
        return new ToolbarViewMvcImpl(inflater,parent);
    }
}
