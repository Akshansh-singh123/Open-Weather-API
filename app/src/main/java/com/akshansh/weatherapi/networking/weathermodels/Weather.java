package com.akshansh.weatherapi.networking.weathermodels;

import com.google.gson.annotations.SerializedName;

public class Weather {
    @SerializedName("id")
    private int id;
    @SerializedName("main")
    private String weatherTitle;
    @SerializedName("description")
    private String weatherDescription;
    @SerializedName("icon")
    private String icon;

    public int getId() {
        return id;
    }

    public String getWeatherTitle() {
        return weatherTitle;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public String getIcon() {
        return icon;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "id=" + id +
                ", weatherTitle='" + weatherTitle + '\'' +
                ", weatherDescription='" + weatherDescription + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
