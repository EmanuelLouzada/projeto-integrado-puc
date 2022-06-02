package com.tees.checklist.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.tees.checklist.commons.TableNames;
import com.tees.checklist.data.converter.DateConverter;

import java.util.Date;

@Entity(tableName = TableNames.DIRETORIA)
public class Diretoria {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NonNull
    public int id;

    @ColumnInfo(name = "fk_id_localidade")
    @NonNull
    public Integer fk_id_localidade;

    @ColumnInfo(name = "no_diretoria")
    @NonNull
    public String no_diretoria;

    @ColumnInfo(name = "ts_atualizacao")
    @NonNull
    @TypeConverters(DateConverter.class)
    public Date ts_atualizacao;

}
