package com.tees.checklist.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.tees.checklist.commons.TableNames;
import com.tees.checklist.data.converter.DateConverter;

import java.util.Date;

@Entity(tableName = TableNames.LOCALIDADE)
public class Localidade {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NonNull
    public int id;

    @ColumnInfo(name = "sg_uf")
    @NonNull
    public String sg_uf;

    @ColumnInfo(name = "no_localidade")
    @NonNull
    public String no_localidade;

    @ColumnInfo(name = "ts_atualizacao")
    @TypeConverters(DateConverter.class)
    public Date ts_atualizacao;

}
