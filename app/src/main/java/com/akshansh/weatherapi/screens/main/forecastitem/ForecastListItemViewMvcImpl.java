package com.akshansh.weatherapi.screens.main.forecastitem;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akshansh.weatherapi.R;
import com.akshansh.weatherapi.common.ViewMvcFactory;
import com.akshansh.weatherapi.databinding.WeatherForecastListItemBinding;
import com.akshansh.weatherapi.networking.weathermodels.WeatherForecastData;
import com.akshansh.weatherapi.networking.weathermodels.forecastdata.ForecastData;
import com.akshansh.weatherapi.screens.common.views.BaseViewMvc;
import com.akshansh.weatherapi.screens.main.forecastitem.dayselectitem.DaySelectListItemAdapter;
import com.google.android.material.slider.Slider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TreeMap;

public class ForecastListItemViewMvcImpl extends BaseViewMvc implements ForecastListItemViewMvc, DaySelectListItemAdapter.Listener {
    private WeatherForecastListItemBinding binding;
    private final Slider hourSlider;
    private final TextView weatherDescriptionTextView;
    private final TextView mainTemperatureTextView;
    private final ImageView weatherIcon;
    private final TextView precipitationTextView;
    private final TextView humidityTextView;
    private final TextView windSpeedTextView;
    private final TextView temperatureUnitTextView;
    private final RecyclerView recyclerView;
    private final SimpleDateFormat dateFormat;
    private final TreeMap<String, List<ForecastData>> forecastDataByDates;
    private final DaySelectListItemAdapter adapter;
    private final SimpleDateFormat keyFormat;

    private ValueChangeListener listener;

    public ForecastListItemViewMvcImpl(@NonNull LayoutInflater inflater, ViewGroup parent,
                                       ViewMvcFactory viewMvcFactory) {
        binding = WeatherForecastListItemBinding.inflate(inflater,parent,false);
        setRootView(binding.getRoot());

        hourSlider = binding.hourlySlider;
        weatherDescriptionTextView = binding.weatherDescriptionText;
        mainTemperatureTextView = binding.mainTemperature;
        weatherIcon = binding.mainWeatherIcon;
        precipitationTextView = binding.dayPrecipitationText;
        humidityTextView = binding.dayHumidityText;
        windSpeedTextView = binding.dayWindSpeed;
        temperatureUnitTextView = binding.temperatureUnitText;
        recyclerView = binding.recyclerView;

        adapter = new DaySelectListItemAdapter(viewMvcFactory,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        dateFormat = new SimpleDateFormat("EEE, h aa, ", Locale.ENGLISH);
        keyFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        forecastDataByDates = new TreeMap<>();
    }

    @Override
    public void bindView(WeatherForecastData weatherForecastData) {
        hashForecastDataByDate(weatherForecastData.getForecastData());
    }

    @Override
    public void OnDaySelected(String key) {
        if(listener != null){
            hourSlider.removeOnChangeListener(listener);
        }

        List<ForecastData> selectedDayForecast = forecastDataByDates.get(key);
        listener = new ValueChangeListener(selectedDayForecast);
        hourSlider.setValueFrom(0);
        hourSlider.setStepSize(1);
        hourSlider.setValueTo(selectedDayForecast.size()-1);
        hourSlider.setValue(0);
        bindWeatherDescription(selectedDayForecast.get(0));
        bindMainTemperature(selectedDayForecast.get(0));
        bindWeatherForecastData(selectedDayForecast.get(0));
        hourSlider.addOnChangeListener(listener);
        temperatureUnitTextView.setText(String.format(Locale.ENGLISH,"%sC",
                getString(R.string.degrees_symbol)));
    }

    private void bindWeatherDescription(ForecastData forecastData) {
        long weatherTimestamp = forecastData.getWeatherTimestamp()*1000L;
        String time = dateFormat.format(new Date(weatherTimestamp));
        String weatherTitle = forecastData.getWeather().get(0).getWeatherTitle();
        weatherDescriptionTextView.setText(time.concat(weatherTitle));
    }

    private void bindMainTemperature(ForecastData forecastData){
        mainTemperatureTextView.setText(String.format(Locale.ENGLISH,"%d",
                Math.round(forecastData.getMainForecast().getTemperature())));
    }

    private void bindWeatherForecastData(ForecastData forecastData){
        String precipitation = String.format(Locale.ENGLISH,"Precipitation Chance: %d%%",
                Math.round(forecastData.getProbabilityOfPrecipitation()*100));
        String humidity = String.format(Locale.ENGLISH,"Humidity: %d%%",
                Math.round(forecastData.getMainForecast().getHumidity()));
        String windSpeed = String.format(Locale.ENGLISH,"Wind speed: %d m/sec",
                Math.round(forecastData.getWind().getWindSpeed()));
        precipitationTextView.setText(precipitation);
        humidityTextView.setText(humidity);
        windSpeedTextView.setText(windSpeed);
    }

    @UiThread
    private void hashForecastDataByDate(List<ForecastData> forecastData) {
        forecastDataByDates.clear();
        for (ForecastData data : forecastData) {
            String day = keyFormat.format(new Date(data.getWeatherTimestamp()*1000L));
            if (!forecastDataByDates.containsKey(day)) {
                forecastDataByDates.put(day, new ArrayList<>());
            }
            forecastDataByDates.get(day).add(data);
        }
        adapter.bindTreeMap(forecastDataByDates);
        OnDaySelected(forecastDataByDates.firstKey());
    }

    private class ValueChangeListener implements Slider.OnChangeListener{
        private final List<ForecastData> selectedForecastData;

        public ValueChangeListener(List<ForecastData> selectedForecastData) {
            this.selectedForecastData = selectedForecastData;
        }

        @Override
        public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
            int index = (int)value;
            bindWeatherDescription(selectedForecastData.get(index));
            bindMainTemperature(selectedForecastData.get(index));
            bindWeatherForecastData(selectedForecastData.get(index));
        }
    }
}
