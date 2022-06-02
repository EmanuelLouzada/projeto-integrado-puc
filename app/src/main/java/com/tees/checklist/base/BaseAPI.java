package com.tees.checklist.base;

import com.tees.checklist.api.request.DefaultDataRequest;
import com.tees.checklist.api.request.UsuarioRequest;
import com.tees.checklist.data.model.AnalisePessoal;
import com.tees.checklist.data.model.Atividade;
import com.tees.checklist.data.model.Configuracoes;
import com.tees.checklist.data.model.Diretoria;
import com.tees.checklist.data.model.DiretoriaAtividade;
import com.tees.checklist.data.model.EPISConvencionais;
import com.tees.checklist.data.model.EPISEspecificos;
import com.tees.checklist.data.model.Funcionario;
import com.tees.checklist.data.model.InspecaoAPR;
import com.tees.checklist.data.model.InspecaoEPC;
import com.tees.checklist.data.model.InspecaoEPI;
import com.tees.checklist.data.model.InspecaoVeicular;
import com.tees.checklist.data.model.Localidade;
import com.tees.checklist.data.model.RiscoSeguranca;
import com.tees.checklist.data.model.Usuario;
import com.tees.checklist.data.model.Veiculo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

public interface BaseAPI {


    @GET("CarregarAnalisePessoal")
    Call<List<AnalisePessoal>> loadAnalisePessoal(@Body DefaultDataRequest data);

    @GET("CarregarAtividade")
    Call<List<Atividade>> loadAtividade(@Body DefaultDataRequest data);

    @GET("CarregarConfiguracoes")
    Call<List<Configuracoes>> loadConfiguracoes(@Body DefaultDataRequest data);

    @GET("CarregarDiretoria")
    Call<List<Diretoria>> loadDiretoria(@Body DefaultDataRequest data);

    @GET("CarregarDiretoriaAtividade")
    Call<List<DiretoriaAtividade>> loadDiretoriaAtividade(@Body DefaultDataRequest data);

    @GET("CarregarEPISConvencionais")
    Call<List<EPISConvencionais>> loadEPISConvencionais(@Body DefaultDataRequest data);

    @GET("CarregarEPISEspecificos")
    Call<List<EPISEspecificos>> loadEPISEspecificos(@Body DefaultDataRequest data);

    @GET("CarregarFuncionario")
    Call<List<Funcionario>> loadFuncionario(@Body DefaultDataRequest data);

    @GET("CarregarInspecaoAPR")
    Call<List<InspecaoAPR>> loadInspecaoAPR(@Body DefaultDataRequest data);

    @GET("CarregarInspecaoEPC")
    Call<List<InspecaoEPC>> loadInspecaoEPC(@Body DefaultDataRequest data);

    @GET("CarregarInspecaoEPI")
    Call<List<InspecaoEPI>> loadInspecaoEPI(@Body DefaultDataRequest data);

    @GET("CarregarInspecaoVeicular")
    Call<List<InspecaoVeicular>> loadInspecaoVeicular(@Body DefaultDataRequest data);

    @GET("CarregarLocalidade")
    Call<List<Localidade>> loadLocalidade(@Body DefaultDataRequest data);

    @GET("CarregarRiscoSeguranca")
    Call<List<RiscoSeguranca>> loadRiscoSeguranca(@Body DefaultDataRequest data);

    @GET("CarregarUsuario")
    Call<List<Usuario>> loadUsuario(@Body UsuarioRequest data);

    @GET("CarregarVeiculo")
    Call<List<Veiculo>> loadVeiculo(@Body DefaultDataRequest data);

}
