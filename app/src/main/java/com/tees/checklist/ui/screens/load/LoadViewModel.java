package com.tees.checklist.ui.screens.load;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tees.checklist.api.WebAPI;
import com.tees.checklist.commons.DateHelper;
import com.tees.checklist.commons.Messages;
import com.tees.checklist.commons.TableNames;
import com.tees.checklist.data.db.AppDatabase;
import com.tees.checklist.service.LoadService;

import java.util.Date;


public class LoadViewModel extends ViewModel {

    private static MutableLiveData<Integer> percent;
    private static MutableLiveData<String> loadResponse;
    private static MutableLiveData<String> tableError = new MutableLiveData<>();
    private static MutableLiveData<String> loadError = new MutableLiveData<>();

    public static int tableCount = 54;
    public static float currentLoad = 100f /tableCount;
    public static Thread thread;


    private static LoadService mService;
    public LoadViewModel(LoadService service) {
        mService = service;
    }


    public LiveData<String> initLoad(String method) {
        loadResponse = new MutableLiveData<>();
        percent = new MutableLiveData<>();
        load(method);
        return loadResponse;
    }


    public LiveData<String> getLoadError() {
        return loadError;
    }

    public LiveData<String> getTableError() {
        return tableError;
    }

    public LiveData<Integer> getPercent() {
        return percent;
    }



    private static boolean runLoadProcess(String method, int index){
        String message = runMethod(method);
        if (message.equals(Messages.SUCCESS_MESSAGE)) {
            //Log.d("PROCESSO","Método "+ method+" true");
            loadResponse.postValue(method + "-" +message);
            int percentValue = (int) Math.ceil(currentLoad * index);
            percent.postValue(percentValue);
            return true;
        } else {
            //Log.d("PROCESSO","Método "+ method+" false");
            loadError.postValue(method + "-" +message);
            return false;
        }

    }

    public void cancelRequests(){
        WebAPI.cancelRequests();
    }

    private static String runMethod(String method){
        switch (method) {

            case TableNames.CONFIGURACOES : return mService.configuracoesRepository.updateOne("S", DateHelper.getUTCTicks(new Date()));

            default: return null;
        }
    }

    public void clearDatabase() {

        class CearDatabase extends AsyncTask<Void, Void, Void> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                mService.init();
                return null;
            }

            @Override
            protected void onPostExecute(Void voids) {
                super.onPostExecute(voids);
            }


        }
        try {
            CearDatabase result = new CearDatabase();
            result.execute();
        } catch (Exception e) {
        }

    }

    private void loadRoutine(String nextMethod){
        AppDatabase.getInstance().runInTransaction(new Runnable() {
            @Override
            public void run() {
                loadError.postValue(null);
                int countProcess = 0;
                boolean success = false;

                int index = 0;

                if(nextMethod.equals(TableNames.CONFIGURACOES) || success) {
                    success = runLoadProcess(TableNames.CONFIGURACOES, 55);
                    if (!success) return;
                }
                loadResponse.postValue(Messages.SUCCESS_LOAD);
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

    private void load(String nextMethod){
        loadError.setValue(null);
        stopThread();
        thread = new Thread(() -> {
            loadRoutine(nextMethod);
        });
        thread.start();
    }





}
