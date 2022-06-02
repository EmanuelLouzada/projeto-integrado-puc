package com.tees.checklist.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.tees.checklist.commons.TableNames;
import com.tees.checklist.data.converter.DateConverter;

import java.util.Date;

@Entity(tableName = TableNames.INSPECAO_APR)
public class InspecaoAPR {

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

    @ColumnInfo(name = "ts_sincronizacao")
    @TypeConverters(DateConverter.class)
    public Date ts_sincronizacao;

    @ColumnInfo(name = "fk_id_servidor")
    public Integer fk_id_servidor;

    @ColumnInfo(name = "ose_oss")
    public String ose_oss;

    @ColumnInfo(name = "ost")
    public String ost;

    @ColumnInfo(name = "trabalho_a_quente")
    @NonNull
    public String trabalho_a_quente;

    @ColumnInfo(name = "icamento")
    @NonNull
    public String icamento;

    @ColumnInfo(name = "trabalho_em_altura")
    @NonNull
    public String trabalho_em_altura;

    @ColumnInfo(name = "ambientes_confinados")
    @NonNull
    public String ambientes_confinados;

    @ColumnInfo(name = "subs_quimica_perigosa")
    @NonNull
    public String subs_quimica_perigosa;

    @ColumnInfo(name = "explosivos")
    public String explosivos;

    @ColumnInfo(name = "media_e_alta_tensao_NR_10")
    @NonNull
    public String media_e_alta_tensao_NR_10;

    @ColumnInfo(name = "escavacao_acima_de_05m")
    @NonNull
    public String escavacao_acima_de_05m;

    @ColumnInfo(name = "manutencao_em_equip_energizado")
    @NonNull
    public String manutencao_em_equip_energizado;

    @ColumnInfo(name = "numeracao_operativa_de_equipamento")
    @NonNull
    public String numeracao_operativa_de_equipamento;

    @ColumnInfo(name = "num_operativa_em_equipamentos_energizados")
    @NonNull
    public String num_operativa_em_equipamentos_energizados;

    @ColumnInfo(name = "manutencao_em_equip_desenergizado")
    @NonNull
    public String manutencao_em_equip_desenergizado;

    @ColumnInfo(name = "coordenacao_de_manobra_in_loco")
    @NonNull
    public String coordenacao_de_manobra_in_loco;

    @ColumnInfo(name = "levantamento_de_dados_em_area_energizada")
    @NonNull
    public String levantamento_de_dados_em_area_energizada;

    @ColumnInfo(name = "medicao_de_sequencia_de_fases_em_equip_energ")
    @NonNull
    public String medicao_de_sequencia_de_fases_em_equip_energ;

    @ColumnInfo(name = "manut_quadro_comando_energizado")
    @NonNull
    public String manut_quadro_comando_energizado;

    @ColumnInfo(name = "manut_quadro_comando_denergizado")
    @NonNull
    public String manut_quadro_comando_denergizado;

    @ColumnInfo(name = "manut_grupo_gerador_diesel")
    @NonNull
    public String manut_grupo_gerador_diesel;

    @ColumnInfo(name = "realizacao_de_testes_funcionais_operacionais_de_equipamento")
    @NonNull
    public String realizacao_de_testes_funcionais_operacionais_de_equipamento;

    @ColumnInfo(name = "outros_ver_matriz_de_risco")
    @NonNull
    public String outros_ver_matriz_de_risco;

    @ColumnInfo(name = "planilha_risco_consultada")
    public String planilha_risco_consultada;

    @ColumnInfo(name = "de_comentario")
    public String de_comentario;

    @ColumnInfo(name = "de_outro_controle")
    public String de_outro_controle;

    @ColumnInfo(name = "solicitante")
    public String solicitante;

    @ColumnInfo(name = "solicitante_area")
    public String solicitante_area;

    @ColumnInfo(name = "resp1")
    public String resp1;

    @ColumnInfo(name = "resp1_area")
    public String resp1_area;

    @ColumnInfo(name = "resp2")
    public String resp2;

    @ColumnInfo(name = "resp2_area")
    public String resp2_area;

    @ColumnInfo(name = "ativ1_executar")
    public String ativ1_executar;

    @ColumnInfo(name = "dt_ativ1")
    public String dt_ativ1;

    @ColumnInfo(name = "inicio_ativ1")
    public String inicio_ativ1;

    @ColumnInfo(name = "termino_ativ1")
    public String termino_ativ1;

    @ColumnInfo(name = "ativ2_executar")
    public String ativ2_executar;

    @ColumnInfo(name = "dt_ativ2")
    public String dt_ativ2;

    @ColumnInfo(name = "inicio_ativ2")
    public String inicio_ativ2;

    @ColumnInfo(name = "termino_ativ2")
    public String termino_ativ2;

    }
