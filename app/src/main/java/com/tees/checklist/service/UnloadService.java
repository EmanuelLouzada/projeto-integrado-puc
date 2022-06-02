package com.tees.checklist.service;

import com.tees.checklist.base.BaseAPI;
import com.tees.checklist.commons.LogArquivo;
import com.tees.checklist.commons.Messages;
import com.tees.checklist.commons.Preferences;
import com.tees.checklist.data.db.AppDatabase;
import com.tees.checklist.repository.AnalisePessoalRepository;
import com.tees.checklist.repository.ConfiguracoesRepository;
import com.tees.checklist.repository.EPISConvencionaisRepository;
import com.tees.checklist.repository.EPISEspecificosRepository;
import com.tees.checklist.repository.InspecaoAPRRepository;
import com.tees.checklist.repository.InspecaoEPCRepository;
import com.tees.checklist.repository.InspecaoEPIRepository;
import com.tees.checklist.repository.RiscoSegurancaRepository;

public class UnloadService{

    public AppDatabase dao;
    public BaseAPI api;
    public Preferences preferences;

    public ConfiguracoesRepository configuracoesRepository;
    public AnalisePessoalRepository analisePessoalRepository;
    public EPISConvencionaisRepository eipsConvencionaisRepository;
    public EPISEspecificosRepository episEspecificosRepository;
    public InspecaoAPRRepository inspecaoAPRRepository;
    public InspecaoEPCRepository inspecaoEPCRepository;
    public InspecaoEPIRepository inspecaoEPIRepository;
    public RiscoSegurancaRepository riscoSegurancaRepository;

    public UnloadService(AppDatabase dao, BaseAPI api, Preferences preferences){
        this.dao = dao;
        this.api = api;
        this.preferences = preferences;

    }

    public String init(){
        try {
            if(api==null) return Messages.WEBSERVICE_ERROR_MESSAGE;
            if(preferences.getSettings() == null){
                return Messages.GENERIC_ERROR_MESSAGE;
            }
            configuracoesRepository = new ConfiguracoesRepository(dao);
            analisePessoalRepository = new AnalisePessoalRepository(dao,api,preferences.getSettings());
            eipsConvencionaisRepository = new EPISConvencionaisRepository(dao,api,preferences.getSettings());
            episEspecificosRepository = new EPISEspecificosRepository(dao,api,preferences.getSettings());
            inspecaoAPRRepository = new InspecaoAPRRepository(dao,api,preferences.getSettings());
            inspecaoEPCRepository = new InspecaoEPCRepository(dao,api,preferences.getSettings());
            inspecaoEPIRepository = new InspecaoEPIRepository(dao,api,preferences.getSettings());
            riscoSegurancaRepository = new RiscoSegurancaRepository(dao,api,preferences.getSettings());


        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return Messages.GENERIC_ERROR_MESSAGE;
        }
        return Messages.SUCCESS_MESSAGE;
    }


}
