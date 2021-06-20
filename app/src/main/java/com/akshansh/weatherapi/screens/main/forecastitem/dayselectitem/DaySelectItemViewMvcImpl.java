package com.akshansh.weatherapi.screens.main.forecastitem.dayselectitem;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;

import com.akshansh.weatherapi.R;
import com.akshansh.weatherapi.common.graphics.IconLoaderHelper;
import com.akshansh.weatherapi.databinding.DayForecastListItemBinding;
import com.akshansh.weatherapi.networking.weathermodels.forecastdata.ForecastData;
import com.akshansh.weatherapi.networking.weathermodels.weather.Weather;
import com.akshansh.weatherapi.screens.common.views.BaseObservableViewMvc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DaySelectItemViewMvcImpl extends BaseObservableViewMvc<DaySelectListItemViewMvc.Listener>
        implements DaySelectListItemViewMvc {
    private DayForecastListItemBinding binding;
    private final TextView dayTextView;
    private final TextView maxTemperatureTextView;
    private final TextView minTemperatureTextView;
    private final ImageView weatherIcon;
    private final View container;
    private List<ForecastData> forecastData;

    public DaySelectItemViewMvcImpl(@NonNull LayoutInflater inflater, ViewGroup parent) {
        binding = DayForecastListItemBinding.inflate(inflater,parent,false);
        setRootView(binding.getRoot());
        dayTextView = binding.dayText;
        maxTemperatureTextView = binding.maxTemperature;
        minTemperatureTextView = binding.minTemperature;
        weatherIcon = binding.weatherIcon;
        container = binding.getRoot();
    }

    @Override
    public void bindView(List<ForecastData> forecastData) {
        this.forecastData = new ArrayList<>(forecastData);
        setMinMaxTemperatureView();
        bindWeatherIcon(forecastData.get(0).getWeather());
        bindDay(forecastData.get(0).getWeatherTimestamp());
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select();
            }
        });
    }

    @Override
    public void setSelected(boolean selected) {
        if(selected){
            container.setBackgroundColor(getColor(R.color.selected_background_black));
        }else{
            container.setBackgroundColor(getColor(android.R.color.transparent));
        }
    }

    private void select() {
        SimpleDateFormat keyFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
        String key = keyFormat.format(new Date(forecastData.get(0).getWeatherTimestamp()*1000L));
        for(Listener listener: getListeners()){
            listener.OnDaySelected(key);
        }
    }

    private void bindDay(long weatherTimestamp) {
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEE",Locale.ENGLISH);
        String dayOfWeek = dayFormat.format(weatherTimestamp*1000L);
        dayTextView.setText(dayOfWeek.toUpperCase());
    }

    private void bindWeatherIcon(List<Weather> weather) {
        String iconCode = weather.get(0).getIcon();
        weatherIcon.setImageResource(IconLoaderHelper.getWeatherIcon(iconCode));
    }

    @UiThread
    private void setMinMaxTemperatureView() {
        double minTemperature = forecastData.get(0).getMainForecast().getMinTemperature();
        double maxTemperature = forecastData.get(0).getMainForecast().getMaxTemperature();
        for (ForecastData data : forecastData) {
            if(data.getMainForecast().getMaxTemperature() > maxTemperature){
                maxTemperature = data.getMainForecast().getMaxTemperature();
            }
            if(data.getMainForecast().getMinTemperature() < minTemperature){
                minTemperature = data.getMainForecast().getMinTemperature();
            }
        }

        minTemperatureTextView.setText(String.format(Locale.ENGLISH,
                "%d%sC",Math.round(minTemperature),getString(R.string.degrees_symbol)));
        maxTemperatureTextView.setText(String.format(Locale.ENGLISH,
                "%d%sC",Math.round(maxTemperature),getString(R.string.degrees_symbol)));
    }
}
