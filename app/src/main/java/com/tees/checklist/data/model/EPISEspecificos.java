package com.tees.checklist.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.tees.checklist.commons.TableNames;
import com.tees.checklist.data.converter.DateConverter;

import java.util.Date;

@Entity(tableName = TableNames.EPIS_ESPECIFICOS)
public class EPISEspecificos {

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
    public Integer fk_id_inspecao_apr  ;

    @ColumnInfo(name = "ts_sincronizacao")
    @TypeConverters(DateConverter.class)
    public Date ts_sincronizacao;

    @ColumnInfo(name = "fk_id_servidor")
    public Integer fk_id_servidor;

    @ColumnInfo(name = "detector_de_tensao")
    public String detector_de_tensao;

    @ColumnInfo(name = "bastao_isolante")
    public String bastao_isolante;

    @ColumnInfo(name = "cadeado_de_seguranca")
    public String cadeado_de_seguranca;

    @ColumnInfo(name = "etiqueta_de_bloqueio")
    public String etiqueta_de_bloqueio;

    @ColumnInfo(name = "aterramento_temporario")
    public String aterramento_temporario;

    @ColumnInfo(name = "ferramentas_isoladas_p_1000")
    public String ferramentas_isoladas_p_1000;

    @ColumnInfo(name = "roupa_protecao_arco_eletrico_classe_2")
    public String roupa_protecao_arco_eletrico_classe_2;

    @ColumnInfo(name = "roupa_protecao_arco_eletrico_classe4")
    public String roupa_protecao_arco_eletrico_classe4;

    @ColumnInfo(name = "capuz_classe_4")
    public String capuz_classe_4;

    @ColumnInfo(name = "luva_para_300kv")
    public String luva_para_300kv;

    @ColumnInfo(name = "luva_acima_de_138kv")
    public String luva_acima_de_138kv;

    @ColumnInfo(name = "luva_para_1000kv")
    public String luva_para_1000kv    ;

    @ColumnInfo(name = "balaclava")
    public String balaclava;

    @ColumnInfo(name = "outros")
    public String outros;

}