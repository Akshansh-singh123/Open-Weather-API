package com.akshansh.weatherapi.networking.weathermodels.forecastdata;

import com.akshansh.weatherapi.networking.weathermodels.mainforecast.MainForecast;
import com.akshansh.weatherapi.networking.weathermodels.weather.Weather;
import com.akshansh.weatherapi.networking.weathermodels.clouds.Clouds;
import com.akshansh.weatherapi.networking.weathermodels.system.ForecastSystem;
import com.akshansh.weatherapi.networking.weathermodels.wind.Wind;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ForecastData {
    @SerializedName("dt")
    private long weatherTimestamp;
    @SerializedName("main")
    private MainForecast mainForecast;
    private List<Weather> weather;
    private Clouds clouds;
    private Wind wind;
    private long visibility;
    @SerializedName("pop")
    private double probabilityOfPrecipitation;
    @SerializedName("sys")
    private ForecastSystem system;
    @SerializedName("dt_txt")
    private String dateTime;

    public long getWeatherTimestamp() {
        return weatherTimestamp;
    }

    public MainForecast getMainForecast() {
        return mainForecast;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public Wind getWind() {
        return wind;
    }

    public long getVisibility() {
        return visibility;
    }

    public double getProbabilityOfPrecipitation() {
        return probabilityOfPrecipitation;
    }

    public ForecastSystem getSystem() {
        return system;
    }

    public String getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return "ForecastData{" +
                "weatherTimestamp=" + weatherTimestamp +
                ", mainForecast=" + mainForecast +
                ", weather=" + weather +
                ", clouds=" + clouds +
                ", wind=" + wind +
                ", visibility=" + visibility +
                ", probabilityOfPrecipitation=" + probabilityOfPrecipitation +
                ", system=" + system +
                ", dateTime='" + dateTime + '\'' +
                '}';
    }
}
