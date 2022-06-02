package com.tees.checklist.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.tees.checklist.commons.TableNames;
import com.tees.checklist.data.converter.DateConverter;

import java.util.Date;

@Entity(tableName = TableNames.EPIS_CONVENCIONAIS)
public class EPISConvencionais {

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

    @ColumnInfo(name = "calcado_de_seguranca")
    public String calcado_de_seguranca;

    @ColumnInfo(name = "capacete_com_jugular")
    public String capacete_com_jugular;

    @ColumnInfo(name = "oculos_de_seguranca")
    public String oculos_de_seguranca;

    @ColumnInfo(name = "oculos_ampla_visao")
    public String oculos_ampla_visao;

    @ColumnInfo(name = "protetor_facial")
    public String protetor_facial;

    @ColumnInfo(name = "protetor_auditivo")
    public String protetor_auditivo;

    @ColumnInfo(name = "Luva_de_vaqueta")
    public String Luva_de_vaqueta;

    @ColumnInfo(name = "luva_de_raspa")
    public String luva_de_raspa;

    @ColumnInfo(name = "luva_de_pvc")
    public String luva_de_pvc;

    @ColumnInfo(name = "respirador_contra_poeira")
    public String respirador_contra_poeira;

    @ColumnInfo(name = "mascara_autonoma")
    public String mascara_autonoma;

    @ColumnInfo(name = "macacao_tnt")
    public String macacao_tnt    ;

    @ColumnInfo(name = "cinto_de_seguranca_com_dois")
    public String cinto_de_seguranca_com_dois;

    @ColumnInfo(name = "conjunto_em_hourion_p_temp")
    public String conjunto_em_hourion_p_temp;

    @ColumnInfo(name = "conjunto_em_texion_g_p_temp")
    public String conjunto_em_texion_g_p_temp;

    @ColumnInfo(name = "conjunto_em_texion_w_p_trab_frio")
    public String conjunto_em_texion_w_p_trab_frio;

    @ColumnInfo(name = "avental_de_couro")
    public String avental_de_couro;

    @ColumnInfo(name = "perneira")
    public String perneira;

    @ColumnInfo(name = "mangote_de_couro")
    public String mangote_de_couro;

    @ColumnInfo(name = "balaclava")
    public String balaclava;

    @ColumnInfo(name = "outros")
    public String outros;
}