package com.akshansh.weatherapi.screens.main.weatherdetailsitem;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.akshansh.weatherapi.R;
import com.akshansh.weatherapi.databinding.WeatherDetailsItemBinding;
import com.akshansh.weatherapi.networking.weathermodels.CurrentWeatherData;
import com.akshansh.weatherapi.screens.common.views.BaseViewMvc;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

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
    private final SimpleDateFormat timeFormat;

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
        timeFormat = new SimpleDateFormat("hh:mm a",Locale.ENGLISH);
    }

    @Override
    public void bindView(CurrentWeatherData weatherData) {
        if (weatherData != null) {
            setFeelsLikeTemperature(weatherData.getForecast().getFeelsLikeTemp());
            setWindSpeed(weatherData.getWind().getWindSpeed());
            setWindDirectionText(weatherData.getWind().getWindDegrees());
            setClouds(weatherData.getClouds().getCloudPercentage());
            setHumidityText(weatherData.getForecast().getHumidity());
            setPressureText(weatherData.getForecast().getPressure());
            setSunriseTimeText(weatherData.getSystem().getSunriseTime());
            setSunsetTimeText(weatherData.getSystem().getSunsetTime());
        }
    }

    private void setSunriseTimeText(long sunriseTime) {
        String sunriseTimeText = timeFormat.format(new Date(sunriseTime*1000L));
        sunriseTimeTextView.setText(String.format("%s",sunriseTimeText));
    }

    private void setSunsetTimeText(long sunsetTime) {
        String sunsetTimeText = timeFormat.format(new Date(sunsetTime*1000L));
        sunsetTimeTextView.setText(String.format("%s",sunsetTimeText));
    }

    private void setPressureText(double pressure) {
        pressureTextView.setText(String.format(Locale.ENGLISH,"%.2f hPa",pressure));
    }

    private void setWindDirectionText(double windDirection) {
        windDirectionTextView.setText(String.format(Locale.ENGLISH,
                "%.2f%s",windDirection, getString(R.string.degrees_symbol)));
    }

    private void setHumidityText(double humidity) {
        humidityTextView.setText(String.format(Locale.ENGLISH,"%.2f %%",humidity));
    }

    private void setClouds(int cloudsPercentage) {
        cloudsTextView.setText(String.format("%s %%",cloudsPercentage));
    }

    private void setWindSpeed(double speed) {
        windSpeedTextView.setText(String.format(Locale.ENGLISH,"%.2f km/h",speed));
    }

    private void setFeelsLikeTemperature(double temperature) {
        feelsLikeTextView.setText(String.format("%s%sC",temperature,getString(R.string.degrees_symbol)));
    }
}
