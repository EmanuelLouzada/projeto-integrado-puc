package com.tees.checklist.commons;

import android.content.Context;

import com.tees.checklist.data.model.Configuracoes;
import com.tees.checklist.data.model.Usuario;

public  class Preferences {

    private static android.content.SharedPreferences sharedPreferences;

    private Context context;


    public Preferences(Context context){
        this.context = context;
    }


    public boolean  isLogged(){
        if(getUser()!=null) return true; else return false;
    }

    public boolean  logout(){
        if(getUser()!=null){
            clearUser();
            clearSettings();
            return true;
        } else {
            return false;
        }
    }

    public  Usuario getUser(){
        sharedPreferences =  context.getSharedPreferences("user", Context.MODE_PRIVATE);
        if(sharedPreferences.getInt("Codigo",0) != 0) {
            Usuario user = new Usuario();
            user.id = sharedPreferences.getInt("Codigo", 0);
            user.no_login = sharedPreferences.getString("Login", null);
            user.no_usuario = sharedPreferences.getString("NomeUsuario", null);
            user.de_senha = sharedPreferences.getString("Senha", null);

            return user;
        }else{
            return null;
        }
    }

    public void  clearUser() {
        sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().apply();
    }

    public void  setUser(Usuario user) {
        sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("Codigo", user.id);
        editor.putString("Login", user.no_login);
        editor.putString("NomeUsuario", String.valueOf(user.no_usuario));
        editor.putString("Senha", String.valueOf(user.de_senha));
        editor.apply();
    }

    public Configuracoes getSettings(){
            sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
            Configuracoes settings = new Configuracoes();
            settings.carregado = sharedPreferences.getString("Carregado", null);
            settings.id = sharedPreferences.getInt("Id", 0);
            settings.ip_servidor_carga = sharedPreferences.getString("IpServidorCarga", null);
            settings.ip_servidor_descarga = sharedPreferences.getString("IpServidorDescarga", null);
            settings.modo_debug = sharedPreferences.getInt("ModoDebug", 0);
            settings.porta_carga = sharedPreferences.getString("PortaCarga", null);
            settings.porta_descarga = sharedPreferences.getString("PortaDescarga", null);
            return settings;
    }

    public void  clearSettings() {
        sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().apply();
    }

    public void  setSettings(Configuracoes settings) {
        sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Carregado", settings.carregado);
        editor.putInt("Id", settings.id);
        editor.putString("IpServidorCarga", settings.ip_servidor_carga);
        editor.putString("IpServidorDescarga", settings.ip_servidor_descarga);
        editor.putInt("ModoDebug", settings.modo_debug);
        editor.putString("PortaCarga", settings.porta_carga);
        editor.putString("PortaDescarga", settings.porta_descarga);
        editor.apply();
    }

    public void setSetting(String key, String value){
        sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        android.content.SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }
}
