package com.akshansh.weatherapi.screens.selectcity;

import android.annotation.SuppressLint;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akshansh.weatherapi.common.ViewMvcFactory;
import com.akshansh.weatherapi.networking.citymodels.City;
import com.akshansh.weatherapi.screens.selectcity.citylist.CityListItemViewMvc;

import java.util.ArrayList;
import java.util.List;

public class CitiesAdapter extends RecyclerView.Adapter<CitiesAdapter.CitiesViewHolder>
        implements CityListItemViewMvc.Listener{
    public interface Listener{
        void OnCitySelected(City city);
    }

    private final ViewMvcFactory viewMvcFactory;
    private final Listener listener;
    private List<City> cities;

    public CitiesAdapter(ViewMvcFactory viewMvcFactory,Listener listener) {
        this.viewMvcFactory = viewMvcFactory;
        this.listener = listener;
        cities = new ArrayList<>();
    }

    @NonNull
    @Override
    public CitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CitiesViewHolder(viewMvcFactory.getCityListItemViewMvc(parent));
    }

    @Override
    public void onBindViewHolder(@NonNull CitiesViewHolder holder, int position) {
        City city = cities.get(position);
        holder.viewMvc.bindView(city);
        holder.viewMvc.bindCityName(city.getCity());
        holder.viewMvc.bindCountryName(city.getCountry());
        holder.viewMvc.registerListener(this);
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void bindView(List<City> cities){
        this.cities = new ArrayList<>(cities);
        notifyDataSetChanged();
    }

    @Override
    public void OnItemSelected(City city) {
        listener.OnCitySelected(city);
    }

    public static class CitiesViewHolder extends RecyclerView.ViewHolder {
        private final CityListItemViewMvc viewMvc;

        public CitiesViewHolder(@NonNull CityListItemViewMvc viewMvc) {
            super(viewMvc.getRootView());
            this.viewMvc = viewMvc;
        }
    }
}
