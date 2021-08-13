package com.akshansh.weatherapi.screens.common.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.akshansh.weatherapi.R;
import com.akshansh.weatherapi.screens.common.BaseDialog;

public class ProgressDialogFragment extends BaseDialog {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater()
                .inflate(R.layout.progress_dialog_layout,null,false);
        dialog.setView(view);
        dialog.setCancelable(false);
        return dialog.create();
    }
}
