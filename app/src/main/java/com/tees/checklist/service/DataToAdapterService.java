package com.tees.checklist.service;

import com.tees.checklist.base.BaseAPI;
import com.tees.checklist.commons.LogArquivo;
import com.tees.checklist.commons.Messages;
import com.tees.checklist.commons.Preferences;
import com.tees.checklist.data.db.AppDatabase;
import com.tees.checklist.data.model.Atividade;
import com.tees.checklist.data.model.Funcionario;
import com.tees.checklist.data.model.Localidade;
import com.tees.checklist.data.model.Veiculo;
import com.tees.checklist.repository.AtividadeRepository;
import com.tees.checklist.repository.DiretoriaAtividadeRepository;
import com.tees.checklist.repository.DiretoriaRepository;
import com.tees.checklist.repository.FuncionarioRepository;
import com.tees.checklist.repository.LocalidadeRepository;
import com.tees.checklist.repository.UsuarioRepository;
import com.tees.checklist.repository.VeiculoRepository;

import java.util.ArrayList;
import java.util.List;

public class DataToAdapterService {
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

    public DataToAdapterService(AppDatabase dao, BaseAPI api, Preferences preferences){
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


    public List<Object> getDataToAdapterCabecalho() throws Exception {
            List<Funcionario> funcionarios = funcionarioRepository.getAll();
            List<Atividade> atividades = atividadeRepository.getAll();
            List<Localidade> localidades = localidadeRepository.getAll();
            List<Object> list = new ArrayList<>();
            list.add(funcionarios);
            list.add(atividades);
            list.add(localidades);
            return list;
    }

    public List<Object> getDataToAdapterInspecaoDiariaVeiculo() throws Exception {
        List<Funcionario> funcionarios = funcionarioRepository.getAll();
        List<Veiculo> veiculos = veiculoRepository.getAll();

        List<Object> list = new ArrayList<>();
        list.add(funcionarios);
        list.add(veiculos);

        return list;
    }


}
