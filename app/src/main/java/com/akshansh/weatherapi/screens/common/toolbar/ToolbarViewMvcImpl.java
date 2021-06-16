package com.akshansh.weatherapi.screens.common.toolbar;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.akshansh.weatherapi.databinding.MainToolbarBinding;
import com.akshansh.weatherapi.screens.common.views.BaseViewMvc;

public class ToolbarViewMvcImpl extends BaseViewMvc implements ToolbarViewMvc {
    private MainToolbarBinding binding;
    private final TextView titleTextView;

    public ToolbarViewMvcImpl(@NonNull LayoutInflater inflater, ViewGroup parent) {
        binding = MainToolbarBinding.inflate(inflater,parent,false);
        setRootView(binding.getRoot());
        titleTextView = binding.titleTextView;
    }

    @Override
    public void setTitle(String title) {
        titleTextView.setText(title);
    }

    @Override
    public void clearBinding() {
        binding = null;
    }
}
