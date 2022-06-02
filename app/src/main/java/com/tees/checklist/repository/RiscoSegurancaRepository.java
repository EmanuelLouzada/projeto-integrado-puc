package com.tees.checklist.repository;

import com.tees.checklist.api.request.DefaultDataRequest;
import com.tees.checklist.base.BaseAPI;
import com.tees.checklist.base.BaseRepository;
import com.tees.checklist.commons.Constants;
import com.tees.checklist.commons.LogArquivo;
import com.tees.checklist.commons.Messages;
import com.tees.checklist.data.db.AppDatabase;
import com.tees.checklist.data.model.Configuracoes;
import com.tees.checklist.data.model.RiscoSeguranca;
import com.tees.checklist.data.utils.SQLCondition;

import java.util.List;

import retrofit2.Call;

public  class RiscoSegurancaRepository extends BaseRepository<RiscoSeguranca> {

    private AppDatabase dao;
    private BaseAPI api;
    public Configuracoes settings;
    public RiscoSegurancaRepository(AppDatabase dao, BaseAPI api,Configuracoes settings){
        this.dao  = dao;
        this.api = api;
        this.settings = settings;
    }


    @Override
    public RiscoSeguranca getRefinedToInsertOrUpdate(RiscoSeguranca item) {
        return item;
    }

    @Override
    public RiscoSeguranca getRefined(RiscoSeguranca item){
        if (item == null) return null;
        return item;
    }


    @SuppressWarnings("unchecked")
    public List<RiscoSeguranca> getAllOnline(DefaultDataRequest data) throws Exception {
        Call<List<RiscoSeguranca>> resources = api.loadRiscoSeguranca(data);
        return (List<RiscoSeguranca>)returnAPIData(resources);
    }

    public List<RiscoSeguranca> getWithParameters(List<SQLCondition> sqlConditions){
        try {
            List<RiscoSeguranca> result =  getRefinedList(dao.riscoSeguranca().getWithParameters(sqlConditions));
            if(result==null){
                LogArquivo.GravaArquivoTextoDetalhado(Messages.NO_DATA_FOUND+getTableName());
            }
            return result;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return null;
        }
    }

    public RiscoSeguranca getOne(){
        try {
            RiscoSeguranca result =  getRefined(dao.riscoSeguranca().getOne());
            if(result==null){
                LogArquivo.GravaArquivoTextoDetalhado(Messages.NO_DATA_FOUND+getTableName());
            }
            return result;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return null;
        }
    }


    public RiscoSeguranca getOneWithParameters(List<SQLCondition> sqlConditions){
        try {
            RiscoSeguranca result =  getRefined(dao.riscoSeguranca().getOneWithParameters(sqlConditions));
            if(result==null){
                LogArquivo.GravaArquivoTextoDetalhado(Messages.NO_DATA_FOUND+getTableName());
            }
            return result;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return null;
        }
    }

    public String insertOffline( List<RiscoSeguranca> list){
        try {
            dao.riscoSeguranca().insertAll(list);
            return Messages.SUCCESS_MESSAGE;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return Messages.error(Constants.INSERT,getTableName());
        }
    }

    public String insertOneOffline(RiscoSeguranca item){
        try {
            dao.riscoSeguranca().insert(item);
            return Messages.SUCCESS_MESSAGE;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return Messages.error(Constants.INSERT,getTableName());
        }
    }
    public String deleteAllOffline(){
        try {
            dao.riscoSeguranca().deleteAll();
            return Messages.SUCCESS_MESSAGE;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return Messages.error(Constants.DELETE, getTableName());
        }
    }

    public String load() {
        if(Constants.TEST_LOAD_UNLOAD) return Messages.SUCCESS_MESSAGE;
        List<RiscoSeguranca> list;
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
