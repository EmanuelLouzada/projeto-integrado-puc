package com.tees.checklist.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.tees.checklist.commons.TableNames;

@Entity(tableName = TableNames.CONFIGURACOES)
public class Configuracoes {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NonNull
    public int id;

    @ColumnInfo(name = "ip_servidor_carga")
    public String ip_servidor_carga;

    @ColumnInfo(name = "ip_servidor_descarga")
    public String ip_servidor_descarga;

    @ColumnInfo(name = "porta_carga")
    public String porta_carga;

    @ColumnInfo(name = "porta_descarga")
    public String porta_descarga;

    @ColumnInfo(name = "carregado")
    public String carregado;

    @ColumnInfo(name = "hr_trans")
    public String hr_trans;

    @ColumnInfo(name = "modo_debug")
    public Integer modo_debug;

}