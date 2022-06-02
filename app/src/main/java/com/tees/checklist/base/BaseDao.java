package com.tees.checklist.base;

import androidx.room.Dao;
import androidx.room.Transaction;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.tees.checklist.commons.Utils;
import com.tees.checklist.data.utils.SQLCondition;
import com.tees.checklist.data.utils.SQLField;
import com.tees.checklist.data.utils.SQLOperator;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;


@Dao
public abstract class BaseDao<T> implements IRawDao<T> {

    private String tableName;

    public BaseDao(String tableName){
        this.tableName = tableName.toLowerCase();
    }

    public String getTableName() {
        return tableName;
    }

    /*public String getTableName() {
        // Below is based on your inheritance chain
        Class clazz = (Class)
                ((ParameterizedType) getClass().getSuperclass().getGenericSuperclass())
                        .getActualTypeArguments()[0];
        // tableName = StringUtil.toSnakeCase(clazz.getSimpleName());
        String tableName = TableNameConverter.convert(clazz.getSimpleName()) ;
        return tableName;
    }*/

    public Single<List<T>> getAllSingle() {
        SimpleSQLiteQuery query = new SimpleSQLiteQuery(
                "SELECT * FROM " + getTableName()
        );

        return sqlSingle(query);
    }

    public Single<T> getOneSingle() {
        SimpleSQLiteQuery query = new SimpleSQLiteQuery(
                "SELECT * FROM " + getTableName()
        );
        return sqlOneSingle(query);
    }


    public Single<Integer> getCountSingle() {
        SimpleSQLiteQuery query = new SimpleSQLiteQuery(
                "SELECT COUNT(*) FROM " + getTableName()
        );
        return sqlCountSingle(query);
    }





    public List<T> getAll() {
        SimpleSQLiteQuery query = new SimpleSQLiteQuery(
                "SELECT * FROM " + getTableName()
        );
        return sql(query);
    }

    public T getOne() {
        SimpleSQLiteQuery query = new SimpleSQLiteQuery(
                "SELECT * FROM " + getTableName()
        );
        return sqlOne(query);
    }


    public int getCount() {
        SimpleSQLiteQuery query = new SimpleSQLiteQuery(
                "SELECT COUNT(*) FROM " + getTableName()
        );
        return sqlCount(query);
    }



    @Transaction
    public void insertAll(List<T> list) {
        List<T> trimmedList = new ArrayList<>();
        for (T item : list) {
            trimmedList.add(Utils.toTrim(item));
        }
        insertList(trimmedList);
    }

   /* @Transaction
    public void insertAll(List<T> list) {
        for (T item : list) {
            insert(item);
        }
    } */


    public void deleteAll() {
        SimpleSQLiteQuery query = new SimpleSQLiteQuery(
                "DELETE FROM " + getTableName()
        );
        delete(query);
    }

    public int deleteWithParameters(List<SQLCondition> sqlConditions, String operator){
        // Query string
        String queryString = new String();

        // List of bind parameters
        List<Object> args = new ArrayList();

        boolean containsCondition = false;

        if(operator==null || operator.isEmpty()){
            operator = " AND ";
        }

        // Beginning of query string
        queryString += "DELETE FROM  "+ getTableName() + " ";


        // Optional parts are added to query string and to args upon here
        for (SQLCondition SQLCondition : sqlConditions) {

            if (containsCondition) {
                queryString += " "+operator+ " ";
            } else {
                queryString += " WHERE ";
                containsCondition = true;
            }

            queryString += "TRIM("+SQLCondition.getNameField()+") = TRIM(?) ";
            args.add(SQLCondition.getValueField());
        }

        // End of query string
        queryString += ";";
        SimpleSQLiteQuery query = new SimpleSQLiteQuery(queryString, args.toArray());
        return sqlDelete(query);
    }



    public int getCountWithParameters(List<SQLCondition> sqlConditions){
        return getCountWithParameters(sqlConditions, SQLOperator.AND);
    }

    public int getCountWithParameters(List<SQLCondition> sqlConditions, String operator){
        // Query string
        String queryString = new String();

        // List of bind parameters
        List<Object> args = new ArrayList();

        boolean containsCondition = false;

        if(operator==null || operator.isEmpty()){
            operator = " AND ";
        }

        // Beginning of query string
        queryString += "SELECT COUNT(*) FROM "+ getTableName();

        // Optional parts are added to query string and to args upon here
        for (SQLCondition SQLCondition : sqlConditions) {

            if (containsCondition) {
                queryString += " "+operator+ " ";
            } else {
                queryString += " WHERE ";
                containsCondition = true;
            }

            queryString += "TRIM("+SQLCondition.getNameField()+") = TRIM(?) ";
            args.add(SQLCondition.getValueField());
        }

        // End of query string
        queryString += ";";
        SimpleSQLiteQuery query = new SimpleSQLiteQuery(queryString, args.toArray());
        return sqlCount(query);
    }


