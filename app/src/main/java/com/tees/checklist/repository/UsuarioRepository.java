package com.tees.checklist.repository;

import com.tees.checklist.api.request.UsuarioRequest;
import com.tees.checklist.base.BaseAPI;
import com.tees.checklist.base.BaseRepository;
import com.tees.checklist.commons.Constants;
import com.tees.checklist.commons.LogArquivo;
import com.tees.checklist.commons.Messages;
import com.tees.checklist.data.db.AppDatabase;
import com.tees.checklist.data.model.Configuracoes;
import com.tees.checklist.data.model.Usuario;
import com.tees.checklist.data.utils.SQLCondition;

import java.util.List;

import retrofit2.Call;

public  class UsuarioRepository extends BaseRepository<Usuario> {

    private AppDatabase dao;
    private BaseAPI api;
    public Configuracoes settings;
    public UsuarioRepository(AppDatabase dao, BaseAPI api,Configuracoes settings){
        this.dao  = dao;
        this.api = api;
        this.settings = settings;
    }


    @Override
    public Usuario getRefinedToInsertOrUpdate(Usuario item) {
        return item;
    }

    @Override
    public Usuario getRefined(Usuario item){
        if (item == null) return null;
        return item;
    }


    @SuppressWarnings("unchecked")
    public List<Usuario> getAllOnline(UsuarioRequest data) throws Exception {
        Call<List<Usuario>> resources = api.loadUsuario(data);
        return (List<Usuario>)returnAPIData(resources);
    }

    public List<Usuario> getWithParameters(List<SQLCondition> sqlConditions){
        try {
            List<Usuario> result =  getRefinedList(dao.usuario().getWithParameters(sqlConditions));
            if(result==null){
                LogArquivo.GravaArquivoTextoDetalhado(Messages.NO_DATA_FOUND+getTableName());
            }
            return result;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return null;
        }
    }

    public Usuario getDefault(){

        Usuario item = new Usuario();
        item.no_login = "admin";
        item.no_usuario = "Admin";
        item.de_senha = "admin";
        return item;

    }


    public String insertOne(Usuario item) {
        try {
            dao.usuario().insert(item);
            return Messages.SUCCESS_MESSAGE;
        } catch (Exception e) {
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return Messages.error(Constants.INSERT, getTableName());
        }
    }

    public Usuario getOne(){
        try {
            Usuario result =  getRefined(dao.usuario().getOne());
            if(result==null){
                LogArquivo.GravaArquivoTextoDetalhado(Messages.NO_DATA_FOUND+getTableName());
            }
            return result;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return null;
        }
    }


    public Usuario getOneWithParameters(List<SQLCondition> sqlConditions){
        try {
            Usuario result =  getRefined(dao.usuario().getOneWithParameters(sqlConditions));
            if(result==null){
                LogArquivo.GravaArquivoTextoDetalhado(Messages.NO_DATA_FOUND+getTableName());
            }
            return result;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return null;
        }
    }

    public Usuario getUsuarioByUserNameOffline(String username){
        try {

            List<Usuario> result = dao.usuario().getByUser(username);
            if(result == null || result.size() == 0){
                LogArquivo.GravaArquivoTextoDetalhado(Messages.NO_DATA_FOUND+getTableName());
                return null;
            }
            return result.get(0);
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return null;
        }
    }

    public List<Usuario> getUsuariosByUserNameAndPasswordOnline(UsuarioRequest data ) throws Exception {
        Call<List<Usuario>> resources = api.loadUsuario(data);
        return (List<Usuario>)returnAPIData(resources);
    }

    public String insertOffline( List<Usuario> list){
        try {
            dao.usuario().insertAll(list);
            return Messages.SUCCESS_MESSAGE;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return Messages.error(Constants.INSERT,getTableName());
        }
    }

    public String deleteAllOffline(){
        try {
            dao.usuario().deleteAll();
            return Messages.SUCCESS_MESSAGE;
        }catch (Exception e){
            LogArquivo.GravaArquivoTextoDetalhado(e.getMessage());
            return Messages.error(Constants.DELETE, getTableName());
        }
    }

    public String load() {
        if(Constants.TEST_LOAD_UNLOAD) return Messages.SUCCESS_MESSAGE;
        List<Usuario> list;
        try {
            UsuarioRequest data = new UsuarioRequest();
            list =  getAllOnline(data);
            if (list == null || list.size() ==0 )
                return  Constants.TEST ? Messages.SUCCESS_MESSAGE:Messages.noOnlineDataFound(getTableName());
        } catch (Exception e) {
            return  Messages.WEBSERVICE_ERROR_MESSAGE;
        }
        return insertOffline(list);

    }

}
