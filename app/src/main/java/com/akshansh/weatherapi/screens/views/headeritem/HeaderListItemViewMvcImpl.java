package com.akshansh.weatherapi.screens.views.headeritem;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;

import com.akshansh.weatherapi.R;
import com.akshansh.weatherapi.common.graphics.IconLoaderHelper;
import com.akshansh.weatherapi.databinding.WeatherHeaderItemBinding;
import com.akshansh.weatherapi.networking.weathermodels.CurrentWeatherData;
import com.akshansh.weatherapi.screens.common.views.BaseViewMvc;
import com.google.android.material.textview.MaterialTextView;

import java.util.Locale;

public class HeaderListItemViewMvcImpl extends BaseViewMvc implements HeaderListItemViewMvc {
    private WeatherHeaderItemBinding binding;
    private final TextView temperatureTextView;
    private final TextView temperatureUnitsTextView;
    private final MaterialTextView weatherTitleTextView;
    private final TextView weatherDescriptionTextView;

    public HeaderListItemViewMvcImpl(@NonNull LayoutInflater inflater, ViewGroup parent) {
        binding = WeatherHeaderItemBinding.inflate(inflater,parent,false);
        setRootView(binding.getRoot());
        temperatureTextView = binding.mainTemperature;
        temperatureUnitsTextView = binding.temperatureUnitText;
        weatherTitleTextView = binding.weatherTitle;
        weatherDescriptionTextView  = binding.weatherDescriptionText;
    }

    @Override
    public void bindView(CurrentWeatherData weatherData) {
        if (weatherData != null) {
            setMainTemperature(weatherData.getForecast().getTemperature());
            setTemperatureUnits();
            setWeatherTitle(weatherData.getWeather().get(0).getWeatherTitle());
            setWeatherIcon(weatherData.getWeather().get(0).getIcon());
            setWeatherDescription(weatherData.getWeather().get(0).getWeatherDescription());
        }
    }

    private void setWeatherIcon(String icon) {
        weatherTitleTextView
                .setCompoundDrawablesWithIntrinsicBounds(IconLoaderHelper.getWeatherIcon(icon),
                0,0,0);
    }

    private void setMainTemperature(double temperature) {
        temperatureTextView.setText(String.format(Locale.ENGLISH,
                "%d",Math.round(temperature)));
    }

    private void setTemperatureUnits() {
        temperatureUnitsTextView.setText(String.format("%sC",getString(R.string.degrees_symbol)));
    }

    private void setWeatherTitle(String title) {
        weatherTitleTextView.setText(title);
    }

    private void setWeatherDescription(String description) {
        weatherDescriptionTextView.setText(String.format("%s%s",
                description.substring(0, 1).toUpperCase(),
                description.substring(1).toLowerCase()));
    }
}
