package com.akshansh.weatherapi.screens.main.weatherdetailsitem;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.akshansh.weatherapi.R;
import com.akshansh.weatherapi.databinding.WeatherDetailsItemBinding;
import com.akshansh.weatherapi.networking.weathermodels.CurrentWeatherData;
import com.akshansh.weatherapi.screens.common.views.BaseViewMvc;

public class WeatherDetailsViewMvcImpl extends BaseViewMvc implements WeatherDetailsItemViewMvc {
    private WeatherDetailsItemBinding binding;
    private final TextView feelsLikeTextView;
    private final TextView windSpeedTextView;
    private final TextView cloudsTextView;
    private final TextView sunriseTimeTextView;
    private final TextView humidityTextView;
    private final TextView windDirectionTextView;
    private final TextView pressureTextView;
    private final TextView sunsetTimeTextView;

    public WeatherDetailsViewMvcImpl(@NonNull LayoutInflater inflater, ViewGroup parent) {
        binding = WeatherDetailsItemBinding.inflate(inflater,parent,false);
        setRootView(binding.getRoot());
        feelsLikeTextView = binding.feelsLikeText;
        windSpeedTextView = binding.windSpeedText;
        cloudsTextView = binding.cloudsText;
        sunriseTimeTextView = binding.sunriseText;
        humidityTextView = binding.humidityText;
        windDirectionTextView = binding.windDirectionText;
        pressureTextView = binding.pressureText;
        sunsetTimeTextView = binding.sunsetText;
    }

    @Override
    public void bindView(CurrentWeatherData weatherData) {
        setFeelsLikeTemperature("24");
        setWindSpeed("12.5");
        setClouds("62.5");
        setSunrise("5:30 am");
        setHumidityText("77%");
        setWindDirectionText("181.5");
        setPressureText("1105 mBar");
        setSunsetTimeText("6:30 pm");
    }

    private void setSunsetTimeText(String sunsetTime) {
        sunsetTimeTextView.setText(sunsetTime);
    }

    private void setPressureText(String pressure) {
        pressureTextView.setText(pressure);
    }

    private void setWindDirectionText(String windDirection) {
        windDirectionTextView.setText(String.format("%s%s",windDirection,
                getString(R.string.degrees_symbol)));
    }

    private void setHumidityText(String humidity) {
        humidityTextView.setText(String.format("%s",humidity));
    }

    private void setSunrise(String sunriseTime) {
        sunriseTimeTextView.setText(String.format("%s",sunriseTime));
    }

    private void setClouds(String cloudsPercentage) {
        cloudsTextView.setText(String.format("%s %%",cloudsPercentage));
    }

    private void setWindSpeed(String speed) {
        windSpeedTextView.setText(String.format("%s km/h",speed));
    }

    private void setFeelsLikeTemperature(String temperature) {
        feelsLikeTextView
                .setText(String.format("%s%sC",temperature,getString(R.string.degrees_symbol)));
    }
}
