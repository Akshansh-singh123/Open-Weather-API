package com.akshansh.weatherapi.screens.common.screensnavigator;

import android.content.Intent;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.akshansh.weatherapi.common.Constants;
import com.akshansh.weatherapi.screens.common.dialogs.ProgressDialogFragment;
import com.akshansh.weatherapi.screens.selectcity.SelectCityActivity;
import com.akshansh.weatherapi.screens.main.main.MainActivity;

public class ScreensNavigator {
    private final AppCompatActivity activity;

    public ScreensNavigator(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void syncToMainActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                activity.startActivity(new Intent(activity, MainActivity.class));
                activity.finish();
            }
        },500);
    }

    public void toCitySelectActivity() {
        activity.startActivity(new Intent(activity, SelectCityActivity.class));
    }

    public void CitySelectToMain() {
        Intent intent = new Intent(activity, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    public DialogFragment showProgressDialog() {
        DialogFragment dialog = new ProgressDialogFragment();
        dialog.show(activity.getSupportFragmentManager(),"ProgressDialog");
        return dialog;
    }
}
