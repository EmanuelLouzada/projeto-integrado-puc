package com.tees.checklist.service;

import com.tees.checklist.base.BaseAPI;
import com.tees.checklist.commons.LogArquivo;
import com.tees.checklist.commons.Messages;
import com.tees.checklist.commons.Preferences;
import com.tees.checklist.data.db.AppDatabase;
import com.tees.checklist.repository.AtividadeRepository;
import com.tees.checklist.repository.DiretoriaAtividadeRepository;
import com.tees.checklist.repository.DiretoriaRepository;
import com.tees.checklist.repository.FuncionarioRepository;
import com.tees.checklist.repository.LocalidadeRepository;
import com.tees.checklist.repository.UsuarioRepository;
import com.tees.checklist.repository.VeiculoRepository;

public class InitService {
    public AppDatabase dao;
    public BaseAPI api;
    public Preferences preferences;

    public AtividadeRepository atividadeRepository;
    public DiretoriaAtividadeRepository diretoriaAtividadeRepository;
    public DiretoriaRepository diretoriaRepository;
    public FuncionarioRepository funcionarioRepository;
    public LocalidadeRepository localidadeRepository;
    public UsuarioRepository usuarioRepository;
    public VeiculoRepository veiculoRepository;





    public InitService(AppDatabase dao, BaseAPI api, Preferences preferences){
        this.dao = dao;
        this.api = api;
        this.preferences = preferences;
        init();

    }

    public String init(){
        try {
            if(preferences.getSettings() == null){
                return Messages.GENERIC_ERROR_MESSAGE;
            }
            atividadeRepository = new AtividadeRepository(dao,api,preferences.getSettings());
            diretoriaAtividadeRepository = new DiretoriaAtividadeRepository(dao,api,preferences.getSettings());
            diretoriaRepository = new DiretoriaRepository(dao,api,preferences.getSettings());
            funcionarioRepository = new FuncionarioRepository(dao,api,preferences.getSettings());
            localidadeRepository = new LocalidadeRepository(dao,api,preferences.getSettings());
            usuarioRepository = new UsuarioRepository(dao,api,preferences.getSettings());
            veiculoRepository = new VeiculoRepository(dao,api,preferences.getSettings());

        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return Messages.GENERIC_ERROR_MESSAGE;
        }
        return Messages.SUCCESS_MESSAGE;
    }
}
