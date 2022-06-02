package com.tees.checklist.base;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

import java.util.List;

import io.reactivex.Single;

@Dao
public
interface IRawDao<T> {
    @RawQuery
    List<T> sql(SupportSQLiteQuery query);

    @RawQuery
    Single<List<T>> sqlSingle(SupportSQLiteQuery query);

    @RawQuery
    T sqlOne(SupportSQLiteQuery query);

    @RawQuery
    int sqlCount(SupportSQLiteQuery query);


    @RawQuery
    Single<T> sqlOneSingle(SupportSQLiteQuery query);

    @RawQuery
    Single<Integer> sqlCountSingle(SupportSQLiteQuery query);



    @RawQuery
    int sqlUpdate(SupportSQLiteQuery query);

    @RawQuery
    int sqlDelete(SupportSQLiteQuery query);

    @RawQuery
    int delete(SupportSQLiteQuery query);

    @Update
    void update(T item);

    @Update
    Single<Integer> updateSingle(T item);

    @Insert
    Single<Long> insertSingle(T item);


    @Insert
    void insertList(List<T> item);

    @Insert
    void insert(T item);

}
