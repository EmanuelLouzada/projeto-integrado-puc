package com.tees.checklist.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.tees.checklist.commons.TableNames;
import com.tees.checklist.data.converter.DateConverter;

import java.util.Date;

@Entity(tableName = TableNames.RISCO_SEGURANCA)
public class RiscoSeguranca {

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

    @ColumnInfo(name = "quedas_pessoais")
    @NonNull
    public String quedas_pessoais;

    @ColumnInfo(name = "qp_utilizar_cinto_de_seguranca")
    public String qp_utilizar_cinto_de_seguranca;

    @ColumnInfo(name = "qp_utilizar_corrimao")
    public String qp_utilizar_corrimao;

    @ColumnInfo(name = "qp_outros")
    public String qp_outros;

    @ColumnInfo(name = "qp_utilizar_cabo_guia")
    public String qp_utilizar_cabo_guia;

    @ColumnInfo(name = "qp_utilizar_trava_quedas")
    public String qp_utilizar_trava_quedas;

    @ColumnInfo(name = "qp_corrigir_local_escorregadio")
    public String qp_corrigir_local_escorregadio;

    @ColumnInfo(name = "queda_material")
    public String queda_material;

    @ColumnInfo(name = "qm_definir_a_zona_controlada_e_seguranca")
    public String qm_definir_a_zona_controlada_e_seguranca;

    @ColumnInfo(name = "qm_amarrar_material")
    public String qm_amarrar_material;

    @ColumnInfo(name = "qm_elaborar_apt")
    public String qm_elaborar_apt;

    @ColumnInfo(name = "qm_isolar_area")
    public String qm_isolar_area;

    @ColumnInfo(name = "qm_outros")
    public String qm_outros;

    @ColumnInfo(name = "projecao_de_material_vibracao")
    public String projecao_de_material_vibracao;

    @ColumnInfo(name = "pm_vibracao_oculos_de_seguranca")
    public String pm_vibracao_oculos_de_seguranca;

    @ColumnInfo(name = "pm_vibracao_protetor_facial")
    public String pm_vibracao_protetor_facial;

    @ColumnInfo(name = "pm_vibracao_isolar_area")
    public String pm_vibracao_isolar_area;

    @ColumnInfo(name = "pm_vibracao_outros")
    public String pm_vibracao_outros;

    @ColumnInfo(name = "alta_pressao")
    public String alta_pressao;

    @ColumnInfo(name = "ap_bloqueio_pneumatico")
    public String ap_bloqueio_pneumatico;

    @ColumnInfo(name = "ap_bloqueio_hidraulico")
    public String ap_bloqueio_hidraulico;

    @ColumnInfo(name = "ap_utilizar_epi_especial")
    public String ap_utilizar_epi_especial;

    @ColumnInfo(name = "ap_outros")
    public String ap_outros;

    @ColumnInfo(name = "colisao")
    public String colisao;

    @ColumnInfo(name = "c_sinalizar_area")
    public String c_sinalizar_area;

    @ColumnInfo(name = "c_check_list_do_veiculo")
    public String c_check_list_do_veiculo;

    @ColumnInfo(name = "c_dirigir_equipamento")
    public String c_dirigir_equipamento;

    @ColumnInfo(name = "c_motorizado")
    public String c_motorizado;

    @ColumnInfo(name = "choque_contra")
    public String choque_contra;

    @ColumnInfo(name = "cc_protecao_para_partes_moveis")
    public String cc_protecao_para_partes_moveis;

    @ColumnInfo(name = "cc_bloqueio_de_equipamentos_auxiliares_bloqueio_mecanico")
    public String cc_bloqueio_de_equipamentos_auxiliares_bloqueio_mecanico;

    @ColumnInfo(name = "ergonomia")
    public String ergonomia;

    @ColumnInfo(name = "e_alongamento_ginastica_elaboral")
    public String e_alongamento_ginastica_elaboral;

    @ColumnInfo(name = "e_ergonomia")
    public String e_ergonomia;

    @ColumnInfo(name = "e_outros")
    public String e_outros;

    @ColumnInfo(name = "fadiga")
    public String fadiga;

    @ColumnInfo(name = "f_revezamento")
    public String f_revezamento;

