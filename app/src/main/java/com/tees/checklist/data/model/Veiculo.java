package com.tees.checklist.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.tees.checklist.commons.TableNames;
import com.tees.checklist.data.converter.DateConverter;

import java.util.Date;

@Entity(tableName = TableNames.VEICULO)
public class Veiculo {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NonNull
    public int id;

    @ColumnInfo(name = "no_marca")
    @NonNull
    public String no_marca;

    @ColumnInfo(name = "no_modelo")
    @NonNull
    public String no_modelo;

    @ColumnInfo(name = "no_ano")
    @NonNull
    public String no_ano;

    @ColumnInfo(name = "no_placa")
    @NonNull
    public String no_placa;

    @ColumnInfo(name = "ic_ativo")
    @NonNull
    public String ic_ativo;

    @ColumnInfo(name = "ts_atualizacao")
    @NonNull
    @TypeConverters(DateConverter.class)
    public Date ts_atualizacao;



}
