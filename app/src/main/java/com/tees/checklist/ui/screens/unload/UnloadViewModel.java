package com.tees.checklist.ui.screens.unload;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tees.checklist.api.WebAPI;
import com.tees.checklist.commons.DateHelper;
import com.tees.checklist.commons.Messages;
import com.tees.checklist.commons.TableNames;
import com.tees.checklist.data.db.AppDatabase;
import com.tees.checklist.service.UnloadService;

import java.util.Date;


public class UnloadViewModel extends ViewModel {

    private MutableLiveData<Integer> percent = new MutableLiveData<>();
    private MutableLiveData<String> unLoadResponse = new MutableLiveData<>();
    private MutableLiveData<String> unloadError = new MutableLiveData<>();

    private int tableCount = 12;
    private int currentLoad = 100 /tableCount;
    public static Thread thread;

    private final UnloadService mService;
    private Context mContext;
    UnloadViewModel(UnloadService service, Context context) {
        mService = service;
        mContext = context;
    }


    LiveData<String> initUnload(String method) {
        unLoad(method,"BASE");
        return unLoadResponse;
    }


   LiveData<String> getLoadError() {
       return unloadError;
   }


    LiveData<Integer> getPercent() {
        return percent;
    }

    public LiveData<String> getUnLoadResponse() {
        return unLoadResponse;
    }

    private boolean runLoadProcess(String method, int index, boolean parcial){
        String message = runMethod(method,parcial);
        assert message != null;
        if (message.equals(Messages.SUCCESS_MESSAGE)) {
            unLoadResponse.postValue(method + "-" +message);
            int percentValue = (int) Math.ceil(currentLoad * index);
            percent.postValue(percentValue);
            return true;
        } else {
            unloadError.postValue(method + "-" +message);
            return false;
        }

    }

    public void cancelRequests(){
        WebAPI.cancelRequests();
    }

    private String runMethod(String method, Boolean parcial){
            switch (method) {
                case TableNames.CONFIGURACOES : return mService.configuracoesRepository.updateOne("N", DateHelper.getUTCTicks(new Date()));

                default: return null;
            }
    }


    private void loadRoutine(String nextMethod, String meio){
        AppDatabase.getInstance().runInTransaction(new Runnable() {
            @Override
            public void run() {
                boolean success = false;


                    if(nextMethod.equals(TableNames.CONFIGURACOES) || success) {
                        success = runLoadProcess(TableNames.CONFIGURACOES, 6,true);
                        if (!success) return;
                        unLoadResponse.postValue("Descarga total concluída com sucesso!");
                        percent.postValue(currentLoad * 12);
                    }
                unLoadResponse.postValue(Messages.SUCCESS_UNLOAD);


            cancelRequests();
            Log.d("Integridade2","Integridade2"+ AppDatabase.isDatabaseIntegrityOk());
        }


        });
    }


    public void stopThread(){
        if(thread!=null) {
            if (thread.isAlive()) {
                thread.interrupt();
            }
            thread = null;
        }

    }

    private void unLoad(String nextMethod,String meio){
        unloadError.setValue(null);
        stopThread();
        thread = new Thread(() -> {
            loadRoutine(nextMethod,meio);
        });
        thread.start();
    }






}
