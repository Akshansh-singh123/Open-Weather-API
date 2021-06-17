package com.akshansh.weatherapi.networking.weathermodels;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CurrentWeatherData {
    @SerializedName("coord")
    private Coordinates coordinates;
    @SerializedName("weather")
    private ArrayList<Weather> weather;
    @SerializedName("base")
    private String base;
    @SerializedName("main")
    private MainForecast forecast;
    @SerializedName("visibility")
    private int visibility;
    @SerializedName("wind")
    private Wind wind;
    @SerializedName("rain")
    private Precipitation precipitation;
    @SerializedName("snow")
    private Precipitation snow;
    @SerializedName("clouds")
    private Clouds clouds;
    @SerializedName("dt")
    private long weatherTimestamp;
    @SerializedName("sys")
    private WeatherSystem system;
    @SerializedName("timezone")
    private long timezone;
    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String cityName;
    @SerializedName("cod")
    private String codeOfDelivery;

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public ArrayList<Weather> getWeather() {
        return weather;
    }

    public String getBase() {
        return base;
    }

    public MainForecast getForecast() {
        return forecast;
    }

    public int getVisibility() {
        return visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public Precipitation getRain() {
        return precipitation;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public long getWeatherTimestamp() {
        return weatherTimestamp;
    }

    public WeatherSystem getSystem() {
        return system;
    }

    public long getTimezone() {
        return timezone;
    }

    public long getId() {
        return id;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCodeOfDelivery() {
        return codeOfDelivery;
    }

    @Override
    public String toString() {
        return "CurrentWeatherData{" +
                "\ncoordinates=" + coordinates +
                "\nweather=" + weather +
                "\nbase='" + base + '\'' +
                "\nforecast=" + forecast +
                "\nvisibility=" + visibility +
                "\nwind=" + wind +
                "\nrain=" + precipitation +
                "\nclouds=" + clouds +
                "\nweatherTimeStamp=" + weatherTimestamp +
                "\nsystem=" + system +
                "\ntimezone=" + timezone +
                "\nid=" + id +
                "\ncityName='" + cityName + '\'' +
                "\ncodeOfDelivery='" + codeOfDelivery + '\'' +
                '}';
    }
}
