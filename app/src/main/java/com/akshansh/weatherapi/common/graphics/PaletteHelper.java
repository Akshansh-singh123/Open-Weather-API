package com.akshansh.weatherapi.common.graphics;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.palette.graphics.Palette;

import com.akshansh.weatherapi.common.BaseObservable;

public class PaletteHelper extends BaseObservable<PaletteHelper.Listener> {
    public interface Listener{
        void OnSwatchGenerated(int swatchColor);
        void OnSwatchGenerationFailed();
    }

    private final Resources resources;
    private static final String TAG = "PaletteHelper";

    public PaletteHelper(Resources resources) {
        this.resources = resources;
    }

    public void getPaletteColor(int resId){
        Bitmap bitmap = BitmapFactory.decodeResource(resources,resId);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(@Nullable Palette palette) {
                Palette.Swatch lightMutedSwatch;
                Palette.Swatch mutedSwatch;
                Palette.Swatch darkMutedSwatch;
                if (palette != null) {
                    lightMutedSwatch = palette.getLightMutedSwatch();
                    mutedSwatch = palette.getMutedSwatch();
                    darkMutedSwatch = palette.getDarkMutedSwatch();
                    if(darkMutedSwatch != null){
                        notifyColor(darkMutedSwatch.getRgb());
                    }else if(mutedSwatch != null){
                        notifyColor(mutedSwatch.getRgb());
                    }else if(lightMutedSwatch != null){
                        notifyColor(lightMutedSwatch.getRgb());
                    }else {
                        for(Listener listener: getListeners()){
                            listener.OnSwatchGenerationFailed();
                        }
                    }
                }else{
                    Log.e(TAG, "onGenerated: palette null");
                    for(Listener listener: getListeners()){
                        listener.OnSwatchGenerationFailed();
                    }
                }
            }
        });
    }

    private void notifyColor(int color){
        for(Listener listener: getListeners()){
            listener.OnSwatchGenerated(color);
        }
    }
}