    @ColumnInfo(name = "f_pausa_para_descanso")
    public String f_pausa_para_descanso;

    @ColumnInfo(name = "f_outros")
    public String f_outros;

    @ColumnInfo(name = "ataque_de_animais")
    public String ataque_de_animais;

    @ColumnInfo(name = "aa_inspecionar_a_area")
    public String aa_inspecionar_a_area;

    @ColumnInfo(name = "aa_providenciar_medidas_acoes_adequeadas")
    public String aa_providenciar_medidas_acoes_adequeadas;

    @ColumnInfo(name = "iluminacao_deficiente_ou_excesso")
    public String iluminacao_deficiente_ou_excesso;

    @ColumnInfo(name = "ide_instalar_iluminacao_adequada")
    public String ide_instalar_iluminacao_adequada;

    @ColumnInfo(name = "ide_solicitar_substituicao_de_lampadas_queimadas")
    public String ide_solicitar_substituicao_de_lampadas_queimadas;

    @ColumnInfo(name = "ide_outros")
    public String ide_outros;

    @ColumnInfo(name = "poeira")
    public String poeira;

    @ColumnInfo(name = "p_utilizar_respirador_contra_poeira")
    public String p_utilizar_respirador_contra_poeira;

    @ColumnInfo(name = "p_utilizar_oculos_ampla_visao")
    public String p_utilizar_oculos_ampla_visao;

    @ColumnInfo(name = "p_outros")
    public String p_outros;

    @ColumnInfo(name = "insolacao")
    public String insolacao;

    @ColumnInfo(name = "i_utilizar_vestimenta_de_manga_longa")
    public String i_utilizar_vestimenta_de_manga_longa;

    @ColumnInfo(name = "i_protetor_solar")
    public String i_protetor_solar;

    @ColumnInfo(name = "i_outros")
    public String i_outros;

    @ColumnInfo(name = "desabamento_soterramento")
    public String desabamento_soterramento;

    @ColumnInfo(name = "ds_fazer_escoramento")
    public String ds_fazer_escoramento;

    @ColumnInfo(name = "ds_elaborar_apt_escavacao")
    public String ds_elaborar_apt_escavacao;

    @ColumnInfo(name = "ds_outros")
    public String ds_outros;

    @ColumnInfo(name = "gases_e_fumos")
    public String gases_e_fumos;

    @ColumnInfo(name = "gf_utilizar_mascara_pff2_para_soldadores")
    public String gf_utilizar_mascara_pff2_para_soldadores;

    @ColumnInfo(name = "gf_utilizar_mascara_com_filtro_p_gases")
    public String gf_utilizar_mascara_com_filtro_p_gases  ;

    @ColumnInfo(name = "gf_outros")
    public String gf_outros;

    @ColumnInfo(name = "gf_utilizar_sistema_de_exaustao")
    public String gf_utilizar_sistema_de_exaustao;

    @ColumnInfo(name = "ruidos")
    public String ruidos;

    @ColumnInfo(name = "r_protetor_auditivo")
    public String r_protetor_auditivo;

    @ColumnInfo(name = "r_outros")
    public String r_outros;

    @ColumnInfo(name = "substancias_toxicas")
    public String substancias_toxicas;

    @ColumnInfo(name = "st_utilizar_resp_c_filtro_p_produtos_quimicos")
    public String st_utilizar_resp_c_filtro_p_produtos_quimicos;

    @ColumnInfo(name = "st_utilizar_oculos_ampla_visao")
    public String st_utilizar_oculos_ampla_visao;

    @ColumnInfo(name = "st_higienizacao_desinfeccao")
    public String st_higienizacao_desinfeccao;

    @ColumnInfo(name = "st_utilizar_creme_de_protecao")
    public String st_utilizar_creme_de_protecao;

    @ColumnInfo(name = "st_utilizar_macacao_em_tnt")
    public String st_utilizar_macacao_em_tnt;

    @ColumnInfo(name = "st_utlizar_luvas")
    public String st_utlizar_luvas;

    @ColumnInfo(name = "incendio_e_explosao")
    public String incendio_e_explosao;

