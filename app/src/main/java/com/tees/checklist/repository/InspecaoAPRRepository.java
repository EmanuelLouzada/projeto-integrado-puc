package com.tees.checklist.repository;

import com.tees.checklist.api.request.DefaultDataRequest;
import com.tees.checklist.base.BaseAPI;
import com.tees.checklist.base.BaseRepository;
import com.tees.checklist.commons.Constants;
import com.tees.checklist.commons.LogArquivo;
import com.tees.checklist.commons.Messages;
import com.tees.checklist.data.db.AppDatabase;
import com.tees.checklist.data.model.Configuracoes;
import com.tees.checklist.data.model.InspecaoAPR;
import com.tees.checklist.data.utils.SQLCondition;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;

public class InspecaoAPRRepository extends BaseRepository<InspecaoAPR> {

    private AppDatabase dao;
    private BaseAPI api;
    public Configuracoes settings;
    public InspecaoAPRRepository(AppDatabase dao, BaseAPI api,Configuracoes settings){
        this.dao  = dao;
        this.api = api;
        this.settings = settings;
    }


    @Override
    public InspecaoAPR getRefinedToInsertOrUpdate(InspecaoAPR item) {
        return item;
    }

    @Override
    public InspecaoAPR getRefined(InspecaoAPR item){
        if (item == null) return null;
        return item;
    }


    @SuppressWarnings("unchecked")
    public List<InspecaoAPR> getAllOnline(DefaultDataRequest data) throws Exception {
        Call<List<InspecaoAPR>> resources = api.loadInspecaoAPR(data);
        return (List<InspecaoAPR>)returnAPIData(resources);
    }

    public List<InspecaoAPR> getWithParameters(List<SQLCondition> sqlConditions){
        try {
            List<InspecaoAPR> result =  getRefinedList(dao.inspecaoAPR().getWithParameters(sqlConditions));
            if(result==null){
                LogArquivo.GravaArquivoTextoDetalhado(Messages.NO_DATA_FOUND+getTableName());
            }
            return result;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return null;
        }
    }

    public InspecaoAPR getOne(){
        try {
            InspecaoAPR result =  getRefined(dao.inspecaoAPR().getOne());
            if(result==null){
                LogArquivo.GravaArquivoTextoDetalhado(Messages.NO_DATA_FOUND+getTableName());
            }
            return result;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return null;
        }
    }


    public InspecaoAPR getOneWithParameters(List<SQLCondition> sqlConditions){
        try {
            InspecaoAPR result =  getRefined(dao.inspecaoAPR().getOneWithParameters(sqlConditions));
            if(result==null){
                LogArquivo.GravaArquivoTextoDetalhado(Messages.NO_DATA_FOUND+getTableName());
            }
            return result;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return null;
        }
    }

    public String insertOffline( List<InspecaoAPR> list){
        try {
            dao.inspecaoAPR().insertAll(list);
            return Messages.SUCCESS_MESSAGE;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return Messages.error(Constants.INSERT,getTableName());
        }
    }



    public String insertOneOffline(InspecaoAPR item){
        try {
            dao.inspecaoAPR().insert(item);
            return Messages.SUCCESS_MESSAGE;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return Messages.error(Constants.INSERT,getTableName());
        }
    }


    public Single<Long> insertOfflineSingle(InspecaoAPR item){
        return dao.inspecaoAPR().insertSingle(getRefined(item));
    }



    public String deleteAllOffline(){
        try {
            dao.inspecaoAPR().deleteAll();
            return Messages.SUCCESS_MESSAGE;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return Messages.error(Constants.DELETE, getTableName());
        }
    }

    public String load() {
        if(Constants.TEST_LOAD_UNLOAD) return Messages.SUCCESS_MESSAGE;
        List<InspecaoAPR> list;
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
