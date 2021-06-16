package com.akshansh.weatherapi.networking.weathermodels;

import com.google.gson.annotations.SerializedName;

public class WeatherSystem {
    @SerializedName("type")
    private int type;
    @SerializedName("id")
    private int id;
    @SerializedName("country")
    private String country;
    @SerializedName("sunrise")
    private long sunriseTime;
    @SerializedName("sunset")
    private long sunsetTime;


    public int getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public long getSunriseTime() {
        return sunriseTime;
    }

    public long getSunsetTime() {
        return sunsetTime;
    }

    @Override
    public String toString() {
        return "WeatherSystem{" +
                "type=" + type +
                ", id=" + id +
                ", country='" + country + '\'' +
                ", sunriseTime=" + sunriseTime +
                ", sunsetTime=" + sunsetTime +
                '}';
    }
}
