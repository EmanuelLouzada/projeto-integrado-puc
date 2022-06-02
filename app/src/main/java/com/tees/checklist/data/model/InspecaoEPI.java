package com.tees.checklist.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.tees.checklist.commons.TableNames;
import com.tees.checklist.data.converter.DateConverter;

import java.util.Date;

@Entity(tableName = TableNames.INSPECAO_EPI)
public class InspecaoEPI {

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

    @ColumnInfo(name = "ts_sincronizacao")
    @TypeConverters(DateConverter.class)
    public Date ts_sincronizacao;

    @ColumnInfo(name = "fk_id_servidor")
    public Integer fk_id_servidor;

    @ColumnInfo(name = "calcado_de_seguranca_s_parte_metalica")
    public String calcado_de_seguranca_s_parte_metalica;

    @ColumnInfo(name = "uniforme_resistente_a_chama")
    public String uniforme_resistente_a_chama;

    @ColumnInfo(name = "protetor_facial")
    public String protetor_facial;

    @ColumnInfo(name = "capacete_de_segunranca_aba_frontal")
    public String capacete_de_segunranca_aba_frontal;

    @ColumnInfo(name = "cinto_de_segunranca_tipo_para_quedista")
    public String cinto_de_segunranca_tipo_para_quedista;

    @ColumnInfo(name = "cracha_de_identificacao")
    public String cracha_de_identificacao;

    @ColumnInfo(name = "laudos_dos_epis")
    public String laudos_dos_epis;

    @ColumnInfo(name = "luva_pu")
    public String luva_pu;

    @ColumnInfo(name = "luva_de_protecao_vaqueta")
    public String luva_de_protecao_vaqueta;

    @ColumnInfo(name = "luvas_isolantes_classe_0")
    public String luvas_isolantes_classe_0;

    @ColumnInfo(name = "luvas_isolantes_classe_2")
    public String luvas_isolantes_classe_2;

    @ColumnInfo(name = "manga_isolante_classe_2")
    public String manga_isolante_classe_2;

    @ColumnInfo(name = "oculos_de_seguranca_lentes_incolor")
    public String oculos_de_seguranca_lentes_incolor;

    @ColumnInfo(name = "oculos_de_seguranca_lentes_fume")
    public String oculos_de_seguranca_lentes_fume;

    @ColumnInfo(name = "har_habilitacao_de_acesso_a_rede")
    public String har_habilitacao_de_acesso_a_rede;

    @ColumnInfo(name = "perneira_tipo_canavieiro")
    public String perneira_tipo_canavieiro;

}
