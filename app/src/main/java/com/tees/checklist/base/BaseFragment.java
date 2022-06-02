package com.tees.checklist.base;

import android.content.Intent;
import android.content.res.Configuration;

import androidx.fragment.app.Fragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.tees.checklist.R;
import com.tees.checklist.commons.Utils;

import java.util.Objects;

public class BaseFragment extends Fragment {

    public void config(){
        try {
            Configuration config = getContext().getResources().getConfiguration();
            if(!Utils.checkDeviceLocale(config,getContext())){
                Utils.setLocale(config,getContext());
                showAlertDialog(R.string.change_language_message);
            }
        }catch (Exception e){
            showAlertDialog(R.string.change_language_message);
        }
    }

    public void showAlertDialog(int message){
        new MaterialAlertDialogBuilder(Objects.requireNonNull(getContext()))
                .setTitle(R.string.error_message)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, (dialog, id) -> {
                    startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCALE_SETTINGS), 0);
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}