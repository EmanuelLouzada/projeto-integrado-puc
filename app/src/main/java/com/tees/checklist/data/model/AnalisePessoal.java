package com.tees.checklist.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.tees.checklist.commons.TableNames;
import com.tees.checklist.data.converter.DateConverter;

import java.util.Date;

@Entity(tableName = TableNames.ANALISE_PESSOAL)
public class AnalisePessoal {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NonNull
    public int id;

    @ColumnInfo(name = "ts_cadastro")
    @NonNull
    @TypeConverters(DateConverter.class)
    public Date ts_cadastro;

    @ColumnInfo(name = "fk_id_inspecao_apr")
    @NonNull
    public Integer fk_id_inspecao_apr;

    @ColumnInfo(name = "ts_sincronizacao")
    @TypeConverters(DateConverter.class)
    public Date ts_sincronizacao;

    @ColumnInfo(name = "fk_id_servidor")
    public Integer fk_id_servidor;

    @ColumnInfo(name = "sinto_me_bem_para_executar_a_atividade")
    public String sinto_me_bem_para_executar_a_atividade;

    @ColumnInfo(name = "conheco_a_atividade_a_ser_executada")
    public String conheco_a_atividade_a_ser_executada;

    @ColumnInfo(name = "as_minhas_ferramentas_estao_ok")
    public String as_minhas_ferramentas_estao_ok;

    @ColumnInfo(name = "as_pessoas_sabem_onde_estou")
    public String as_pessoas_sabem_onde_estou;

    @ColumnInfo(name ="existe_rota_de_fuga")
    public String existe_rota_de_fuga;

}
