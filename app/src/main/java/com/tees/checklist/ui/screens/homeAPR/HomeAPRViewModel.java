package com.tees.checklist.ui.screens.homeAPR;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tees.checklist.commons.Messages;
import com.tees.checklist.data.model.AnalisePessoal;
import com.tees.checklist.data.model.EPISConvencionais;
import com.tees.checklist.data.model.EPISEspecificos;
import com.tees.checklist.data.model.Funcionario;
import com.tees.checklist.data.model.InspecaoAPR;
import com.tees.checklist.data.model.RiscoSeguranca;
import com.tees.checklist.service.AprService;

import java.util.List;

public class HomeAPRViewModel extends ViewModel {
    // TODO: Implement the ViewModel


    private MutableLiveData<String> responseSave;
    private final MutableLiveData<String> errorSave = new MutableLiveData<>();
    private MutableLiveData<List<Funcionario>> resultDataAdapter = new MutableLiveData<>();
    private MutableLiveData<String> resultErrorAdapter = new MutableLiveData<>();

    private AprService mService;

    public HomeAPRViewModel(AprService service) {
        mService = service;
    }

    public LiveData<String> getErrorSave() {
        return errorSave;
    }


    public LiveData<List<Funcionario>> getResultAdapter() {
        getDataAdapter();
        return resultDataAdapter;
    }
    public LiveData<String> getErrorAdapter() {
        return resultErrorAdapter;
    }


    public void getDataAdapter() {
        class GetData extends AsyncTask<Void, Void, List<Funcionario> > {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                resultDataAdapter.setValue(null);
                resultErrorAdapter.setValue(null);
            }
            @Override
            protected List<Funcionario> doInBackground(Void... voids) {
                try {
                    List<Funcionario> funcionarios =  mService.getDataToAdapterAPR();
                    return funcionarios;
                }catch (Exception e){
                    return null;
                }

            }
            @Override
            protected void onPostExecute(List<Funcionario> result) {
                super.onPostExecute(result);
                try {
                    List<Funcionario> funcionarios =  result;
                    if(funcionarios!=null) {
                        if (funcionarios.size() == 0)
                            resultErrorAdapter.setValue(Messages.NO_DATA_FOUND);
                        resultDataAdapter.setValue(funcionarios);
                    }else{
                        resultErrorAdapter.setValue(Messages.NO_DATA_FOUND);
                    }
                }catch (Exception e){
                    resultErrorAdapter.setValue(Messages.GENERIC_ERROR_MESSAGE);
                }

            }
        }

        try {
            GetData result = new GetData();
            result.execute();
        } catch (Exception e) {
            resultErrorAdapter.setValue(Messages.GENERIC_ERROR_MESSAGE);
        }
    }

    public LiveData<String> save(InspecaoAPR inspecaoAPR, EPISEspecificos episEspecificos, EPISConvencionais episConvencionais, RiscoSeguranca riscoSeguranca, AnalisePessoal analisePessoal) {
        if (responseSave == null) {
            responseSave = new MutableLiveData<>();
        }
        insert(inspecaoAPR, episEspecificos, episConvencionais, riscoSeguranca, analisePessoal);
        return responseSave;

    }

    public void insert(InspecaoAPR inspecaoAPR, EPISEspecificos episEspecificos, EPISConvencionais episConvencionais, RiscoSeguranca riscoSeguranca, AnalisePessoal analisePessoal) {
        class Insert extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                responseSave.setValue(null);
                errorSave.setValue(null);
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {

                    String message =mService.save(inspecaoAPR, riscoSeguranca, analisePessoal, episConvencionais, episEspecificos);
                    if(message.equals(Messages.SUCCESS_MESSAGE)){
                        return Messages.SUCCESS_MESSAGE;
                    }else{
                        return  Messages.FAIL_INSERT_MESSAGE;
                    }
                } catch (Exception e) {
                    return Messages.GENERIC_ERROR_MESSAGE;
                }
            }
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if (!result.equals(Messages.SUCCESS_MESSAGE)) {
                    errorSave.setValue(result);
                }else{
                    responseSave.setValue(Messages.SUCCESS_MESSAGE);
                }
            }
        }
        try {
            Insert result = new Insert();
            result.execute();
        } catch (Exception e) {
            errorSave.setValue(Messages.GENERIC_ERROR_MESSAGE);

        }
    }

    }

