package com.akshansh.weatherapi.networking.weathermodels;

import com.google.gson.annotations.SerializedName;

public class Wind {
    @SerializedName("speed")
    private double windSpeed;
    @SerializedName("deg")
    private double windDegrees;

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getWindDegrees() {
        return windDegrees;
    }

    @Override
    public String toString() {
        return "Wind{" +
                "windSpeed=" + windSpeed +
                ", windDegrees=" + windDegrees +
                '}';
    }
}
