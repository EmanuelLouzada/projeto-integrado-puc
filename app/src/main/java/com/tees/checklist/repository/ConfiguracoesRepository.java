package com.tees.checklist.repository;

import com.tees.checklist.base.BaseRepository;
import com.tees.checklist.commons.Constants;
import com.tees.checklist.commons.LogArquivo;
import com.tees.checklist.commons.Messages;
import com.tees.checklist.data.db.AppDatabase;
import com.tees.checklist.data.model.Configuracoes;

import io.reactivex.Single;

public class  ConfiguracoesRepository extends BaseRepository<Configuracoes> {


    private AppDatabase dao;
    public ConfiguracoesRepository(AppDatabase dao){
        this.dao  = dao;
    }



    @Override
    public Configuracoes getRefinedToInsertOrUpdate(Configuracoes item) {
        return item;
    }


    public Configuracoes getRefined(Configuracoes item){
        if (item == null) return null;

        item.ip_servidor_carga = item.ip_servidor_carga==null? "" : item.ip_servidor_carga.trim();
        item.ip_servidor_descarga = item.ip_servidor_descarga==null? "" : item.ip_servidor_descarga.trim();
        item.porta_carga = item.porta_carga==null? "" : item.porta_carga.trim();
        item.porta_descarga = item.porta_descarga==null? "" : item.porta_descarga.trim();
        if(item.modo_debug==null) item.modo_debug = 0;
        if(item.carregado ==null) item.carregado = "";


        return item;
    }

    public Configuracoes getOne()  {

        try {
            return  getRefined(dao.configuracoes().getOne());
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return null;
        }
    }

    public Configuracoes getDefault(){

        Configuracoes item = new Configuracoes();
        item.carregado = "S";
        item.ip_servidor_carga = Constants.IPWebAPI;
        item.porta_carga = Constants.PortaWebAPI;
        item.ip_servidor_descarga  = Constants.IPWebAPI;
        item.porta_descarga =  Constants.PortaWebAPI;
        item.modo_debug = 1;
        return item;

    }

    public String insertOne(Configuracoes item) {
        try {
            dao.configuracoes().insert(item);
            return Messages.SUCCESS_MESSAGE;
        } catch (Exception e) {
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return Messages.error(Constants.INSERT, getTableName());
        }
    }


    public Single<Integer> updateOfflineSingle(Configuracoes settings){
        LogArquivo.StatusLog(settings.modo_debug);

        return dao.configuracoes().updateSingle(getRefined(settings));
    }

    public String updateFlagCarregado(String carregado){
        try {
            dao.configuracoes().updateFlagCarregado(carregado);
            return Messages.SUCCESS_MESSAGE;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return Messages.error(Constants.UPDATE, getTableName());
        }
    }
    public String updateOne(String Carregado, Long HrTrans){
        try {
            dao.configuracoes().updateFields(Carregado,HrTrans);
            return Messages.SUCCESS_MESSAGE;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return Messages.error(Constants.UPDATE, getTableName());
        }
    }



}
