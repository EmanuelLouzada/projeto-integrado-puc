package com.tees.checklist.repository;

import com.tees.checklist.api.request.DefaultDataRequest;
import com.tees.checklist.base.BaseAPI;
import com.tees.checklist.base.BaseRepository;
import com.tees.checklist.commons.Constants;
import com.tees.checklist.commons.LogArquivo;
import com.tees.checklist.commons.Messages;
import com.tees.checklist.data.db.AppDatabase;
import com.tees.checklist.data.model.Configuracoes;
import com.tees.checklist.data.model.Funcionario;
import com.tees.checklist.data.utils.SQLCondition;

import java.util.List;

import retrofit2.Call;

public class FuncionarioRepository extends BaseRepository<Funcionario> {

    private AppDatabase dao;
    private BaseAPI api;
    public Configuracoes settings;
    public FuncionarioRepository(AppDatabase dao, BaseAPI api,Configuracoes settings){
        this.dao  = dao;
        this.api = api;
        this.settings = settings;
    }


    @Override
    public Funcionario getRefinedToInsertOrUpdate(Funcionario item) {
        return item;
    }

    @Override
    public Funcionario getRefined(Funcionario item){
        if (item == null) return null;
        return item;
    }


    @SuppressWarnings("unchecked")
    public List<Funcionario> getAllOnline(DefaultDataRequest data) throws Exception {
        Call<List<Funcionario>> resources = api.loadFuncionario(data);
        return (List<Funcionario>)returnAPIData(resources);
    }


    public List<Funcionario> getAll(){
        try {
            List<Funcionario> result =  getRefinedList(dao.funcionario().getAll());
            if(result==null){
                LogArquivo.GravaArquivoTextoDetalhado(Messages.NO_DATA_FOUND+getTableName());
            }
            return result;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return null;
        }
    }

    public List<Funcionario> getWithParameters(List<SQLCondition> sqlConditions){
        try {
            List<Funcionario> result =  getRefinedList(dao.funcionario().getWithParameters(sqlConditions));
            if(result==null){
                LogArquivo.GravaArquivoTextoDetalhado(Messages.NO_DATA_FOUND+getTableName());
            }
            return result;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return null;
        }
    }

    public Funcionario getOne(){
        try {
            Funcionario result =  getRefined(dao.funcionario().getOne());
            if(result==null){
                LogArquivo.GravaArquivoTextoDetalhado(Messages.NO_DATA_FOUND+getTableName());
            }
            return result;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return null;
        }
    }


    public Funcionario getOneWithParameters(List<SQLCondition> sqlConditions){
        try {
            Funcionario result =  getRefined(dao.funcionario().getOneWithParameters(sqlConditions));
            if(result==null){
                LogArquivo.GravaArquivoTextoDetalhado(Messages.NO_DATA_FOUND+getTableName());
            }
            return result;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return null;
        }
    }



    public String insertOffline( List<Funcionario> list){
        try {
            dao.funcionario().insertAll(list);
            return Messages.SUCCESS_MESSAGE;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return Messages.error(Constants.INSERT,getTableName());
        }
    }

    public String deleteAllOffline(){
        try {
            dao.funcionario().deleteAll();
            return Messages.SUCCESS_MESSAGE;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return Messages.error(Constants.DELETE, getTableName());
        }
    }

    public String load() {
        if(Constants.TEST_LOAD_UNLOAD) return Messages.SUCCESS_MESSAGE;
        List<Funcionario> list;
        try {
            DefaultDataRequest data = new DefaultDataRequest();
            list =  getAllOnline(data);
            if (list == null || list.size() ==0 )
                return  Constants.TEST ? Messages.SUCCESS_MESSAGE:Messages.noOnlineDataFound(getTableName());
        } catch (Exception e) {
            return  Messages.WEBSERVICE_ERROR_MESSAGE;
        }
        return insertOffline(list);

    }

}
