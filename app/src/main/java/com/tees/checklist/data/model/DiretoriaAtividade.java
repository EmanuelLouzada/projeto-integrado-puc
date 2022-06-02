package com.tees.checklist.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.tees.checklist.commons.TableNames;
import com.tees.checklist.data.converter.DateConverter;

import java.util.Date;

@Entity(tableName = TableNames.DIRETORIA_ATIVIDADE)
public class DiretoriaAtividade {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NonNull
    public int id;

    @ColumnInfo(name = "fk_id_atividade")
    @NonNull
    public Integer fk_id_atividade;

    @ColumnInfo(name = "fk_id_diretoria")
    @NonNull
    public Integer fk_id_diretoria;

    @ColumnInfo(name = "ts_atualizacao")
    @TypeConverters(DateConverter.class)
    public Date ts_atualizacao;

}
