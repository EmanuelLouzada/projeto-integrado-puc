package com.tees.checklist.repository;

import com.tees.checklist.api.request.DefaultDataRequest;
import com.tees.checklist.base.BaseAPI;
import com.tees.checklist.base.BaseRepository;
import com.tees.checklist.commons.Constants;
import com.tees.checklist.commons.LogArquivo;
import com.tees.checklist.commons.Messages;
import com.tees.checklist.data.db.AppDatabase;
import com.tees.checklist.data.model.Configuracoes;
import com.tees.checklist.data.model.EPISEspecificos;
import com.tees.checklist.data.utils.SQLCondition;

import java.util.List;

import retrofit2.Call;

public class EPISEspecificosRepository extends BaseRepository<EPISEspecificos> {

    private AppDatabase dao;
    private BaseAPI api;
    public Configuracoes settings;
    public EPISEspecificosRepository(AppDatabase dao, BaseAPI api,Configuracoes settings){
        this.dao  = dao;
        this.api = api;
        this.settings = settings;
    }


    @Override
    public EPISEspecificos getRefinedToInsertOrUpdate(EPISEspecificos item) {
        return item;
    }

    @Override
    public EPISEspecificos getRefined(EPISEspecificos item){
        if (item == null) return null;
        return item;
    }


    @SuppressWarnings("unchecked")
    public List<EPISEspecificos> getAllOnline(DefaultDataRequest data) throws Exception {
        Call<List<EPISEspecificos>> resources = api.loadEPISEspecificos(data);
        return (List<EPISEspecificos>)returnAPIData(resources);
    }

    public List<EPISEspecificos> getWithParameters(List<SQLCondition> sqlConditions){
        try {
            List<EPISEspecificos> result =  getRefinedList(dao.episEspecificos().getWithParameters(sqlConditions));
            if(result==null){
                LogArquivo.GravaArquivoTextoDetalhado(Messages.NO_DATA_FOUND+getTableName());
            }
            return result;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return null;
        }
    }

    public EPISEspecificos getOne(){
        try {
            EPISEspecificos result =  getRefined(dao.episEspecificos().getOne());
            if(result==null){
                LogArquivo.GravaArquivoTextoDetalhado(Messages.NO_DATA_FOUND+getTableName());
            }
            return result;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return null;
        }
    }


    public EPISEspecificos getOneWithParameters(List<SQLCondition> sqlConditions){
        try {
            EPISEspecificos result =  getRefined(dao.episEspecificos().getOneWithParameters(sqlConditions));
            if(result==null){
                LogArquivo.GravaArquivoTextoDetalhado(Messages.NO_DATA_FOUND+getTableName());
            }
            return result;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return null;
        }
    }

    public String insertOffline( List<EPISEspecificos> list){
        try {
            dao.episEspecificos().insertAll(list);
            return Messages.SUCCESS_MESSAGE;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return Messages.error(Constants.INSERT,getTableName());
        }
    }

    public String insertOneOffline(EPISEspecificos item){
        try {
            dao.episEspecificos().insert(item);
            return Messages.SUCCESS_MESSAGE;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return Messages.error(Constants.INSERT,getTableName());
        }
    }

    public String deleteAllOffline(){
        try {
            dao.episEspecificos().deleteAll();
            return Messages.SUCCESS_MESSAGE;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return Messages.error(Constants.DELETE, getTableName());
        }
    }

    public String load() {
        if(Constants.TEST_LOAD_UNLOAD) return Messages.SUCCESS_MESSAGE;
        List<EPISEspecificos> list;
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
