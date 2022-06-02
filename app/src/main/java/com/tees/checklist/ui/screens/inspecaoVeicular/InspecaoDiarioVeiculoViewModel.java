package com.tees.checklist.ui.screens.inspecaoVeicular;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tees.checklist.commons.Messages;
import com.tees.checklist.data.model.Funcionario;
import com.tees.checklist.data.model.InspecaoVeicular;
import com.tees.checklist.data.model.Veiculo;
import com.tees.checklist.repository.InspecaoVeicularRepository;
import com.tees.checklist.service.DataToAdapterService;

import java.util.List;

public class InspecaoDiarioVeiculoViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private MutableLiveData<List<Object>> resultData = new MutableLiveData<>();
    private MutableLiveData<String> resultError = new MutableLiveData<>();
    private MutableLiveData<String> responseSave;
    private final MutableLiveData<String> errorSave = new MutableLiveData<>();



    private DataToAdapterService mService;
    private InspecaoVeicularRepository mRepository;

    public LiveData<List<Object>> getResult() {
        getData();
        return resultData;
    }
    public LiveData<String> getError() {
        return resultError;
    }

    public LiveData<String> getErrorSave() {
        return errorSave;
    }


    public InspecaoDiarioVeiculoViewModel(DataToAdapterService service){
        mService = service;
    }

    public void getData() {
        class GetData extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                resultData.setValue(null);
                resultError.setValue(null);
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    List<Object>  list =   mService.getDataToAdapterInspecaoDiariaVeiculo();
                    List<Funcionario> funcionarios =   (List<Funcionario>)list.get(0);
                    List<Veiculo> veiculos=   (List<Veiculo>)list.get(1);
                    if(funcionarios.size() ==0 || veiculos.size()==0)
                        return Messages.NO_DATA_FOUND;
                    resultData.postValue(list);
                    return Messages.SUCCESS_MESSAGE;
                }catch (Exception e){
                    return Messages.GENERIC_ERROR_MESSAGE;
                }

            }
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if(!result.equals(Messages.SUCCESS_MESSAGE)){
                    resultError.setValue(result);
                }
            }
        }

        try {
            GetData result = new GetData();
            result.execute();
        } catch (Exception e) {
            resultError.setValue(Messages.GENERIC_ERROR_MESSAGE);

        }
    }

    public LiveData<String> updateOffline(InspecaoVeicular item) {
        if (responseSave == null) {
            responseSave = new MutableLiveData<>();
            updateOfflineSingle(item);
        }
        return responseSave;
    }



    @SuppressLint("CheckResult")
    private void updateOfflineSingle(InspecaoVeicular item) {
        responseSave.postValue(Messages.SUCCESS_MESSAGE);
/*        mRepository.insertOfflineSingle(item)
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