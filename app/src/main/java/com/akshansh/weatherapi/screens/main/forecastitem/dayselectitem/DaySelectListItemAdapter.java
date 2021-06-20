package com.akshansh.weatherapi.screens.main.forecastitem.dayselectitem;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akshansh.weatherapi.common.ViewMvcFactory;
import com.akshansh.weatherapi.networking.weathermodels.forecastdata.ForecastData;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class DaySelectListItemAdapter extends RecyclerView.Adapter<DaySelectItemViewHolder>
        implements DaySelectListItemViewMvc.Listener {
    public interface Listener{
        void OnDaySelected(String key);
    }

    private final ViewMvcFactory viewMvcFactory;
    private final Listener listener;
    private TreeMap<String, List<ForecastData>> forecastDataTreeMap;
    private List<List<ForecastData>> valueSet;
    private List<String> keySey;
    private int selectedItemIndex;

    public DaySelectListItemAdapter(ViewMvcFactory viewMvcFactory,Listener listener) {
        this.viewMvcFactory = viewMvcFactory;
        this.listener = listener;
        selectedItemIndex =  0;
    }

    public void bindTreeMap(TreeMap<String,List<ForecastData>> forecastDataTreeMap){
        this.forecastDataTreeMap = new TreeMap<>(forecastDataTreeMap);
        valueSet = new ArrayList<>(forecastDataTreeMap.values());
        keySey = new ArrayList<>(forecastDataTreeMap.keySet());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DaySelectItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DaySelectListItemViewMvc viewMvc = viewMvcFactory.getDaySelectListItemViewMvc(parent);
        return new DaySelectItemViewHolder(viewMvc);
    }

    @Override
    public void onBindViewHolder(@NonNull DaySelectItemViewHolder holder, int position) {
        holder.viewMvc.bindView(valueSet.get(position));
        holder.viewMvc.registerListener(this);
        holder.viewMvc.setSelected(position == selectedItemIndex);
    }

    @Override
    public int getItemCount() {
        return forecastDataTreeMap.size();
    }

    @Override
    public void OnDaySelected(String key) {
        selectedItemIndex = keySey.indexOf(key);
        listener.OnDaySelected(key);
        notifyDataSetChanged();
    }
}
