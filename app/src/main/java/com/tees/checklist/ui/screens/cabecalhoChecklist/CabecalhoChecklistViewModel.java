package com.tees.checklist.ui.screens.cabecalhoChecklist;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tees.checklist.commons.Messages;
import com.tees.checklist.data.model.Atividade;
import com.tees.checklist.data.model.Funcionario;
import com.tees.checklist.data.model.Localidade;
import com.tees.checklist.service.DataToAdapterService;

import java.util.List;

public class CabecalhoChecklistViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private MutableLiveData<List<Object>> resultData = new MutableLiveData<>();
    private MutableLiveData<String> resultError = new MutableLiveData<>();
    private DataToAdapterService mService;

    public LiveData<List<Object>> getResult() {
        getData();
        return resultData;
    }
    public LiveData<String> getError() {
        return resultError;
    }


    public CabecalhoChecklistViewModel(DataToAdapterService service){
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
                 List<Object>  list =   mService.getDataToAdapterCabecalho();
                 List<Funcionario> funcionarios =   (List<Funcionario>)list.get(0);
                 List<Atividade> atividades =   (List<Atividade>)list.get(1);
                 List<Localidade> localidades =   (List<Localidade>)list.get(2);
                    if(funcionarios.size() ==0 || atividades.size()==0 || localidades.size()==0)
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


}