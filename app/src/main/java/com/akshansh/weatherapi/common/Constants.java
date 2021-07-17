package com.akshansh.weatherapi.common;

import com.akshansh.weatherapi.R;

import java.util.HashMap;

public class Constants {
    public static final String ENDPOINT = "https://api.openweathermap.org/data/2.5/";
    public static final String API_KEY = "70e0001d2e515fd84953359565e83ae9";
    public static final int LOCATION_REQUEST_CODE = 100;
    private static final HashMap<String, Integer> dayDrawables = new HashMap<>();
    private static final HashMap<String, Integer> nightDrawables = new HashMap<>();

    public static int getDayDrawable(String title){
        dayDrawables.put("thunderstorm",R.drawable.day_thunderstorm);
        dayDrawables.put("drizzle",R.drawable.day_rainy);
        dayDrawables.put("rain",R.drawable.day_rainy);
        dayDrawables.put("snow",R.drawable.day_snow);
        dayDrawables.put("atmosphere",R.drawable.day_atmosphere);
        dayDrawables.put("haze",R.drawable.day_atmosphere);
        dayDrawables.put("clear",R.drawable.day_clear);
        dayDrawables.put("clouds",R.drawable.day_cloudy);
        if (dayDrawables.containsKey(title))
            return dayDrawables.get(title);
        else
            return dayDrawables.get("clear");
    }

    public static int getNightDrawable(String title){
        nightDrawables.put("thunderstorm",R.drawable.night_thunderstorm);
        nightDrawables.put("drizzle",R.drawable.night_rainy);
        nightDrawables.put("rain",R.drawable.night_rainy);
        nightDrawables.put("snow",R.drawable.night_snow);
        nightDrawables.put("atmosphere",R.drawable.night_atmosphere);
        nightDrawables.put("haze",R.drawable.night_atmosphere);
        nightDrawables.put("clear",R.drawable.night_clear);
        nightDrawables.put("clouds",R.drawable.night_cloudy);
        if (nightDrawables.containsKey(title))
            return nightDrawables.get(title);
        else
            return nightDrawables.get("clear");
    }
}
