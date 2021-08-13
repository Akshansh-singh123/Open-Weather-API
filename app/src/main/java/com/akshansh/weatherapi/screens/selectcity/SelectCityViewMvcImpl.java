package com.akshansh.weatherapi.screens.selectcity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akshansh.weatherapi.common.ViewMvcFactory;
import com.akshansh.weatherapi.databinding.ActivitySelectCityBinding;
import com.akshansh.weatherapi.networking.citymodels.City;
import com.akshansh.weatherapi.screens.common.views.BaseObservableViewMvc;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class SelectCityViewMvcImpl extends BaseObservableViewMvc<SelectCityViewMvc.Listener>
        implements SelectCityViewMvc, CitiesAdapter.Listener {
    private final RecyclerView recyclerView;
    private final TextInputLayout textInputLayout;
    private final TextInputEditText textInputEditText;
    private final ProgressBar progressBar;
    private final TextView messageTextView;
    private final CitiesAdapter adapter;

    public SelectCityViewMvcImpl(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup parent,
                                 ViewMvcFactory viewMvcFactory) {
        ActivitySelectCityBinding binding = ActivitySelectCityBinding
                .inflate(layoutInflater,parent,false);
        setRootView(binding.getRoot());
        adapter = new CitiesAdapter(viewMvcFactory,this);
        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        textInputEditText = binding.textInputEditText;
        textInputLayout = binding.textInputLayout;
        progressBar = binding.progressBar;
        messageTextView = binding.messageTextView;
        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                for (Listener listener : getListeners()) {
                    listener.onTextTyped(s.toString());
                }
            }
        });
    }

    @Override
    public void bindView(List<City> cities) {
        recyclerView.setVisibility(View.VISIBLE);
        messageTextView.setVisibility(View.GONE);
        adapter.bindView(cities);
    }

    @Override
    public void cityNotFound() {
        recyclerView.setVisibility(View.GONE);
        messageTextView.setVisibility(View.VISIBLE);
        messageTextView.setText("City not found");
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        messageTextView.setVisibility(View.GONE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        messageTextView.setVisibility(View.GONE);
    }

    @Override
    public void OnCitySelected(City city) {
        hideKeyboard(textInputEditText);
        for(Listener listener: getListeners()){
            listener.onCitySelected(city);
        }
    }
}
