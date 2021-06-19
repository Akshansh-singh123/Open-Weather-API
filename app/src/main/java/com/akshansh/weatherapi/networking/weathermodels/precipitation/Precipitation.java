package com.akshansh.weatherapi.networking.weathermodels.precipitation;

import com.google.gson.annotations.SerializedName;

public class Precipitation {
    @SerializedName("1h")
    private double oneHourPPTInMM;
    @SerializedName("3h")
    private double threeHourPPTInMM;


    public double getOneHourPPTInMM() {
        return oneHourPPTInMM;
    }

    public double getThreeHourPPTInMM() {
        return threeHourPPTInMM;
    }

    @Override
    public String toString() {
        return "Precipitation{" +
                "oneHourPPTInMM=" + oneHourPPTInMM +
                ", threeHourPPTInMM=" + threeHourPPTInMM +
                '}';
    }
}
