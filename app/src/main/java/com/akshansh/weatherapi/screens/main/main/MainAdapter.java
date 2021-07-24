package com.akshansh.weatherapi.screens.main.main;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akshansh.weatherapi.common.ViewMvcFactory;
import com.akshansh.weatherapi.networking.weathermodels.CurrentWeatherData;
import com.akshansh.weatherapi.networking.weathermodels.WeatherForecastData;
import com.akshansh.weatherapi.screens.main.forecastitem.ForecastListItemViewHolder;
import com.akshansh.weatherapi.screens.main.forecastitem.ForecastListItemViewMvc;
import com.akshansh.weatherapi.screens.main.headeritem.HeaderListItemViewHolder;
import com.akshansh.weatherapi.screens.main.headeritem.HeaderListItemViewMvc;
import com.akshansh.weatherapi.screens.main.precipitationitem.PrecipitationListItemViewHolder;
import com.akshansh.weatherapi.screens.main.precipitationitem.PrecipitationListItemViewMvc;
import com.akshansh.weatherapi.screens.main.weatherdetailsitem.WeatherDetailsItemViewHolder;
import com.akshansh.weatherapi.screens.main.weatherdetailsitem.WeatherDetailsItemViewMvc;

public class MainAdapter extends RecyclerView.Adapter {
    private static final int WEATHER_HEADER = 1;
    private static final int WEATHER_DETAILS = 2;
    private static final int PRECIPITATION = 3;
    private static final int WEATHER_FORECAST = 4;

    private CurrentWeatherData weatherData;
    private WeatherForecastData weatherForecastData;
    private final ViewMvcFactory viewMvcFactory;
    private boolean progressBarVisible = false;

    public MainAdapter(ViewMvcFactory viewMvcFactory) {
        this.viewMvcFactory = viewMvcFactory;
    }

    public void bindView(CurrentWeatherData weatherData, WeatherForecastData weatherForecastData){
        this.weatherData = weatherData;
        this.weatherForecastData = weatherForecastData;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        switch (position){
            case 0:
                return WEATHER_HEADER;
            case 1:
                return WEATHER_DETAILS;
            case 2:
                return PRECIPITATION;
            case 3:
                return WEATHER_FORECAST;
            default:
                throw new RuntimeException("Invalid position");
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case WEATHER_HEADER:
                HeaderListItemViewMvc headerListItemViewMvc = viewMvcFactory
                        .getHeaderListItemViewMvc(parent);
                return new HeaderListItemViewHolder(headerListItemViewMvc);
            case WEATHER_DETAILS:
                WeatherDetailsItemViewMvc weatherDetailsItemViewMvc = viewMvcFactory
                        .getWeatherDetailsItemViewMvc(parent);
                return new WeatherDetailsItemViewHolder(weatherDetailsItemViewMvc);
            case PRECIPITATION:
                PrecipitationListItemViewMvc precipitationListItemViewMvc = viewMvcFactory.
                        getPrecipitationListItemViewMvc(parent);
                return new PrecipitationListItemViewHolder(precipitationListItemViewMvc);
            case WEATHER_FORECAST:
                ForecastListItemViewMvc forecastListItemViewMvc = viewMvcFactory
                        .getForecastListItemViewMvc(parent);
                return new ForecastListItemViewHolder(forecastListItemViewMvc);
            default:
                throw new RuntimeException("Invalid view type");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (position){
            case 0:
                HeaderListItemViewHolder headerListItemViewHolder = (HeaderListItemViewHolder)holder;
                headerListItemViewHolder.viewMvc.bindView(weatherData);
                break;
            case 1:
                WeatherDetailsItemViewHolder weatherDetailsItemViewHolder =
                        (WeatherDetailsItemViewHolder)holder;
                weatherDetailsItemViewHolder.viewMvc.bindView(weatherData);
                break;
            case 2:
                PrecipitationListItemViewHolder precipitationListItemViewHolder =
                        (PrecipitationListItemViewHolder)holder;
                precipitationListItemViewHolder.viewMvc.bindRainData(weatherData.getRain());
                precipitationListItemViewHolder.viewMvc.bindSnowData(weatherData.getSnow());
                break;
            case 3:
                ForecastListItemViewHolder forecastListItemViewHolder =
                        (ForecastListItemViewHolder)holder;
                forecastListItemViewHolder.viewMvc.bindView(weatherForecastData);
                forecastListItemViewHolder.viewMvc.setProgressBarVisible(progressBarVisible);
                break;
            default:
                throw new RuntimeException("invalid position");
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public void setProgressBarVisible(boolean visible) {
        progressBarVisible = visible;
        notifyItemChanged(3);
    }
}
