package com.akshansh.weatherapi.screens.selectcity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.akshansh.weatherapi.databinding.ActivitySelectCityBinding;

public class SelectCityActivity extends AppCompatActivity {
    private ActivitySelectCityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectCityBinding.inflate(getLayoutInflater(),
                null,false);
        setContentView(binding.getRoot());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}