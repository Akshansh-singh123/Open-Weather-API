package com.akshansh.weatherapi.screens.common.toolbar;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.akshansh.weatherapi.databinding.MainToolbarBinding;
import com.akshansh.weatherapi.screens.common.views.BaseViewMvc;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ToolbarViewMvcImpl extends BaseViewMvc implements ToolbarViewMvc {
    private MainToolbarBinding binding;
    private final TextView titleTextView;
    private final TextView lastUpdateTimeTextView;
    private final SimpleDateFormat timeFormat;

    public ToolbarViewMvcImpl(@NonNull LayoutInflater inflater, ViewGroup parent) {
        binding = MainToolbarBinding.inflate(inflater,parent,false);
        setRootView(binding.getRoot());
        titleTextView = binding.titleTextView;
        lastUpdateTimeTextView = binding.lastUpdateTimeText;
        timeFormat = new SimpleDateFormat("dd-MM-yy hh:mm a",Locale.ENGLISH);
    }

    @Override
    public void setTitle(String title) {
        titleTextView.setText(title);
    }

    @Override
    public void setLastUpdatedTime(long lastUpdatedTimestamp) {
        String lastUpdateTime = timeFormat.format(lastUpdatedTimestamp*1000L);
        lastUpdateTimeTextView.setText(String.format("Last update on: %s",
                lastUpdateTime));
    }

    @Override
    public void clearBinding() {
        binding = null;
    }
}
