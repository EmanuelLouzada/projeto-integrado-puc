package com.tees.checklist.service;

import com.tees.checklist.base.BaseAPI;
import com.tees.checklist.commons.Constants;
import com.tees.checklist.commons.LogArquivo;
import com.tees.checklist.commons.Messages;
import com.tees.checklist.commons.Preferences;
import com.tees.checklist.data.db.AppDatabase;
import com.tees.checklist.repository.AnalisePessoalRepository;
import com.tees.checklist.repository.AtividadeRepository;
import com.tees.checklist.repository.ConfiguracoesRepository;
import com.tees.checklist.repository.DiretoriaAtividadeRepository;
import com.tees.checklist.repository.DiretoriaRepository;
import com.tees.checklist.repository.EPISConvencionaisRepository;
import com.tees.checklist.repository.EPISEspecificosRepository;
import com.tees.checklist.repository.FuncionarioRepository;
import com.tees.checklist.repository.InspecaoAPRRepository;
import com.tees.checklist.repository.InspecaoEPCRepository;
import com.tees.checklist.repository.InspecaoEPIRepository;
import com.tees.checklist.repository.LocalidadeRepository;
import com.tees.checklist.repository.RiscoSegurancaRepository;
import com.tees.checklist.repository.UsuarioRepository;
import com.tees.checklist.repository.VeiculoRepository;

public class LoadService {
    public AppDatabase dao;
    public BaseAPI api;
    public Preferences preferences;
    public ConfiguracoesRepository configuracoesRepository;
    public AtividadeRepository atividadeRepository;
    public DiretoriaAtividadeRepository diretoriaAtividadeRepository;
    public DiretoriaRepository diretoriaRepository;
    public FuncionarioRepository funcionarioRepository;
    public LocalidadeRepository localidadeRepository;
    public UsuarioRepository usuarioRepository;
    public VeiculoRepository veiculoRepository;

    public AnalisePessoalRepository analisePessoalRepository;
    public EPISConvencionaisRepository eipsConvencionaisRepository;
    public EPISEspecificosRepository episEspecificosRepository;
    public InspecaoAPRRepository inspecaoAPRRepository;
    public InspecaoEPCRepository inspecaoEPCRepository;
    public InspecaoEPIRepository inspecaoEPIRepository;
    public RiscoSegurancaRepository riscoSegurancaRepository;






    public LoadService(AppDatabase dao, BaseAPI api, Preferences preferences){
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
            atividadeRepository = new AtividadeRepository(dao,api,preferences.getSettings());
            diretoriaAtividadeRepository = new DiretoriaAtividadeRepository(dao,api,preferences.getSettings());
            diretoriaRepository = new DiretoriaRepository(dao,api,preferences.getSettings());
            funcionarioRepository = new FuncionarioRepository(dao,api,preferences.getSettings());
            localidadeRepository = new LocalidadeRepository(dao,api,preferences.getSettings());
            usuarioRepository = new UsuarioRepository(dao,api,preferences.getSettings());
            veiculoRepository = new VeiculoRepository(dao,api,preferences.getSettings());

            analisePessoalRepository = new AnalisePessoalRepository(dao,api,preferences.getSettings());
            eipsConvencionaisRepository = new EPISConvencionaisRepository(dao,api,preferences.getSettings());
            episEspecificosRepository = new EPISEspecificosRepository(dao,api,preferences.getSettings());
            inspecaoAPRRepository = new InspecaoAPRRepository(dao,api,preferences.getSettings());
            inspecaoEPCRepository = new InspecaoEPCRepository(dao,api,preferences.getSettings());
            inspecaoEPIRepository = new InspecaoEPIRepository(dao,api,preferences.getSettings());
            riscoSegurancaRepository = new RiscoSegurancaRepository(dao,api,preferences.getSettings());

            if(!Constants.TEST_LOAD_UNLOAD)
                clearDatabase();


        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return Messages.GENERIC_ERROR_MESSAGE;
        }
        return Messages.SUCCESS_MESSAGE;
    }


    public void clearDatabase() {
        atividadeRepository.deleteAllOffline();
        diretoriaAtividadeRepository.deleteAllOffline();
        diretoriaRepository.deleteAllOffline();
        funcionarioRepository.deleteAllOffline();
        localidadeRepository.deleteAllOffline();
        usuarioRepository.deleteAllOffline();
        veiculoRepository.deleteAllOffline();

        analisePessoalRepository.deleteAllOffline();
        eipsConvencionaisRepository.deleteAllOffline();
        episEspecificosRepository.deleteAllOffline();
        inspecaoAPRRepository.deleteAllOffline();
        inspecaoEPCRepository.deleteAllOffline();
        inspecaoEPIRepository.deleteAllOffline();
        riscoSegurancaRepository.deleteAllOffline();


    }
}
