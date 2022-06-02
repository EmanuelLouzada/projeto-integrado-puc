package com.tees.checklist.repository;

import com.tees.checklist.api.request.DefaultDataRequest;
import com.tees.checklist.base.BaseAPI;
import com.tees.checklist.base.BaseRepository;
import com.tees.checklist.commons.Constants;
import com.tees.checklist.commons.LogArquivo;
import com.tees.checklist.commons.Messages;
import com.tees.checklist.data.db.AppDatabase;
import com.tees.checklist.data.model.Configuracoes;
import com.tees.checklist.data.model.InspecaoVeicular;
import com.tees.checklist.data.utils.SQLCondition;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;

public class InspecaoVeicularRepository extends BaseRepository<InspecaoVeicular> {

    private AppDatabase dao;
    private BaseAPI api;
    public Configuracoes settings;
    public InspecaoVeicularRepository(AppDatabase dao, BaseAPI api,Configuracoes settings){
        this.dao  = dao;
        this.api = api;
        this.settings = settings;
    }


    @Override
    public InspecaoVeicular getRefinedToInsertOrUpdate(InspecaoVeicular item) {
        return item;
    }

    @Override
    public InspecaoVeicular getRefined(InspecaoVeicular item){
        if (item == null) return null;
        return item;
    }


    @SuppressWarnings("unchecked")
    public List<InspecaoVeicular> getAllOnline(DefaultDataRequest data) throws Exception {
        Call<List<InspecaoVeicular>> resources = api.loadInspecaoVeicular(data);
        return (List<InspecaoVeicular>)returnAPIData(resources);
    }

    public List<InspecaoVeicular> getWithParameters(List<SQLCondition> sqlConditions){
        try {
            List<InspecaoVeicular> result =  getRefinedList(dao.inspecaoVeicular().getWithParameters(sqlConditions));
            if(result==null){
                LogArquivo.GravaArquivoTextoDetalhado(Messages.NO_DATA_FOUND+getTableName());
            }
            return result;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return null;
        }
    }

    public InspecaoVeicular getOne(){
        try {
            InspecaoVeicular result =  getRefined(dao.inspecaoVeicular().getOne());
            if(result==null){
                LogArquivo.GravaArquivoTextoDetalhado(Messages.NO_DATA_FOUND+getTableName());
            }
            return result;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return null;
        }
    }


    public InspecaoVeicular getOneWithParameters(List<SQLCondition> sqlConditions){
        try {
            InspecaoVeicular result =  getRefined(dao.inspecaoVeicular().getOneWithParameters(sqlConditions));
            if(result==null){
                LogArquivo.GravaArquivoTextoDetalhado(Messages.NO_DATA_FOUND+getTableName());
            }
            return result;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return null;
        }
    }

    public String insertOffline( List<InspecaoVeicular> list){
        try {
            dao.inspecaoVeicular().insertAll(list);
            return Messages.SUCCESS_MESSAGE;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return Messages.error(Constants.INSERT,getTableName());
        }
    }

    public Single<Long> insertOfflineSingle(InspecaoVeicular item){
        return dao.inspecaoVeicular().insertSingle(getRefined(item));
    }


    public String deleteAllOffline(){
        try {
            dao.inspecaoVeicular().deleteAll();
            return Messages.SUCCESS_MESSAGE;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return Messages.error(Constants.DELETE, getTableName());
        }
    }

    public String load() {
        if(Constants.TEST_LOAD_UNLOAD) return Messages.SUCCESS_MESSAGE;
        List<InspecaoVeicular> list;
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
