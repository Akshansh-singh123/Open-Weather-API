package com.akshansh.weatherapi.networking.weathermodels.system;

import com.google.gson.annotations.SerializedName;

public class ForecastSystem {
    @SerializedName("pod")
    private String partOfDay;

    public String getPartOfDay() {
        return partOfDay;
    }

    @Override
    public String toString() {
        return "ForecastSystem{" +
                "partOfDay='" + partOfDay + '\'' +
                '}';
    }
}
