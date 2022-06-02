package com.tees.checklist.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.tees.checklist.commons.TableNames;
import com.tees.checklist.data.converter.DateConverter;

import java.util.Date;

@Entity(tableName = TableNames.INSPECAO_EPC)
public class InspecaoEPC {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NonNull
    public int id;

    @ColumnInfo(name = "ts_cadastro")
    @NonNull
    @TypeConverters(DateConverter.class)
    public Date ts_cadastro;

    @ColumnInfo(name = "fk_id_usuario")
    @NonNull
    public Integer fk_id_usuario;

    @ColumnInfo(name = "fk_id_funcionario")
    @NonNull
    public Integer fk_id_funcionario;

    @ColumnInfo(name = "fk_id_diretoria_atividade")
    @NonNull
    public Integer fk_id_diretoria_atividade;

    @ColumnInfo(name = "fk_id_servidor")
    public Integer fk_id_servidor;

    @ColumnInfo(name = "ts_sincronizacao")
    @TypeConverters(DateConverter.class)
    public Date ts_sincronizacao;

    @ColumnInfo(name = "escada_fibra_vidro_extensiva_singela")
    @NonNull
    public String escada_fibra_vidro_extensiva_singela;

    @ColumnInfo(name = "varas_manobras_telescopia")
    @NonNull
    public String varas_manobras_telescopia;

    @ColumnInfo(name = "its_apr")
    @NonNull
    public String its_apr;

    @ColumnInfo(name = "detector_presenca_ausencia")
    @NonNull
    public String detector_presenca_ausencia;

    @ColumnInfo(name = "laudo_dieletrico")
    @NonNull
    public String laudo_dieletrico;

    @ColumnInfo(name = "corda_cordilha_dupla_acao")
    @NonNull
    public String corda_cordilha_dupla_acao;

    @ColumnInfo(name = "bandeirola_sinalizacao")
    @NonNull
    public String bandeirola_sinalizacao;


}
