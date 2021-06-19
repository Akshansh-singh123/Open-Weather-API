package com.akshansh.weatherapi.common.graphics;


import com.akshansh.weatherapi.common.Constants;
import com.akshansh.weatherapi.networking.weathermodels.CurrentWeatherData;
import com.akshansh.weatherapi.networking.weathermodels.weather.Weather;

public class ImageLoaderHelper {
    public int getBackgroundDrawableResource(CurrentWeatherData weatherData){
        Weather weather = weatherData.getWeather().get(0);
        String title = weather.getWeatherTitle();
        long sunriseTimeStamp = weatherData.getSystem().getSunriseTime()*1000L;
        long sunsetTimeStamp = weatherData.getSystem().getSunsetTime()*1000L;
        long currentTime = System.currentTimeMillis();

        if (currentTime>=sunriseTimeStamp && currentTime<sunsetTimeStamp)
            return getBackgroundDayDrawableResource(title);
        else
            return getBackgroundNightDrawableResource(title);
    }

    private int getBackgroundDayDrawableResource(String title) {
        return Constants.getDayDrawable(title.toLowerCase());
    }

    private int getBackgroundNightDrawableResource(String title) {
        return Constants.getNightDrawable(title.toLowerCase());
    }
}
