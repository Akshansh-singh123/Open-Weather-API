package com.akshansh.weatherapi.networking.citymodels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CitiesSchema {
    @SerializedName("cities")
    private List<City> cities;
    @SerializedName("cod")
    private int deliveryCode;

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public int getDeliveryCode() {
        return deliveryCode;
    }

    public void setDeliveryCode(int deliveryCode) {
        this.deliveryCode = deliveryCode;
    }

    @Override
    public String toString() {
        return "deliveryCode=" + deliveryCode;
    }
}
