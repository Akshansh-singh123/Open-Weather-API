package com.akshansh.weatherapi.screens.selectcity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.fragment.app.DialogFragment;

import com.akshansh.weatherapi.city.FetchCitiesQueryUseCase;
import com.akshansh.weatherapi.common.Constants;
import com.akshansh.weatherapi.common.ViewMvcFactory;
import com.akshansh.weatherapi.common.utils.WeatherDataSyncHelper;
import com.akshansh.weatherapi.networking.citymodels.City;
import com.akshansh.weatherapi.networking.weathermodels.CurrentWeatherData;
import com.akshansh.weatherapi.networking.weathermodels.WeatherForecastData;
import com.akshansh.weatherapi.screens.common.BaseActivity;
import com.akshansh.weatherapi.screens.common.dialogs.ProgressDialogFragment;
import com.akshansh.weatherapi.screens.common.screensnavigator.ScreensNavigator;
import com.akshansh.weatherapi.screens.common.toast.ToastHelper;
import com.akshansh.weatherapi.screens.main.main.MainActivity;
import com.akshansh.weatherapi.weather.FetchWeatherUseCase;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Inject;

public class SelectCityActivity extends BaseActivity implements
        SelectCityViewMvc.Listener, FetchCitiesQueryUseCase.Listener, FetchWeatherUseCase.Listener {
    @Inject public ViewMvcFactory viewMvcFactory;
    @Inject public ExecutorService executor;
    @Inject public FetchCitiesQueryUseCase fetchCitiesQueryUseCase;
    @Inject public ToastHelper toastHelper;
    @Inject public Handler uiThread;
    @Inject public FetchWeatherUseCase fetchWeatherUseCase;
    @Inject public ScreensNavigator screensNavigator;
    @Inject public WeatherDataSyncHelper weatherDataSyncHelper;

    private SelectCityViewMvc viewMvc;
    private boolean listening = true;
    private final AtomicLong lastTypeTime = new AtomicLong(0L);
    private String searchText = "";
    private DialogFragment dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getInjector().inject(this);
        viewMvc = viewMvcFactory.getSelectCityViewMvc(null);
        setContentView(viewMvc.getRootView());
        dispatchSearch();
    }

    @Override
    protected void onStart() {
        super.onStart();
        listening = true;
        viewMvc.registerListener(this);
        fetchCitiesQueryUseCase.registerListener(this);
        fetchWeatherUseCase.registerListener(this);
    }

    @Override
    protected void onDestroy() {
        listening = false;
        super.onDestroy();
        viewMvc.unregisterListener(this);
        fetchCitiesQueryUseCase.unregisterListener(this);
        fetchWeatherUseCase.unregisterListener(this);
    }

    @Override
    public void onTextTyped(String text) {
        if(!text.trim().equals(searchText)) {
            lastTypeTime.getAndSet(System.currentTimeMillis());
            searchText = text;
        }
    }

    @Override
    public void onCitySelected(City city) {
        fetchWeatherUseCase.fetchWeatherForecastByCityName(city.getCity(), Constants.METRIC,false);
        dialog = screensNavigator.showProgressDialog();
    }

    private void dispatchSearch() {
        executor.execute(()->{
            while (listening){
                if(System.currentTimeMillis() - lastTypeTime.get() >= 1000L){
                    if(!searchText.trim().equals("")
                            && lastTypeTime.get() != 0L
                            && searchText.trim().length() >= 3){
                        fetchCitiesQueryUseCase.fetchQueriedCities(searchText);
                        uiThread.post(()->viewMvc.showProgressBar());
                        lastTypeTime.getAndSet(0L);
                    }
                }
            }
        });
    }

    @Override
    public void OnCityFetchSuccessful(List<City> cities) {
        viewMvc.hideProgressBar();
        viewMvc.bindView(cities);
    }

    @Override
    public void OnQueriedCityNotFound() {
        viewMvc.hideProgressBar();
        viewMvc.cityNotFound();
    }

    @Override
    public void OnFetchFailure() {
        viewMvc.hideProgressBar();
        toastHelper.makeToast("Something went wrong");
    }

    @Override
    public void OnNetworkError() {
        viewMvc.hideProgressBar();
        toastHelper.makeToast("No internet connection");
    }

    @Override
    public void OnFetchWeatherSuccessful(CurrentWeatherData weatherData, WeatherForecastData weatherForecastData) {
        dialog.dismiss();
        if(weatherData.getCodeOfDelivery().equals("200"))
            screensNavigator.CitySelectToMain();
        else
            OnFetchWeatherFailure();
    }

    @Override
    public void OnFetchWeatherFailure() {
        dialog.dismiss();
        toastHelper.makeToast("Failed to fetch weather for this city");
        finish();
    }

    @Override
    public void OnFetchWeatherNetworkError() {
        dialog.dismiss();
        toastHelper.makeToast("Check your internet connection");
        finish();
    }
}