package com.akshansh.weatherapi.screens.main.precipitationitem;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.akshansh.weatherapi.databinding.WeatherPrecipitationListItemBinding;
import com.akshansh.weatherapi.networking.weathermodels.precipitation.Precipitation;
import com.akshansh.weatherapi.screens.common.views.BaseViewMvc;

import java.util.Locale;

public class PrecipitationListItemViewMvcImpl extends BaseViewMvc implements PrecipitationListItemViewMvc {
    private WeatherPrecipitationListItemBinding binding;
    private final TextView oneHourRainTextView;
    private final TextView threeHourRainTextView;
    private final TextView oneHourSnowTextView;
    private final TextView threeHourSnowTextView;

    public PrecipitationListItemViewMvcImpl(@NonNull LayoutInflater inflater, ViewGroup parent) {
        binding = WeatherPrecipitationListItemBinding.inflate(inflater,parent,false);
        setRootView(binding.getRoot());
        oneHourRainTextView = binding.oneHourRainText;
        threeHourRainTextView = binding.threeHourRainText;
        oneHourSnowTextView = binding.oneHourSnowText;
        threeHourSnowTextView = binding.threeHourSnowText;
    }

    @Override
    public void bindRainData(@Nullable Precipitation rain) {
        if (rain != null) {
            oneHourRainTextView.setText(String.format(Locale.ENGLISH,
                    "Last 1 hour: %.2f mm",rain.getOneHourPPTInMM()));
            threeHourRainTextView.setText(String.format(Locale.ENGLISH,
                    "Last 3 hour: %.2f mm",rain.getOneHourPPTInMM()));
        }else{
            oneHourRainTextView.setText(String.format(Locale.ENGLISH,"Last 1 hour: %s mm",0));
            threeHourRainTextView.setText(String.format(Locale.ENGLISH,"Last 3 hour: %s mm",0));
        }
    }

    @Override
    public void bindSnowData(@Nullable Precipitation snow) {
        if (snow != null) {
            oneHourSnowTextView.setText(String.format(Locale.ENGLISH,
                    "Last 1 hour: %.2f mm",snow.getOneHourPPTInMM()));
            threeHourSnowTextView.setText(String.format(Locale.ENGLISH,
                    "Last 3 hour: %.2f mm",snow.getThreeHourPPTInMM()));
        }else{
            oneHourSnowTextView.setText(String.format(Locale.ENGLISH,"Last 1 hour: %s mm",0));
            threeHourSnowTextView.setText(String.format(Locale.ENGLISH,"Last 3 hour: %s mm",0));
        }
    }
}
