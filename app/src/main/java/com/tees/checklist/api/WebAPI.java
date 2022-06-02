package com.tees.checklist.api;

import android.os.Build;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tees.checklist.base.BaseAPI;
import com.tees.checklist.commons.Constants;
import com.tees.checklist.data.model.Configuracoes;
import com.tees.checklist.data.model.Usuario;

import java.util.Base64;
import java.util.concurrent.TimeUnit;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebAPI {

    private static Retrofit retrofit = null;
    private static BaseAPI api = null;
    private static OkHttpClient client;
    //private Leiturista user;


    public static BaseAPI getApi(){
        return api;
    }


    public static void setAPI(String URL) {
        if(api==null){
            String BASE_URL =URL+"/";
            try {
                api = getClient(BASE_URL,null).create(BaseAPI.class);
            }catch (Exception e){
                try {
                    api = getClient(URL+"/",null).create(BaseAPI.class);
                } catch (Exception e2){
                    api = null;
                }


            }
        }

    }

    public static void setAPI(String service, Usuario user, Configuracoes settings) {
            if(api==null){
                String BASE_URL = "";
                if(service.equals(Constants.CARGA)){
                    if(!settings.ip_servidor_carga.contains("http:")){
                        BASE_URL = "http://";
                    }
                    BASE_URL = BASE_URL + settings.ip_servidor_carga;
                    if(settings.porta_carga!=null && !settings.porta_carga.isEmpty() ){
                        BASE_URL = BASE_URL + ":" + settings.porta_carga;
                    }
                }else if(service.equals(Constants.DESCARGA)){
                    BASE_URL = settings.ip_servidor_carga;
                    if(settings.porta_carga!=null && !settings.porta_descarga.isEmpty() ){
                        BASE_URL = BASE_URL + ":" + settings.porta_descarga;
                    }
                }else{
                    BASE_URL = Constants.UrlWebAPI;
                }
                BASE_URL = BASE_URL+"/";
                try {
                    api = getClient(BASE_URL,user).create(BaseAPI.class);
                }catch (Exception e){
                    try {
                        api = getClient(Constants.UrlWebAPI+"/",user).create(BaseAPI.class);
                    } catch (Exception e2){
                        api = null;
                    }


                }
            }

    }

    public static void unsetAPI() {
        retrofit = null;
        api = null;
        client = null;
    }

    public static Retrofit getClient(String url, Usuario user) {
        //if (client == null){
            Dispatcher dispatcher=new Dispatcher();
            dispatcher.setMaxRequests(1000);
            cancelRequests();
            client = new OkHttpClient.Builder().connectTimeout(Constants.OnlineConnectTimeOut, TimeUnit.SECONDS)
                    .readTimeout(Constants.OnlineReadWriteTimeOut, TimeUnit.SECONDS)
                    .writeTimeout(Constants.OnlineReadWriteTimeOut, TimeUnit.SECONDS)
                    .dispatcher(dispatcher)
                    .addInterceptor(chain -> {

                        if (user != null) {
                            String token = user.no_login.trim() + ":" + user.de_senha;
                            String encoded;

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                encoded = Base64.getEncoder().encodeToString(token.getBytes());
                            } else {
                                encoded = android.util.Base64.encodeToString(token.getBytes(), android.util.Base64.NO_WRAP);
                            }
                            Request newRequest = chain.request().newBuilder()
                                    .addHeader("Authorization", "Basic " + encoded)
                                    .addHeader("secretKey", Constants.SECRET_KEY)
                                    .build();
                            return chain.proceed(newRequest);
                        } else {
                            Request newRequest = chain.request().newBuilder().build();
                            return chain.proceed(newRequest);
                        }
                    }).build();
        //}


        //if (retrofit==null) {

            Gson gson = new GsonBuilder()
                    .setDateFormat(Constants.FORMAT_DATE_API)
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        //}
        return retrofit;
    }

    public static void cancelRequests(){
        if(client!=null) {
            client.dispatcher().cancelAll();
        }
    }

}
