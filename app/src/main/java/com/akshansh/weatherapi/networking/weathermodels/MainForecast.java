package com.akshansh.weatherapi.networking.weathermodels;

import com.google.gson.annotations.SerializedName;

public class MainForecast {
    @SerializedName("temp")
    private double temperature;
    @SerializedName("feels_like")
    private double feelsLikeTemp;
    @SerializedName("temp_min")
    private double minTemperature;
    @SerializedName("temp_max")
    private double maxTemperature;
    @SerializedName("pressure")
    private double pressure;
    @SerializedName("humidity")
    private double humidity;

    public double getTemperature() {
        return temperature;
    }

    public double getFeelsLikeTemp() {
        return feelsLikeTemp;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public double getPressure() {
        return pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    @Override
    public String toString() {
        return "MainForecast{" +
                "temperature=" + temperature +
                ", feelsLikeTemp=" + feelsLikeTemp +
                ", minTemperature=" + minTemperature +
                ", maxTemperature=" + maxTemperature +
                ", pressure=" + pressure +
                ", humidity=" + humidity +
                '}';
    }
}
