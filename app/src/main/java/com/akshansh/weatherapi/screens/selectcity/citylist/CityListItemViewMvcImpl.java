package com.akshansh.weatherapi.screens.selectcity.citylist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.akshansh.weatherapi.databinding.ListItemLayoutBinding;
import com.akshansh.weatherapi.networking.citymodels.City;
import com.akshansh.weatherapi.screens.common.views.BaseObservableViewMvc;

public class CityListItemViewMvcImpl extends BaseObservableViewMvc<CityListItemViewMvc.Listener>
        implements CityListItemViewMvc{
    private final TextView cityTextView;
    private final TextView countryTextView;
    private final View container;

    public CityListItemViewMvcImpl(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent) {
        ListItemLayoutBinding binding = ListItemLayoutBinding.inflate(inflater,parent,false);
        cityTextView = binding.cityNameTextView;
        countryTextView = binding.countryTextView;
        container = binding.getRoot();
        setRootView(binding.getRoot());
    }

    @Override
    public void bindView(City city) {
        container.setOnClickListener((view)->{
            for(Listener listener: getListeners()){
                listener.OnItemSelected(city);
            }
        });
    }

    @Override
    public void bindCityName(String city) {
        cityTextView.setText(city);
    }

    @Override
    public void bindCountryName(String country) {
        countryTextView.setText(country);
    }
}