    public int updateWithParameter(List<SQLField> sqlFields, List<SQLCondition> sqlConditions, String operator){
        // Query string
        String queryString = new String();

        // List of bind parameters
        List<Object> args = new ArrayList();

        boolean containsCondition = false;

        if(operator==null || operator.isEmpty()){
            operator = " AND ";
        }

        // Beginning of query string
        queryString += "UPDATE "+ getTableName() + " SET ";


        // Optional parts are added to query string and to args upon here
        boolean containsField = false;
        for (SQLField sqlField : sqlFields) {

            if (containsField) {
                queryString += " , ";
            } else {
                containsField = true;
            }

            queryString += sqlField.getNameField()+ " = ?";
            args.add(sqlField.getValueField());
        }


        // Optional parts are added to query string and to args upon here
        for (SQLCondition SQLCondition : sqlConditions) {

            if (containsCondition) {
                queryString += " "+operator+ " ";
            } else {
                queryString += " WHERE ";
                containsCondition = true;
            }

            queryString += "TRIM("+SQLCondition.getNameField()+") = TRIM(?) ";
            args.add(SQLCondition.getValueField());
        }

        // End of query string
        queryString += ";";
        SimpleSQLiteQuery query = new SimpleSQLiteQuery(queryString, args.toArray());
        return sqlUpdate(query);
    }



    public SimpleSQLiteQuery createQuery(List<SQLCondition> sqlConditions, String operator, boolean limit, String orderBy){

        // Query string
        String queryString = new String();

        // List of bind parameters
        List<Object> args = new ArrayList();

        boolean containsCondition = false;

        if(operator==null || operator.isEmpty()){
            operator = " AND ";
        }

        // Beginning of query string
        queryString += "SELECT * FROM "+ getTableName();

        // Optional parts are added to query string and to args upon here
        for (SQLCondition SQLCondition : sqlConditions) {

            if (containsCondition) {
                queryString += " "+operator+ " ";
            } else {
                queryString += " WHERE ";
                containsCondition = true;
            }

            queryString += "TRIM("+SQLCondition.getNameField()+") = TRIM(?) ";
            args.add(SQLCondition.getValueField());
        }


        if(limit){
            queryString += " LIMIT 1 ";
        }



        if (getTableName().equals("ocorrencia_agrupamento") || getTableName().equals("tipo_entrega"))
        {
            queryString += " ORDER BY descripcion ";
        }else{
            if(orderBy!=null)
                queryString += " "+orderBy+" ";
        }


        // End of query string
        queryString += ";";
        SimpleSQLiteQuery query = new SimpleSQLiteQuery(queryString, args.toArray());
        return query;
    }



    public List<T> getWithParameters(List<SQLCondition> sqlConditions){
        return getWithParameters(sqlConditions,SQLOperator.AND);
    }

    public List<T> getWithParameters(List<SQLCondition> sqlConditions, String operator){
        SimpleSQLiteQuery query = createQuery(sqlConditions,operator, false, null);
        return sql(query);
    }


    public List<T> getWithParameters(List<SQLCondition> sqlConditions, String operator, String orderBy){
        SimpleSQLiteQuery query = createQuery(sqlConditions,operator, false, orderBy);
        return sql(query);
    }

    public List<T> getWithParametersAndOrder(List<SQLCondition> sqlConditions, String orderBy){
        SimpleSQLiteQuery query = createQuery(sqlConditions,SQLOperator.AND, false, orderBy);
        return sql(query);
    }


    public T getOneWithParameters(List<SQLCondition> sqlConditions){
        return getOneWithParameters(sqlConditions,SQLOperator.AND);
    }

    public T getOneWithParameters(List<SQLCondition> sqlConditions, String operator){
       return  getOneWithParameters(sqlConditions,operator, null);
    }

    public T getOneWithParameters(List<SQLCondition> sqlConditions, String operator, String orderBy){
        SimpleSQLiteQuery query = createQuery(sqlConditions,operator, false, orderBy);
        return sqlOne(query);
    }

    public T getOneWithParametersAndOrder(List<SQLCondition> sqlConditions, String orderBy){
        SimpleSQLiteQuery query = createQuery(sqlConditions,SQLOperator.AND, false, orderBy);
        return sqlOne(query);
    }


    public Single<List<T>> getWithParametersSingle(List<SQLCondition> sqlConditions){
        return getWithParametersSingle(sqlConditions,SQLOperator.AND);
    }

    public Single<List<T>> getWithParametersSingle(List<SQLCondition> sqlConditions, String operator){
        SimpleSQLiteQuery query = createQuery(sqlConditions,operator, false, null);
        return sqlSingle(query);
    }


    public Single<List<T>> getWithParametersSingle(List<SQLCondition> sqlConditions, String operator, String orderBy){
        SimpleSQLiteQuery query = createQuery(sqlConditions,operator, false, orderBy);
        return sqlSingle(query);
    }

    public Single<List<T>> getWithParametersAndOrderSingle(List<SQLCondition> sqlConditions, String orderBy){
        SimpleSQLiteQuery query = createQuery(sqlConditions,SQLOperator.AND, false, orderBy);
        return sqlSingle(query);
    }


    public Single<T> getOneWithParametersSingle(List<SQLCondition> sqlConditions){
        return getOneWithParametersSingle(sqlConditions,SQLOperator.AND);
    }

    public Single<T> getOneWithParametersSingle(List<SQLCondition> sqlConditions, String operator){
        return  getOneWithParametersSingle(sqlConditions,operator, null);
    }

    public Single<T> getOneWithParametersSingle(List<SQLCondition> sqlConditions, String operator, String orderBy){
        SimpleSQLiteQuery query = createQuery(sqlConditions,operator, false, orderBy);
        return sqlOneSingle(query);
    }

    public Single<T> getOneWithParametersAndOrderSingle(List<SQLCondition> sqlConditions, String orderBy){
        SimpleSQLiteQuery query = createQuery(sqlConditions,SQLOperator.AND, false, orderBy);
        return sqlOneSingle(query);
    }

}
