package com.tees.checklist;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.tees.checklist.commons.LogArquivo;
import com.tees.checklist.commons.Preferences;
import com.tees.checklist.commons.SingletonContext;
import com.tees.checklist.commons.Utils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SingletonContext context = new SingletonContext();
        context.init(this.getApplicationContext());
        //Thread.setDefaultUncaughtExceptionHandler(new TopExceptionHandler(this.getApplicationContext(), getIntent()));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//Set Portrait
        DataBindingUtil.setContentView(this, R.layout.activity_main);
        Preferences preferences = new Preferences(this.getApplicationContext());
        LogArquivo.StatusLog(preferences.getSettings().modo_debug);
        Utils.enableHttpResponseCache(getCacheDir());


    }
}