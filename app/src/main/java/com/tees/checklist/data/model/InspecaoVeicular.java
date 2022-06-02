package com.tees.checklist.data.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.tees.checklist.commons.TableNames;
import com.tees.checklist.data.converter.DateConverter;

import java.util.Date;

@Entity(tableName = TableNames.INSPECAO_VEICULAR)
public class InspecaoVeicular {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NonNull
    public int id;

    @ColumnInfo(name = "ts_inspecao")
    @NonNull
    @TypeConverters(DateConverter.class)
    public Date ts_inspecao;

    @ColumnInfo(name = "fk_id_usuario")
    @NonNull
    public Integer fk_id_usuario;

    @ColumnInfo(name = "fk_id_funcionario")
    @NonNull
    public String fk_id_funcionario;

    @ColumnInfo(name = "fk_id_veiculo")
    @NonNull
    public String fk_id_veiculo;

    @ColumnInfo(name = "ts_cadastro")
    @NonNull
    @TypeConverters(DateConverter.class)
    public Date ts_cadastro;

    @ColumnInfo(name = "ts_sincronizacao")
    @TypeConverters(DateConverter.class)
    public Date ts_sincronizacao;

    @ColumnInfo(name = "fk_id_servidor")
    public Integer fk_id_servidor;

    @ColumnInfo(name = "nu_km")
    @NonNull
    public String nu_km;

    @ColumnInfo(name = "bancos_do_veiculo")
    public String bancos_do_veiculo;

    @ColumnInfo(name = "camera_interna")
    public String camera_interna;

    @ColumnInfo(name = "camera_externa")
    public String camera_externa;

    @ColumnInfo(name = "limpeza_do_veiculo")
    public String limpeza_do_veiculo;

    @ColumnInfo(name = "motor")
    public String motor;

    @ColumnInfo(name = "embreagem")
    public String embreagem;

    @ColumnInfo(name = "freio")
    public String freio;

    @ColumnInfo(name = "nivel_oleo_motor")
    public String nivel_oleo_motor;

    @ColumnInfo(name = "nivel_oleo_freio")
    public String nivel_oleo_freio;

    @ColumnInfo(name = "pneus")
    public String pneus;

    @ColumnInfo(name = "estepe")
    public String estepe;

    @ColumnInfo(name = "pedais")
    public String pedais;

    @ColumnInfo(name = "chave_de_roda")
    public String chave_de_roda;

    @ColumnInfo(name = "escapamento")
    public String escapamento;

    @ColumnInfo(name = "freio_de_mao")
    public String freio_de_mao;

    @ColumnInfo(name = "macaco")
    public String macaco;

    @ColumnInfo(name = "ferois")
    public String ferois;

    @ColumnInfo(name = "setas")
    public String setas;

    @ColumnInfo(name = "lanternas")
    public String lanternas;

    @ColumnInfo(name = "luz_de_freio")
    public String luz_de_freio;

    @ColumnInfo(name = "pisca_alerta")
    public String pisca_alerta;

    @ColumnInfo(name = "lampadas_inter")
    public String lampadas_inter;

    @ColumnInfo(name = "luz_de_re")
    public String luz_de_re;

    @ColumnInfo(name = "sinal_sonoro_de_re")
    public String sinal_sonoro_de_re;

    @ColumnInfo(name = "luz_alta")
    public String luz_alta;

    @ColumnInfo(name = "luz_baixa")
    public String luz_baixa;

    @ColumnInfo(name = "painel")
    public String painel;

    @ColumnInfo(name = "portas_e_travas")
    public String portas_e_travas;

    @ColumnInfo(name = "buzina")
    public String buzina;

    @ColumnInfo(name = "cinto_de_seguranca")
    public String cinto_de_seguranca;

    @ColumnInfo(name = "extintor_de_incendio")
    public String extintor_de_incendio;

    @ColumnInfo(name = "triangulo_sinalizador")
    public String triangulo_sinalizador;

    @ColumnInfo(name = "espelho_retrovisor_externo")
    public String espelho_retrovisor_externo;

    @ColumnInfo(name = "espelho_retrovisor_interno")
    public String espelho_retrovisor_interno;

    @ColumnInfo(name = "para_choque")
    public String para_choque;

    @ColumnInfo(name = "limpador_para_brisa")
    public String limpador_para_brisa;

    @ColumnInfo(name = "suspensao")
    public String suspensao;

    @ColumnInfo(name = "alinhamento_balanceamento")
    public String alinhamento_balanceamento;

    @ColumnInfo(name = "placa_dianteira")
    public String placa_dianteira;

    @ColumnInfo(name = "placa_traseira")
    public String placa_traseira;

    @ColumnInfo(name = "ar_condicionado")
    public String ar_condicionado;

    @ColumnInfo(name = "agua_arrefecimento")
    public String agua_arrefecimento;

    @ColumnInfo(name = "velocimetro")
    public String velocimetro;

    @ColumnInfo(name = "vidros")
    public String vidros;

    @ColumnInfo(name = "revisoes")
    public String revisoes;

}
