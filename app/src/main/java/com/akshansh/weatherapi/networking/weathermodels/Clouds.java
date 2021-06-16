package com.akshansh.weatherapi.networking.weathermodels;

import com.google.gson.annotations.SerializedName;

public class Clouds {
    @SerializedName("all")
    private int altitude;

    public int getAltitude() {
        return altitude;
    }

    @Override
    public String toString() {
        return "Clouds{" +
                "altitude=" + altitude +
                '}';
    }
}
