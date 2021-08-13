package com.akshansh.weatherapi.networking.weathermodels;

import com.akshansh.weatherapi.networking.weathermodels.city.WeatherCity;
import com.akshansh.weatherapi.networking.weathermodels.forecastdata.ForecastData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherForecastData {
    @SerializedName("cod")
    private String deliveryCode;
    private String message;
    @SerializedName("cnt")
    private int weatherDataCount;
    @SerializedName("list")
    private List<ForecastData> forecastData;
    private WeatherCity weatherCity;

    public String getDeliveryCode() {
        return deliveryCode;
    }

    public String getMessage() {
        return message;
    }

    public int getWeatherDataCount() {
        return weatherDataCount;
    }

    public List<ForecastData> getForecastData() {
        return forecastData;
    }

    public WeatherCity getCity() {
        return weatherCity;
    }

    @Override
    public String toString() {
        return "WeatherForecastData{" +
                "deliveryCode='" + deliveryCode + '\'' +
                ", message='" + message + '\'' +
                ", weatherDataCount=" + weatherDataCount +
                ", forecastData=" + forecastData +
                ", city=" + weatherCity +
                '}';
    }
}
