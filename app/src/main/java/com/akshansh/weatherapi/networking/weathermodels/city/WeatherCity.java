package com.akshansh.weatherapi.networking.weathermodels.city;

import com.akshansh.weatherapi.networking.weathermodels.coordinates.Coordinates;
import com.google.gson.annotations.SerializedName;

public class WeatherCity {
    private long id;
    @SerializedName("name")
    private String cityName;
    @SerializedName("coord")
    private Coordinates coordinates;
    private String country;
    private long population;
    private long sunrise;
    private long sunset;

    public long getId() {
        return id;
    }

    public String getCityName() {
        return cityName;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public String getCountry() {
        return country;
    }

    public long getPopulation() {
        return population;
    }

    public long getSunrise() {
        return sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", cityName='" + cityName + '\'' +
                ", coordinates=" + coordinates +
                ", country='" + country + '\'' +
                ", population=" + population +
                ", sunrise=" + sunrise +
                ", sunset=" + sunset +
                '}';
    }
}
