package com.akshansh.weatherapi.screens.common.toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.akshansh.weatherapi.databinding.MainToolbarBinding;
import com.akshansh.weatherapi.screens.common.views.BaseObservableViewMvc;
import com.akshansh.weatherapi.screens.common.views.BaseViewMvc;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class ToolbarViewMvcImpl extends BaseObservableViewMvc<ToolbarViewMvc.Listener>
        implements ToolbarViewMvc {
    private MainToolbarBinding binding;
    private final TextView titleTextView;
    private final TextView lastUpdateTimeTextView;
    private final SimpleDateFormat timeFormat;
    private final TextView activateGPSButton;
    private final ProgressBar progressBar;

    public ToolbarViewMvcImpl(@NonNull LayoutInflater inflater, ViewGroup parent) {
        binding = MainToolbarBinding.inflate(inflater,parent,false);
        setRootView(binding.getRoot());
        titleTextView = binding.titleTextView;
        lastUpdateTimeTextView = binding.lastUpdateTimeText;
        activateGPSButton = binding.activateGpsButton;
        progressBar = binding.progressBar;
        timeFormat = new SimpleDateFormat("dd-MM-yy hh:mm a",Locale.ENGLISH);
        activateGPSButton.setOnClickListener(view->{
            for(Listener listener: getListeners()){
                listener.OnGPSActivateButtonClicked();
            }
        });
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
    public void setGPSButtonVisible(boolean visible) {
        if (visible) {
            activateGPSButton.setVisibility(View.VISIBLE);
        } else {
            activateGPSButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void setProgressBarVisible(boolean visible) {
        if(visible){
            progressBar.setVisibility(View.VISIBLE);
            lastUpdateTimeTextView.setVisibility(View.INVISIBLE);
        }else{
            progressBar.setVisibility(View.INVISIBLE);
            lastUpdateTimeTextView.setVisibility(View.VISIBLE);
        }
    }
}
