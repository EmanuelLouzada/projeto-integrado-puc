package com.tees.checklist.repository;

import com.tees.checklist.api.request.DefaultDataRequest;
import com.tees.checklist.base.BaseAPI;
import com.tees.checklist.base.BaseRepository;
import com.tees.checklist.commons.Constants;
import com.tees.checklist.commons.LogArquivo;
import com.tees.checklist.commons.Messages;
import com.tees.checklist.data.db.AppDatabase;
import com.tees.checklist.data.model.Configuracoes;
import com.tees.checklist.data.model.DiretoriaAtividade;
import com.tees.checklist.data.utils.SQLCondition;

import java.util.List;

import retrofit2.Call;

public class DiretoriaAtividadeRepository extends BaseRepository<DiretoriaAtividade> {

    private AppDatabase dao;
    private BaseAPI api;
    public Configuracoes settings;
    public DiretoriaAtividadeRepository(AppDatabase dao, BaseAPI api,Configuracoes settings){
        this.dao  = dao;
        this.api = api;
        this.settings = settings;
    }


    @Override
    public DiretoriaAtividade getRefinedToInsertOrUpdate(DiretoriaAtividade item) {
        return item;
    }

    @Override
    public DiretoriaAtividade getRefined(DiretoriaAtividade item){
        if (item == null) return null;
        return item;
    }


    @SuppressWarnings("unchecked")
    public List<DiretoriaAtividade> getAllOnline(DefaultDataRequest data) throws Exception {
        Call<List<DiretoriaAtividade>> resources = api.loadDiretoriaAtividade(data);
        return (List<DiretoriaAtividade>)returnAPIData(resources);
    }

    public List<DiretoriaAtividade> getWithParameters(List<SQLCondition> sqlConditions){
        try {
            List<DiretoriaAtividade> result =  getRefinedList(dao.diretoriaAtividade().getWithParameters(sqlConditions));
            if(result==null){
                LogArquivo.GravaArquivoTextoDetalhado(Messages.NO_DATA_FOUND+getTableName());
            }
            return result;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return null;
        }
    }

    public DiretoriaAtividade getOne(){
        try {
            DiretoriaAtividade result =  getRefined(dao.diretoriaAtividade().getOne());
            if(result==null){
                LogArquivo.GravaArquivoTextoDetalhado(Messages.NO_DATA_FOUND+getTableName());
            }
            return result;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return null;
        }
    }


    public DiretoriaAtividade getOneWithParameters(List<SQLCondition> sqlConditions){
        try {
            DiretoriaAtividade result =  getRefined(dao.diretoriaAtividade().getOneWithParameters(sqlConditions));
            if(result==null){
                LogArquivo.GravaArquivoTextoDetalhado(Messages.NO_DATA_FOUND+getTableName());
            }
            return result;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return null;
        }
    }

    public String insertOffline( List<DiretoriaAtividade> list){
        try {
            dao.diretoriaAtividade().insertAll(list);
            return Messages.SUCCESS_MESSAGE;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return Messages.error(Constants.INSERT,getTableName());
        }
    }

    public String deleteAllOffline(){
        try {
            dao.diretoriaAtividade().deleteAll();
            return Messages.SUCCESS_MESSAGE;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return Messages.error(Constants.DELETE, getTableName());
        }
    }

    public String load() {
        if(Constants.TEST_LOAD_UNLOAD) return Messages.SUCCESS_MESSAGE;
        List<DiretoriaAtividade> list;
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
