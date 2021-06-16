package com.akshansh.weatherapi.screens.main.headeritem;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.akshansh.weatherapi.R;
import com.akshansh.weatherapi.databinding.WeatherHeaderItemBinding;
import com.akshansh.weatherapi.networking.weathermodels.CurrentWeatherData;
import com.akshansh.weatherapi.screens.common.views.BaseViewMvc;

public class HeaderListItemViewMvcImpl extends BaseViewMvc implements HeaderListItemViewMvc {
    private WeatherHeaderItemBinding binding;
    private final TextView temperatureTextView;
    private final TextView temperatureUnitsTextView;
    private final TextView weatherTitleTextView;
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
        setMainTemperature("24");
        setTemperatureUnits();
        setWeatherTitle("Rain");
        setWeatherDescription("Rain, thunderstorm");
    }

    private void setMainTemperature(String temperature) {
        temperatureTextView.setText(temperature);
    }

    private void setTemperatureUnits() {
        temperatureUnitsTextView.setText(String.format("%sC",getString(R.string.degrees_symbol)));
    }

    private void setWeatherTitle(String title) {
        weatherTitleTextView.setText(title);
    }

    private void setWeatherDescription(String description) {
        weatherDescriptionTextView.setText(description);
    }
}
