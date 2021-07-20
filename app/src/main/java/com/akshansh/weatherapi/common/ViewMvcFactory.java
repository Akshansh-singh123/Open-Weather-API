package com.akshansh.weatherapi.common;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.akshansh.weatherapi.screens.common.toolbar.ToolbarViewMvc;
import com.akshansh.weatherapi.screens.common.toolbar.ToolbarViewMvcImpl;
import com.akshansh.weatherapi.screens.main.main.MainViewMvc;
import com.akshansh.weatherapi.screens.main.main.MainViewMvcImpl;
import com.akshansh.weatherapi.screens.main.forecastitem.ForecastListItemViewMvc;
import com.akshansh.weatherapi.screens.main.forecastitem.ForecastListItemViewMvcImpl;
import com.akshansh.weatherapi.screens.main.forecastitem.dayselectitem.DaySelectItemViewMvcImpl;
import com.akshansh.weatherapi.screens.main.forecastitem.dayselectitem.DaySelectListItemViewMvc;
import com.akshansh.weatherapi.screens.main.headeritem.HeaderListItemViewMvc;
import com.akshansh.weatherapi.screens.main.headeritem.HeaderListItemViewMvcImpl;
import com.akshansh.weatherapi.screens.main.precipitationitem.PrecipitationListItemViewMvc;
import com.akshansh.weatherapi.screens.main.precipitationitem.PrecipitationListItemViewMvcImpl;
import com.akshansh.weatherapi.screens.main.weatherdetailsitem.WeatherDetailsItemViewMvc;
import com.akshansh.weatherapi.screens.main.weatherdetailsitem.WeatherDetailsViewMvcImpl;

public class ViewMvcFactory {
    private final LayoutInflater inflater;

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

    public PrecipitationListItemViewMvc getPrecipitationListItemViewMvc(ViewGroup parent){
        return new PrecipitationListItemViewMvcImpl(inflater,parent);
    }

    public ForecastListItemViewMvc getForecastListItemViewMvc(ViewGroup parent) {
        return new ForecastListItemViewMvcImpl(inflater,parent,this);
    }

    public DaySelectListItemViewMvc getDaySelectListItemViewMvc(ViewGroup parent) {
        return new DaySelectItemViewMvcImpl(inflater,parent);
    }
}
