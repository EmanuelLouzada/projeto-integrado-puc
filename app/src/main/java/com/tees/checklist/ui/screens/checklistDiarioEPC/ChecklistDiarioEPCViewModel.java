package com.tees.checklist.ui.screens.checklistDiarioEPC;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tees.checklist.commons.Messages;
import com.tees.checklist.data.model.InspecaoEPC;
import com.tees.checklist.repository.InspecaoEPCRepository;
import com.tees.checklist.service.DataToAdapterService;

import java.util.List;

public class ChecklistDiarioEPCViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private MutableLiveData<List<Object>> resultData = new MutableLiveData<>();
    private MutableLiveData<String> resultError = new MutableLiveData<>();
    private MutableLiveData<String> responseSave;
    private final MutableLiveData<String> errorSave = new MutableLiveData<>();



    private DataToAdapterService mService;
    private InspecaoEPCRepository mRepository;


    public LiveData<String> getErrorSave() {
        return errorSave;
    }


    public ChecklistDiarioEPCViewModel(DataToAdapterService service){
        mService = service;
    }



    public LiveData<String> updateOffline(InspecaoEPC item) {
        if (responseSave == null) {
            responseSave = new MutableLiveData<>();
            updateOfflineSingle(item);
        }
        return responseSave;
    }



    @SuppressLint("CheckResult")
    private void updateOfflineSingle(InspecaoEPC item) {
        responseSave.postValue(Messages.SUCCESS_MESSAGE);
        /*
        mRepository.insertOfflineSingle(item)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Long>() {
                    @Override
                    public void onSuccess(Long value) {
                        if(value>0) {
                            responseSave.setValue(Messages.SUCCESS_MESSAGE);
                        }else{
                            errorSave.setValue(Messages.FAIL_INSERT_MESSAGE);
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        errorSave.setValue(Messages.FAIL_INSERT_MESSAGE);
                    }

                }); */
    }


}