package com.tees.checklist.service;

import com.tees.checklist.api.WebAPI;
import com.tees.checklist.api.request.UsuarioRequest;
import com.tees.checklist.base.BaseAPI;
import com.tees.checklist.commons.Constants;
import com.tees.checklist.commons.LogArquivo;
import com.tees.checklist.commons.Messages;
import com.tees.checklist.commons.Preferences;
import com.tees.checklist.data.db.AppDatabase;
import com.tees.checklist.data.model.Configuracoes;
import com.tees.checklist.data.model.Usuario;
import com.tees.checklist.repository.ConfiguracoesRepository;
import com.tees.checklist.repository.UsuarioRepository;

import java.util.List;

public class LoginService {

    UsuarioRepository repositoryUsuario;
    ConfiguracoesRepository configuracoesRepository;
    UsuarioRepository UsuarioRepository;
    AppDatabase dao;
    BaseAPI api;
    Preferences preferences;
    public LoginService(AppDatabase dao, Preferences preferences){
        this.dao = dao;
        this.preferences = preferences;
        configuracoesRepository = new ConfiguracoesRepository(dao);
    }

    private Usuario user = null;


    private void setLoggedInUser(Usuario user) {
        this.user = user;
        preferences.setUser(this.user);
        preferences.setSettings(configuracoesRepository.getOne());
    }

    public boolean setPrefencesSettings() {
        Configuracoes settings = configuracoesRepository.getOne();
        if(settings!=null) {
            preferences.setSettings(settings);
        }else {
            //Default Settings and User
            configuracoesRepository.insertOne(configuracoesRepository.getDefault());
            repositoryUsuario  = new UsuarioRepository(dao,WebAPI.getApi(),getPreferenceSettings());
            repositoryUsuario.insertOne(repositoryUsuario.getDefault());
        }
        return true;
    }
    private void setLogOutUser() {
        this.user = null;
        preferences.clearUser();
        preferences.clearSettings();
    }

    public Usuario getUser(){
        return preferences.getUser();
    }

    public Configuracoes getPreferenceSettings(){
        return preferences.getSettings();
    }

    public String login(String username, String password) {


        if(!setPrefencesSettings()) return Messages.GENERIC_ERROR_MESSAGE;
        if(getPreferenceSettings()==null)  return Messages.GENERIC_ERROR_MESSAGE;
        //WebAPI.setAPI(Constants.CARGA, null, preferences.getSettings());

        //WebAPI.setAPI(Constants.CARGA, preferences.getUser(), preferences.getSettings());
        //if(WebAPI.getApi()==null) return Messages.WEBSERVICE_ERROR_MESSAGE;
        repositoryUsuario  = new UsuarioRepository(dao,WebAPI.getApi(),getPreferenceSettings());

        if((username.trim().isEmpty() || username == null)   ||  (password.trim().isEmpty() || password == null)  ){
            return "Usuário e Senha são obrigatórios.";        }

        Usuario result = repositoryUsuario.getUsuarioByUserNameOffline(username);

        if(result == null){
            if(Constants.TEST)  return "Usuario não encontrado";
            try {

                UsuarioRequest data = new UsuarioRequest();
                data.codigoUsuario = username;
                data.senhaUsuario = password;
                List<Usuario> list =  repositoryUsuario.getUsuariosByUserNameAndPasswordOnline(data);
                if (list == null || list.size() == 0)
                {
                    return "Usuario não encontrado";
                }

                Usuario Usuario = null;
                for (Usuario user :list) {
                    if(user.no_login.equals(username)){
                        Usuario = user;
                    }
                }

                if(Usuario == null){
                    return "Usuario não encontrado";
                }

                setLoggedInUser(Usuario);
                return Messages.SUCCESS_MESSAGE;

            }catch (Exception e){
                LogArquivo.GravaArquivoTextoDetalhado(Messages.WEBSERVICE_ERROR_MESSAGE);
                return  Messages.WEBSERVICE_ERROR_MESSAGE;

            }
        }else{
            if(!password.trim().equals(result.de_senha.trim())){
                return  "Senha informada está errada.";
            }
        }
        setLoggedInUser(result);
        WebAPI.unsetAPI();
        return Messages.SUCCESS_MESSAGE;
    }
}
