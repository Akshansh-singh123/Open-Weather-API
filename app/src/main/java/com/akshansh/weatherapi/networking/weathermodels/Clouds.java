package com.akshansh.weatherapi.networking.weathermodels;

import com.google.gson.annotations.SerializedName;

public class Clouds {
    @SerializedName("all")
    private int cloudPercentage;

    public int getCloudPercentage() {
        return cloudPercentage;
    }

    @Override
    public String toString() {
        return "Clouds{" +
                "cloudPercentage=" + cloudPercentage +
                '}';
    }
}
