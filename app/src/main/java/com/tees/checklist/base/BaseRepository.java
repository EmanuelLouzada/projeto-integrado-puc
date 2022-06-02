package com.tees.checklist.base;

import android.accounts.NetworkErrorException;

import com.tees.checklist.commons.Messages;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public abstract class BaseRepository<T>  {


    public String getTableName() {

        return this.getClass().getName().replace(this.getClass().getPackage().getName(),"").toUpperCase().replace("REPOSITORY","").replace(".","");
    }

    public List<T> getRefinedList(List<T> result){
        if(result==null) return null;
        List<T> newList = new ArrayList<>();
        for (T item: result){
            newList.add(getRefined((T)item));
        }
        return newList;
    }

    public List<T> getRefinedListToInsertOrUpdate(List<T> result){
        if(result==null) return null;
        List<T> newList = new ArrayList<>();
        for (T item: result){
            newList.add(getRefinedToInsertOrUpdate((T)item));
        }
        return newList;
    }
    public abstract T getRefinedToInsertOrUpdate(T item);
    public abstract T getRefined(T item);

    /*public List<T> getAPIData(Call<List<T>> resources) throws Exception {
        Response<List<T>> response = resources.execute();
            if(response.isSuccessful()){
                return response.body();
            }else{
                LogArquivo.GravaArquivoTextoDetalhado(response.errorBody().toString());
                throw new Exception(Messages.WEBSERVICE_ERROR_MESSAGE);
            }
    }
    public Boolean postAPIData(Call<Boolean> resources) throws Exception {
            Response<Boolean> response = resources.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                LogArquivo.GravaArquivoTextoDetalhado(response.errorBody().toString());
                throw new Exception(Messages.WEBSERVICE_ERROR_MESSAGE);
            }
    }*/

    public Object returnAPIData(Object resources) throws Exception {
        try {
            Call<?> call = (Call<?>) resources;
            Response<?> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                throw new Exception(response.errorBody().toString());
            }
        } catch (NetworkErrorException | ConnectException e1) {
            throw new Exception(Messages.NO_CONNECTION_MESSAGE);
        } catch (SocketTimeoutException e2) {
            throw new Exception(Messages.TIMEOUT_MESSAGE);
        } catch (Exception e4) {
            throw new Exception(Messages.GENERIC_ERROR_MESSAGE);

        }
    }
    public String getError(Exception e, String message) {
        if (e.toString().equals(Messages.NO_CONNECTION_MESSAGE) || e.toString().equals(Messages.TIMEOUT_MESSAGE) || e.toString().equals(Messages.GENERIC_ERROR_MESSAGE)) {
            return Messages.WEBSERVICE_ERROR_MESSAGE;
        } else {
            return Messages.errorPostOnline(message);
        }
    }
}

