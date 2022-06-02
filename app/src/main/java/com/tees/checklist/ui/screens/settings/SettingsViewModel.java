package com.tees.checklist.ui.screens.settings;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tees.checklist.commons.Messages;
import com.tees.checklist.commons.Preferences;
import com.tees.checklist.data.model.Configuracoes;
import com.tees.checklist.repository.ConfiguracoesRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;


public class SettingsViewModel extends ViewModel {

    private final ConfiguracoesRepository mRepository;
    private final Preferences mPreferences;

    public SettingsViewModel(ConfiguracoesRepository repository, Preferences preferences) {
        mRepository = repository;
        mPreferences = preferences;
    }


    private MutableLiveData<String> response;
    private final MutableLiveData<String> error = new MutableLiveData<>();

    public LiveData<String> updateOffline(Configuracoes settings) {
        if (response == null) {
            response = new MutableLiveData<>();
            updateOfflineSingle(settings);
        }
        return response;
    }

    public LiveData<String> getError() {
        return error;
    }


    @SuppressLint("CheckResult")
    private void updateOfflineSingle(Configuracoes settings) {
        mRepository.updateOfflineSingle(settings)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Integer>() {
                    @Override
                    public void onSuccess(Integer value) {
                        if(value>0) {
                            mPreferences.setSettings(settings);
                            response.setValue(Messages.SUCCESS_MESSAGE);
                        }else{
                            error.setValue(Messages.FAIL_UPDATE_MESSAGE);
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        error.setValue(Messages.FAIL_UPDATE_MESSAGE);
                    }

                });
    }

}
