package com.akshansh.weatherapi.networking.weathermodels;

import com.google.gson.annotations.SerializedName;

public class Rain {
    @SerializedName("1h")
    private double oneHourRainInMM;

    public double getOneHourRainInMM() {
        return oneHourRainInMM;
    }

    @Override
    public String toString() {
        return "Rain{" +
                "oneHourRainInMM=" + oneHourRainInMM +
                '}';
    }
}
