package com.akshansh.weatherapi.common.graphics;

import com.akshansh.weatherapi.R;

import java.util.HashMap;

public class IconLoaderHelper {
    private static final HashMap<String,Integer> weatherIconMap = new HashMap<>();

    public static int getWeatherIcon(String iconCode){
        initializeIconMap();
        if(weatherIconMap.containsKey(iconCode))
            return weatherIconMap.get(iconCode);
        return R.drawable.main_icon;
    }

    private static void initializeIconMap() {
        weatherIconMap.put("01d", R.drawable.ic_day_clear);
        weatherIconMap.put("01n", R.drawable.ic_night_clear);
        weatherIconMap.put("02d",R.drawable.ic_day_cloudy);
        weatherIconMap.put("02n",R.drawable.ic_night_cloudy);
        weatherIconMap.put("03d",R.drawable.ic_scattered_clouds);
        weatherIconMap.put("03n",R.drawable.ic_scattered_clouds);
        weatherIconMap.put("04d",R.drawable.ic_broken_clouds);
        weatherIconMap.put("04n",R.drawable.ic_broken_clouds);
        weatherIconMap.put("09d",R.drawable.ic_shower_rain);
        weatherIconMap.put("09n",R.drawable.ic_shower_rain);
        weatherIconMap.put("10d",R.drawable.ic_day_rain);
        weatherIconMap.put("10n",R.drawable.ic_night_rain);
        weatherIconMap.put("11d",R.drawable.ic_day_thunderstorm);
        weatherIconMap.put("11n",R.drawable.ic_night_thunderstorm);
        weatherIconMap.put("13d",R.drawable.ic_snow);
        weatherIconMap.put("13n",R.drawable.ic_snow);
        weatherIconMap.put("50d",R.drawable.ic_mist);
        weatherIconMap.put("50n",R.drawable.ic_mist);
    }
}
