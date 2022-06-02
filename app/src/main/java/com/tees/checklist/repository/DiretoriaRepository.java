package com.tees.checklist.repository;

import com.tees.checklist.api.request.DefaultDataRequest;
import com.tees.checklist.base.BaseAPI;
import com.tees.checklist.base.BaseRepository;
import com.tees.checklist.commons.Constants;
import com.tees.checklist.commons.LogArquivo;
import com.tees.checklist.commons.Messages;
import com.tees.checklist.data.db.AppDatabase;
import com.tees.checklist.data.model.Configuracoes;
import com.tees.checklist.data.model.Diretoria;
import com.tees.checklist.data.utils.SQLCondition;

import java.util.List;

import retrofit2.Call;

public class DiretoriaRepository extends BaseRepository<Diretoria> {

    private AppDatabase dao;
    private BaseAPI api;
    public Configuracoes settings;
    public DiretoriaRepository(AppDatabase dao, BaseAPI api,Configuracoes settings){
        this.dao  = dao;
        this.api = api;
        this.settings = settings;
    }


    @Override
    public Diretoria getRefinedToInsertOrUpdate(Diretoria item) {
        return item;
    }

    @Override
    public Diretoria getRefined(Diretoria item){
        if (item == null) return null;
        return item;
    }


    @SuppressWarnings("unchecked")
    public List<Diretoria> getAllOnline(DefaultDataRequest data) throws Exception {
        Call<List<Diretoria>> resources = api.loadDiretoria(data);
        return (List<Diretoria>)returnAPIData(resources);
    }

    public List<Diretoria> getWithParameters(List<SQLCondition> sqlConditions){
        try {
            List<Diretoria> result =  getRefinedList(dao.diretoria().getWithParameters(sqlConditions));
            if(result==null){
                LogArquivo.GravaArquivoTextoDetalhado(Messages.NO_DATA_FOUND+getTableName());
            }
            return result;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return null;
        }
    }

    public Diretoria getOne(){
        try {
            Diretoria result =  getRefined(dao.diretoria().getOne());
            if(result==null){
                LogArquivo.GravaArquivoTextoDetalhado(Messages.NO_DATA_FOUND+getTableName());
            }
            return result;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return null;
        }
    }


    public Diretoria getOneWithParameters(List<SQLCondition> sqlConditions){
        try {
            Diretoria result =  getRefined(dao.diretoria().getOneWithParameters(sqlConditions));
            if(result==null){
                LogArquivo.GravaArquivoTextoDetalhado(Messages.NO_DATA_FOUND+getTableName());
            }
            return result;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return null;
        }
    }

    public String insertOffline( List<Diretoria> list){
        try {
            dao.diretoria().insertAll(list);
            return Messages.SUCCESS_MESSAGE;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return Messages.error(Constants.INSERT,getTableName());
        }
    }

    public String deleteAllOffline(){
        try {
            dao.diretoria().deleteAll();
            return Messages.SUCCESS_MESSAGE;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return Messages.error(Constants.DELETE, getTableName());
        }
    }

    public String load() {
        if(Constants.TEST_LOAD_UNLOAD) return Messages.SUCCESS_MESSAGE;
        List<Diretoria> list;
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
