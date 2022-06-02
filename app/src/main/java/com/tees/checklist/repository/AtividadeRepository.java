package com.tees.checklist.repository;

import com.tees.checklist.api.request.DefaultDataRequest;
import com.tees.checklist.base.BaseAPI;
import com.tees.checklist.base.BaseRepository;
import com.tees.checklist.commons.Constants;
import com.tees.checklist.commons.LogArquivo;
import com.tees.checklist.commons.Messages;
import com.tees.checklist.data.db.AppDatabase;
import com.tees.checklist.data.model.Atividade;
import com.tees.checklist.data.model.Configuracoes;
import com.tees.checklist.data.utils.SQLCondition;

import java.util.List;

import retrofit2.Call;

public  class AtividadeRepository extends BaseRepository<Atividade> {

    private AppDatabase dao;
    private BaseAPI api;
    public Configuracoes settings;
    public AtividadeRepository(AppDatabase dao, BaseAPI api,Configuracoes settings){
        this.dao  = dao;
        this.api = api;
        this.settings = settings;
    }


    @Override
    public Atividade getRefinedToInsertOrUpdate(Atividade item) {
        return item;
    }

    @Override
    public Atividade getRefined(Atividade item){
        if (item == null) return null;
        return item;
    }



    @SuppressWarnings("unchecked")
    public List<Atividade> getAllOnline(DefaultDataRequest data) throws Exception {
        Call<List<Atividade>> resources = api.loadAtividade(data);
        return (List<Atividade>)returnAPIData(resources);
    }

    public List<Atividade> getWithParameters(List<SQLCondition> sqlConditions){
        try {
            List<Atividade> result =  getRefinedList(dao.atividade().getWithParameters(sqlConditions));
            if(result==null){
                LogArquivo.GravaArquivoTextoDetalhado(Messages.NO_DATA_FOUND+getTableName());
            }
            return result;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return null;
        }
    }

    public List<Atividade> getAll(){
        try {
            List<Atividade> result =  getRefinedList(dao.atividade().getAll());
            if(result==null){
                LogArquivo.GravaArquivoTextoDetalhado(Messages.NO_DATA_FOUND+getTableName());
            }
            return result;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return null;
        }
    }


    public Atividade getOne(){
        try {
            Atividade result =  getRefined(dao.atividade().getOne());
            if(result==null){
                LogArquivo.GravaArquivoTextoDetalhado(Messages.NO_DATA_FOUND+getTableName());
            }
            return result;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return null;
        }
    }


    public Atividade getOneWithParameters(List<SQLCondition> sqlConditions){
        try {
            Atividade result =  getRefined(dao.atividade().getOneWithParameters(sqlConditions));
            if(result==null){
                LogArquivo.GravaArquivoTextoDetalhado(Messages.NO_DATA_FOUND+getTableName());
            }
            return result;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return null;
        }
    }

    public String insertOffline( List<Atividade> list){
        try {
            dao.atividade().insertAll(list);
            return Messages.SUCCESS_MESSAGE;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return Messages.error(Constants.INSERT,getTableName());
        }
    }

    public String deleteAllOffline(){
        try {
            dao.atividade().deleteAll();
            return Messages.SUCCESS_MESSAGE;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return Messages.error(Constants.DELETE, getTableName());
        }
    }

    public String load() {
        if(Constants.TEST_LOAD_UNLOAD) return Messages.SUCCESS_MESSAGE;
        List<Atividade> list;
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
