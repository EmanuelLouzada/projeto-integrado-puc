package com.tees.checklist.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.tees.checklist.commons.TableNames;
import com.tees.checklist.data.converter.DateConverter;

import java.util.Date;

@Entity(tableName = TableNames.FUNCIONARIO)
public class Funcionario {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NonNull
    public int id;

    @ColumnInfo(name = "no_funcionario")
    @NonNull
    public String no_funcionario;

    @ColumnInfo(name = "co_cnh")
    public String co_cnh;

    @ColumnInfo(name = "dt_validade_cnh")
    public String dt_validade_cnh;

    @ColumnInfo(name = "fk_id_usuario")
    public Integer fk_id_usuario;

    @ColumnInfo(name = "ts_atualizacao")
    @TypeConverters(DateConverter.class)
    public Date ts_atualizacao;

}
