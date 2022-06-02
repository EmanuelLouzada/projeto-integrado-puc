package com.tees.checklist.repository;

import com.tees.checklist.api.request.DefaultDataRequest;
import com.tees.checklist.base.BaseAPI;
import com.tees.checklist.base.BaseRepository;
import com.tees.checklist.commons.Constants;
import com.tees.checklist.commons.LogArquivo;
import com.tees.checklist.commons.Messages;
import com.tees.checklist.data.db.AppDatabase;
import com.tees.checklist.data.model.Configuracoes;
import com.tees.checklist.data.model.InspecaoEPI;
import com.tees.checklist.data.utils.SQLCondition;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;

public class InspecaoEPIRepository extends BaseRepository<InspecaoEPI> {

    private AppDatabase dao;
    private BaseAPI api;
    public Configuracoes settings;
    public InspecaoEPIRepository(AppDatabase dao, BaseAPI api,Configuracoes settings){
        this.dao  = dao;
        this.api = api;
        this.settings = settings;
    }


    @Override
    public InspecaoEPI getRefinedToInsertOrUpdate(InspecaoEPI item) {
        return item;
    }

    @Override
    public InspecaoEPI getRefined(InspecaoEPI item){
        if (item == null) return null;
        return item;
    }


    @SuppressWarnings("unchecked")
    public List<InspecaoEPI> getAllOnline(DefaultDataRequest data) throws Exception {
        Call<List<InspecaoEPI>> resources = api.loadInspecaoEPI(data);
        return (List<InspecaoEPI>)returnAPIData(resources);
    }

    public List<InspecaoEPI> getWithParameters(List<SQLCondition> sqlConditions){
        try {
            List<InspecaoEPI> result =  getRefinedList(dao.inspecaoEPI().getWithParameters(sqlConditions));
            if(result==null){
                LogArquivo.GravaArquivoTextoDetalhado(Messages.NO_DATA_FOUND+getTableName());
            }
            return result;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return null;
        }
    }

    public InspecaoEPI getOne(){
        try {
            InspecaoEPI result =  getRefined(dao.inspecaoEPI().getOne());
            if(result==null){
                LogArquivo.GravaArquivoTextoDetalhado(Messages.NO_DATA_FOUND+getTableName());
            }
            return result;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return null;
        }
    }


    public InspecaoEPI getOneWithParameters(List<SQLCondition> sqlConditions){
        try {
            InspecaoEPI result =  getRefined(dao.inspecaoEPI().getOneWithParameters(sqlConditions));
            if(result==null){
                LogArquivo.GravaArquivoTextoDetalhado(Messages.NO_DATA_FOUND+getTableName());
            }
            return result;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return null;
        }
    }

    public String insertOffline( List<InspecaoEPI> list){
        try {
            dao.inspecaoEPI().insertAll(list);
            return Messages.SUCCESS_MESSAGE;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return Messages.error(Constants.INSERT,getTableName());
        }
    }


    public Single<Long> insertOfflineSingle(InspecaoEPI item){
        return dao.inspecaoEPI().insertSingle(getRefined(item));
    }

    public String deleteAllOffline(){
        try {
            dao.inspecaoEPI().deleteAll();
            return Messages.SUCCESS_MESSAGE;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return Messages.error(Constants.DELETE, getTableName());
        }
    }

    public String load() {
        if(Constants.TEST_LOAD_UNLOAD) return Messages.SUCCESS_MESSAGE;
        List<InspecaoEPI> list;
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
