package com.tees.checklist.service;

import com.tees.checklist.base.BaseAPI;
import com.tees.checklist.commons.LogArquivo;
import com.tees.checklist.commons.Messages;
import com.tees.checklist.commons.Preferences;
import com.tees.checklist.data.db.AppDatabase;
import com.tees.checklist.data.model.AnalisePessoal;
import com.tees.checklist.data.model.EPISConvencionais;
import com.tees.checklist.data.model.EPISEspecificos;
import com.tees.checklist.data.model.Funcionario;
import com.tees.checklist.data.model.InspecaoAPR;
import com.tees.checklist.data.model.RiscoSeguranca;
import com.tees.checklist.repository.AnalisePessoalRepository;
import com.tees.checklist.repository.AtividadeRepository;
import com.tees.checklist.repository.EPISConvencionaisRepository;
import com.tees.checklist.repository.EPISEspecificosRepository;
import com.tees.checklist.repository.FuncionarioRepository;
import com.tees.checklist.repository.InspecaoAPRRepository;
import com.tees.checklist.repository.RiscoSegurancaRepository;

import java.util.List;

public class AprService {
    public AppDatabase dao;
    public BaseAPI api;
    public Preferences preferences;

    public RiscoSegurancaRepository riscoSegurancaRepository;
    public InspecaoAPRRepository inspecaoAPRRepository;
    public AnalisePessoalRepository analisePessoalRepository;
    public FuncionarioRepository funcionarioRepository;
    public EPISConvencionaisRepository episConvencionaisRepository;
    public EPISEspecificosRepository episEspecificosRepository;
    public AtividadeRepository atividadeRepository;


    public AprService(AppDatabase dao, BaseAPI api, Preferences preferences) {
        this.dao = dao;
        this.api = api;
        this.preferences = preferences;
        init();

    }

    public String init() {
        try {
            if (preferences.getSettings() == null) {
                return Messages.GENERIC_ERROR_MESSAGE;
            }
            inspecaoAPRRepository = new InspecaoAPRRepository(dao, api, preferences.getSettings());
            riscoSegurancaRepository = new RiscoSegurancaRepository(dao, api, preferences.getSettings());
            analisePessoalRepository = new AnalisePessoalRepository(dao, api, preferences.getSettings());
            episConvencionaisRepository = new EPISConvencionaisRepository(dao, api, preferences.getSettings());
            episEspecificosRepository = new EPISEspecificosRepository(dao, api, preferences.getSettings());
            funcionarioRepository = new FuncionarioRepository(dao, api, preferences.getSettings());
            atividadeRepository = new AtividadeRepository(dao, api, preferences.getSettings());

        } catch (Exception e) {
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return Messages.GENERIC_ERROR_MESSAGE;
        }
        return Messages.SUCCESS_MESSAGE;
    }

    public String save(InspecaoAPR inspecaoAPR, RiscoSeguranca riscoSeguranca, AnalisePessoal analisePessoal,
                       EPISConvencionais episConvencionais, EPISEspecificos episEspecificos)  {
        try {
            //inspecaoAPRRepository.insertOneOffline(inspecaoAPR);
            //episConvencionaisRepository.insertOneOffline(episConvencionais);
            //episEspecificosRepository.insertOneOffline(episEspecificos);
            //riscoSegurancaRepository.insertOneOffline(riscoSeguranca);
            //analisePessoalRepository.insertOneOffline(analisePessoal);


        } catch (Exception e){
            return Messages.FAIL_INSERT_MESSAGE;
        }

        return Messages.SUCCESS_MESSAGE;

    }


    public List<Funcionario> getDataToAdapterAPR() throws Exception {
           List<Funcionario> funcionarios = funcionarioRepository.getAll();

            return funcionarios;

        }

    }
