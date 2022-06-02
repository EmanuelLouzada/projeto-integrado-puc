package com.tees.checklist.repository;

import com.tees.checklist.api.request.DefaultDataRequest;
import com.tees.checklist.base.BaseAPI;
import com.tees.checklist.base.BaseRepository;
import com.tees.checklist.commons.Constants;
import com.tees.checklist.commons.LogArquivo;
import com.tees.checklist.commons.Messages;
import com.tees.checklist.data.db.AppDatabase;
import com.tees.checklist.data.model.Configuracoes;
import com.tees.checklist.data.model.InspecaoEPC;
import com.tees.checklist.data.utils.SQLCondition;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;

public class InspecaoEPCRepository extends BaseRepository<InspecaoEPC> {

    private AppDatabase dao;
    private BaseAPI api;
    public Configuracoes settings;
    public InspecaoEPCRepository(AppDatabase dao, BaseAPI api,Configuracoes settings){
        this.dao  = dao;
        this.api = api;
        this.settings = settings;
    }


    @Override
    public InspecaoEPC getRefinedToInsertOrUpdate(InspecaoEPC item) {
        return item;
    }

    @Override
    public InspecaoEPC getRefined(InspecaoEPC item){
        if (item == null) return null;
        return item;
    }


    @SuppressWarnings("unchecked")
    public List<InspecaoEPC> getAllOnline(DefaultDataRequest data) throws Exception {
        Call<List<InspecaoEPC>> resources = api.loadInspecaoEPC(data);
        return (List<InspecaoEPC>)returnAPIData(resources);
    }

    public List<InspecaoEPC> getWithParameters(List<SQLCondition> sqlConditions){
        try {
            List<InspecaoEPC> result =  getRefinedList(dao.inspecaoEPC().getWithParameters(sqlConditions));
            if(result==null){
                LogArquivo.GravaArquivoTextoDetalhado(Messages.NO_DATA_FOUND+getTableName());
            }
            return result;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return null;
        }
    }

    public InspecaoEPC getOne(){
        try {
            InspecaoEPC result =  getRefined(dao.inspecaoEPC().getOne());
            if(result==null){
                LogArquivo.GravaArquivoTextoDetalhado(Messages.NO_DATA_FOUND+getTableName());
            }
            return result;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return null;
        }
    }


    public InspecaoEPC getOneWithParameters(List<SQLCondition> sqlConditions){
        try {
            InspecaoEPC result =  getRefined(dao.inspecaoEPC().getOneWithParameters(sqlConditions));
            if(result==null){
                LogArquivo.GravaArquivoTextoDetalhado(Messages.NO_DATA_FOUND+getTableName());
            }
            return result;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return null;
        }
    }

    public String insertOffline( List<InspecaoEPC> list){
        try {
            dao.inspecaoEPC().insertAll(list);
            return Messages.SUCCESS_MESSAGE;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return Messages.error(Constants.INSERT,getTableName());
        }
    }

    public Single<Long> insertOfflineSingle(InspecaoEPC item){
        return dao.inspecaoEPC().insertSingle(getRefined(item));
    }

    public String deleteAllOffline(){
        try {
            dao.inspecaoEPC().deleteAll();
            return Messages.SUCCESS_MESSAGE;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return Messages.error(Constants.DELETE, getTableName());
        }
    }

    public String load() {
        if(Constants.TEST_LOAD_UNLOAD) return Messages.SUCCESS_MESSAGE;
        List<InspecaoEPC> list;
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