    @ColumnInfo(name = "ie_proteger_mat_combustivel")
    public String ie_proteger_mat_combustivel;

    @ColumnInfo(name = "ie_rota_de_evacuacao")
    public String ie_rota_de_evacuacao;

    @ColumnInfo(name = "ie_existe_extintor_adequado")
    public String ie_existe_extintor_adequado;

    @ColumnInfo(name = "ie_inst_extintor_adequado")
    public String ie_inst_extintor_adequado;

    @ColumnInfo(name = "asfixia")
    public String asfixia;

    @ColumnInfo(name = "a_instalar_ventilador_exaustao")
    public String a_instalar_ventilador_exaustao;

    @ColumnInfo(name = "a_utilizar_mascara_autonoma")
    public String a_utilizar_mascara_autonoma;

    @ColumnInfo(name = "a_medicao_de_nivel_de_oxigenio")
    public String a_medicao_de_nivel_de_oxigenio;

    @ColumnInfo(name = "a_outros")
    public String a_outros;

    @ColumnInfo(name = "agarramento_presamento_de_membros")
    public String agarramento_presamento_de_membros;

    @ColumnInfo(name = "agpm_barreiras_de_protecao")
    public String agpm_barreiras_de_protecao;

    @ColumnInfo(name = "agpm_manter_distancia_segura")
    public String agpm_manter_distancia_segura;

    @ColumnInfo(name = "agpm_epis_adequados")
    public String agpm_epis_adequados;

    @ColumnInfo(name = "agpm_outros")
    public String agpm_outros;

    @ColumnInfo(name = "queimadura_a_quente")
    public String queimadura_a_quente;

    @ColumnInfo(name = "qq_ultizar_epi_proprio_para_calor")
    public String qq_ultizar_epi_proprio_para_calor;

    @ColumnInfo(name = "qq_solicitar_medicao_de_calor")
    public String qq_solicitar_medicao_de_calor;

    @ColumnInfo(name = "qq_elaborar_apt_trabalho_a_quente")
    public String qq_elaborar_apt_trabalho_a_quente;

    @ColumnInfo(name = "qq_aguardar_resfriamento")
    public String qq_aguardar_resfriamento;

    @ColumnInfo(name = "qq_utilizar_avental_de_couro")
    public String qq_utilizar_avental_de_couro;

    @ColumnInfo(name = "qq_utilizar_perneira_de_couro")
    public String qq_utilizar_perneira_de_couro;

    @ColumnInfo(name = "vazamento_derramamentos")
    public String vazamento_derramamentos;

    @ColumnInfo(name = "vd_providenciar_barreiras_de_contencao")
    public String vd_providenciar_barreiras_de_contencao;

    @ColumnInfo(name = "vd_providenciar_po_de_serragem")
    public String vd_providenciar_po_de_serragem;

    @ColumnInfo(name = "vd_solicitar_apoio_da_brigada")
    public String vd_solicitar_apoio_da_brigada;

    @ColumnInfo(name = "vd_outros")
    public String vd_outros;

    @ColumnInfo(name = "choque_eletrico")
    public String choque_eletrico;

    @ColumnInfo(name = "ce_desenergizar_bloqueio_eletrico")
    public String ce_desenergizar_bloqueio_eletrico;

    @ColumnInfo(name = "ce_utilizar_epis_especificos_nr10")
    public String ce_utilizar_epis_especificos_nr10;

    @ColumnInfo(name = "ce_diagrama_unifilar_de_operacao_atualizado")
    public String ce_diagrama_unifilar_de_operacao_atualizado;

    @ColumnInfo(name = "ce_instrucoes_de_operacao_atualizadas")
    public String ce_instrucoes_de_operacao_atualizadas;

    @ColumnInfo(name = "ce_sequencimetro_adequado")
    public String ce_sequencimetro_adequado;

    @ColumnInfo(name = "ce_definicao_das_zonas_de_controle")
    public String ce_definicao_das_zonas_de_controle;

    @ColumnInfo(name = "ce_ferramentas_adequadas")
    public String ce_ferramentas_adequadas;

    @ColumnInfo(name = "ce_outros")
    public String ce_outros;

    @ColumnInfo(name = "outros")
    public String outros;
}
