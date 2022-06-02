package com.tees.checklist.ui.screens.homeAPR;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.tabs.TabLayout;
import com.tees.checklist.R;
import com.tees.checklist.commons.Messages;
import com.tees.checklist.commons.Preferences;
import com.tees.checklist.data.model.AnalisePessoal;
import com.tees.checklist.data.model.Atividade;
import com.tees.checklist.data.model.EPISConvencionais;
import com.tees.checklist.data.model.EPISEspecificos;
import com.tees.checklist.data.model.Funcionario;
import com.tees.checklist.data.model.InspecaoAPR;
import com.tees.checklist.data.model.RiscoSeguranca;
import com.tees.checklist.databinding.FragmentHomeAprBinding;
import com.tees.checklist.ui.Injection;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeAPRFragment extends Fragment {

    private HomeAPRViewModel mViewModel;
    private HomeAPRViewModelFactory mViewModelFactory;
    private FragmentHomeAprBinding binding;
    private Context mContext;
    public Preferences preferences;
    private ArrayList<Integer> funcionariosIds = new ArrayList<>();
    AlertDialog dialog;


    public static HomeAPRFragment newInstance() {
        return new HomeAPRFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_home_apr,
                container,
                false);

        toolBarConfig();
        tabconfig();
        setListeners();
        return binding.getRoot();
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        mViewModelFactory = Injection.provideHomeAPRViewModelFactory(mContext);
        mViewModel = new ViewModelProvider(this, mViewModelFactory).get(HomeAPRViewModel.class);

    }

    private void toolBarConfig() {
        Toolbar top_app_bar = binding.toolbar.findViewById(R.id.topAppBar);
        top_app_bar.setTitle(R.string.homeAPR_screen_name);
        top_app_bar.setNavigationOnClickListener(
                view -> {
                    final NavController navController = Navigation.findNavController(view);
                    navController.popBackStack();
                }
        );
    }

    private void setVisibleGone() {
        binding.include1.getRoot().setVisibility(View.GONE);
        binding.include2.getRoot().setVisibility(View.GONE);
        binding.include3.getRoot().setVisibility(View.GONE);
        binding.include4.getRoot().setVisibility(View.GONE);
        binding.include5.getRoot().setVisibility(View.GONE);
        binding.include6.getRoot().setVisibility(View.GONE);
    }

    private void tabconfig() {

        binding.tabLayout2.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        setVisibleGone();
                        binding.include1.getRoot().setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        setVisibleGone();
                        binding.include2.getRoot().setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        setVisibleGone();
                        binding.include3.getRoot().setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        setVisibleGone();
                        binding.include4.getRoot().setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        setVisibleGone();
                        binding.include5.getRoot().setVisibility(View.VISIBLE);
                        break;
                    case 5:
                        setVisibleGone();
                        binding.include6.getRoot().setVisibility(View.VISIBLE);
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void  populateFuncionarios(List<Funcionario> funcionarios){
        Spinner sItem1 = binding.include4.spinnerSolicitante;
        Spinner sItem2 = binding.include4.spinnerReponsavel1;
        Spinner sItem3 = binding.include4.spinnerReponsavel2;
        Spinner sItem4 = binding.include4.spinnerExecutante1;
        Spinner sItem5 = binding.include4.spinnerExecutante2;

        List<String> spinnerArray = new ArrayList<>();
        for (Funcionario funcionario:funcionarios) {
            spinnerArray.add(funcionario.no_funcionario + " - " + funcionario.co_cnh + " - " + funcionario.dt_validade_cnh );
            funcionariosIds.add(funcionario.id);

        }
        if (spinnerArray.size()>0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
            sItem1.setAdapter(adapter);
            sItem2.setAdapter(adapter);
            sItem3.setAdapter(adapter);
            sItem4.setAdapter(adapter);
            sItem5.setAdapter(adapter);
        }
    }

    private void  populateAtividades(List<Atividade> atividades){
        Spinner sItem = binding.include4.spinnerAtividadeAExecutar;
        Spinner sItem2 = binding.include4.spinnerAtividadeAExecutar2;
        List<String> spinnerArray =  new ArrayList<String>();
        for (Atividade atividade:atividades ) {
            spinnerArray.add(atividade.no_atividade);
        }
        if (spinnerArray.size()>0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
            sItem.setAdapter(adapter);
            sItem2.setAdapter(adapter);
        }
    }

    private void populateAdapter(List<Funcionario> list) {
        populateFuncionarios(list);

    }

    private void setListeners() {
        mViewModel.getResultAdapter().observe(this, result -> {
            if (result == null) {
                return ;
            }
            populateAdapter(result);
        });

        mViewModel.getErrorAdapter().observe(this, result -> {
            if (result != null) {
                new MaterialAlertDialogBuilder(requireContext(), R.style.StyledThemeOverlay_MaterialComponents_MaterialAlertDialog)
                        .setTitle(R.string.error_message)
                        .setMessage(result)
                        .setPositiveButton(android.R.string.ok, null)
                        .setIcon(R.drawable.ic_error_24)
                        .show();

            }

        });




        binding.include6.buttonSave.setOnClickListener(
                view -> save());


    }

    public String getValueFromSpinner(int position){
        String[] array = getResources().getStringArray(R.array.values_sim_nao);
        return array[position];
    }

    public boolean validate(){
        return true;
    }

    public void save() {

        RiscoSeguranca riscoSeguranca = new RiscoSeguranca();

        //riscoSeguranca.fk_id_inspecao_apr=String.valueOf();
        //riscoSeguranca.fk_id_servidor=String.valueOf();
        riscoSeguranca.ts_sincronizacao = new Date();
        riscoSeguranca.ts_cadastro= new Date();;
        riscoSeguranca.alta_pressao = getValueFromSpinner(binding.include2.spinnerRiscoAltaPressao.getSelectedItemPosition());
        riscoSeguranca.vazamento_derramamentos = getValueFromSpinner(binding.include2.spinnerVazamentosDerramamentos.getSelectedItemPosition());
        riscoSeguranca.substancias_toxicas = getValueFromSpinner(binding.include2.spinnerSubstanciasToxicas.getSelectedItemPosition());
        riscoSeguranca.ruidos = getValueFromSpinner(binding.include2.spinnerRuidos.getSelectedItemPosition());
        riscoSeguranca.queimadura_a_quente = getValueFromSpinner(binding.include2.spinnerQuimaduraQuente.getSelectedItemPosition());
        riscoSeguranca.quedas_pessoais = getValueFromSpinner(binding.include2.spinnerRiscoQuedaPessoal.getSelectedItemPosition());
        riscoSeguranca.queda_material = getValueFromSpinner(binding.include2.spinnerRiscoQuedaMaterial.getSelectedItemPosition());
        riscoSeguranca.poeira = getValueFromSpinner(binding.include2.spinnerPoeira.getSelectedItemPosition());
        riscoSeguranca.insolacao = getValueFromSpinner(binding.include2.spinnerInsolacao.getSelectedItemPosition());
        riscoSeguranca.outros = getValueFromSpinner(binding.include2.spinnerOutros.getSelectedItemPosition());
        riscoSeguranca.projecao_de_material_vibracao = getValueFromSpinner(binding.include2.spinnerRiscoProjecaoMaterialVibracao.getSelectedItemPosition());
        riscoSeguranca.incendio_e_explosao = getValueFromSpinner(binding.include2.spinnerIncendioExplosao.getSelectedItemPosition());
        riscoSeguranca.iluminacao_deficiente_ou_excesso = getValueFromSpinner(binding.include2.spinnerRiscoIluminacaoDeficienteExcesso.getSelectedItemPosition());
        riscoSeguranca.gases_e_fumos = getValueFromSpinner(binding.include2.spinnerGasesFumos.getSelectedItemPosition());
        riscoSeguranca.fadiga = getValueFromSpinner(binding.include2.spinnerRiscoFadiga.getSelectedItemPosition());
        riscoSeguranca.ergonomia = getValueFromSpinner(binding.include2.spinnerRiscoErgonomia.getSelectedItemPosition());
        riscoSeguranca.colisao = getValueFromSpinner(binding.include2.spinnerRiscoColisao.getSelectedItemPosition());
        riscoSeguranca.desabamento_soterramento = getValueFromSpinner(binding.include2.spinnerDesabamentoSoterramento.getSelectedItemPosition());
        riscoSeguranca.choque_eletrico = getValueFromSpinner(binding.include2.spinnerChoqueEletrico.getSelectedItemPosition());
        riscoSeguranca.choque_contra = getValueFromSpinner(binding.include2.spinnerRiscoChoqueContra.getSelectedItemPosition());
        riscoSeguranca.asfixia = getValueFromSpinner(binding.include2.spinnerAsfixia.getSelectedItemPosition());
        riscoSeguranca.choque_eletrico = getValueFromSpinner(binding.include2.spinnerChoqueEletrico.getSelectedItemPosition());
        riscoSeguranca.agarramento_presamento_de_membros = getValueFromSpinner(binding.include2.spinnerAgarramentoPrensamentoMembros.getSelectedItemPosition());
        riscoSeguranca.ataque_de_animais = getValueFromSpinner(binding.include2.spinnerRiscoAtaqueAnimais.getSelectedItemPosition());
        riscoSeguranca.a_instalar_ventilador_exaustao = binding.include2.checkBoxInstalarVentilacaoExaustao.isChecked() ? "S" : "N";
        riscoSeguranca.a_medicao_de_nivel_de_oxigenio = binding.include2.checkBoxMedicaoNivelOxigenio.isChecked() ? "S" : "N";
        riscoSeguranca.a_outros = binding.include2.checkBoxOutrosAsfixia.isChecked() ? "S" : "N";
        riscoSeguranca.a_utilizar_mascara_autonoma = binding.include2.checkBoxUtilizarMascaraAutonoma.isChecked() ? "S" : "N";
        riscoSeguranca.aa_inspecionar_a_area = binding.include2.checkBoxInspecionarArea.isChecked() ? "S" : "N";
        riscoSeguranca.aa_providenciar_medidas_acoes_adequeadas = binding.include2.checkBoxProvidenciarMedidasAcoesAdequadas.isChecked() ? "S" : "N";
        riscoSeguranca.agarramento_presamento_de_membros = binding.include2.checkBoxOutrosAgarramentoPrensamento.isChecked() ? "S" : "N";
        riscoSeguranca.agpm_barreiras_de_protecao = binding.include2.checkBoxBarreirasProtecao.isChecked() ? "S" : "N";
        riscoSeguranca.agpm_epis_adequados = binding.include2.checkBoxEpisAdequados.isChecked() ? "S" : "N";
        riscoSeguranca.agpm_manter_distancia_segura = binding.include2.checkBoxManterDistanciaSegura.isChecked() ? "S" : "N";
        riscoSeguranca.agpm_outros = binding.include2.checkBoxOutrosAgarramentoPrensamento.isChecked() ? "S" : "N";
        riscoSeguranca.ap_bloqueio_hidraulico = binding.include2.checkBoxBloqueioHidraulico.isChecked() ? "S" : "N";
        riscoSeguranca.ap_bloqueio_pneumatico = binding.include2.checkBoxBloqueioPneumatico.isChecked() ? "S" : "N";
        riscoSeguranca.ap_utilizar_epi_especial = binding.include2.checkBoxUtilizarEPIEspecial.isChecked() ? "S" : "N";
        riscoSeguranca.ap_outros = binding.include2.checkBoxOutrosAltaPressao.isChecked() ? "S" : "N";
        riscoSeguranca.c_check_list_do_veiculo = binding.include2.checkBoxChecklistVeiculo.isChecked() ? "S" : "N";
        riscoSeguranca.c_dirigir_equipamento = binding.include2.checkBoxAutDirigirEquipamento.isChecked() ? "S" : "N";
        riscoSeguranca.c_motorizado = binding.include2.checkBoxMotorizado.isChecked() ? "S" : "N";
        riscoSeguranca.c_sinalizar_area = binding.include2.checkBoxSinalizarArea.isChecked() ? "S" : "N";
        riscoSeguranca.cc_bloqueio_de_equipamentos_auxiliares_bloqueio_mecanico = binding.include2.checkBoxBloqueioEquipamentosAuxiliares.isChecked() ? "S" : "N";
        riscoSeguranca.cc_protecao_para_partes_moveis = binding.include2.checkBoxProtecaoPartesMoveis.isChecked() ? "S" : "N";
        riscoSeguranca.c_dirigir_equipamento = binding.include2.checkBoxAutDirigirEquipamento.isChecked() ? "S" : "N";
        riscoSeguranca.c_motorizado = binding.include2.checkBoxMotorizado.isChecked() ? "S" : "N";
        riscoSeguranca.c_sinalizar_area = binding.include2.checkBoxSinalizarArea.isChecked() ? "S" : "N";
        riscoSeguranca.cc_bloqueio_de_equipamentos_auxiliares_bloqueio_mecanico = binding.include2.checkBoxBloqueioEquipamentosAuxiliares.isChecked() ? "S" : "N";
        riscoSeguranca.c_sinalizar_area = binding.include2.checkBoxSinalizarArea.isChecked() ? "S" : "N";
        riscoSeguranca.cc_bloqueio_de_equipamentos_auxiliares_bloqueio_mecanico = binding.include2.checkBoxBloqueioEquipamentosAuxiliares.isChecked() ? "S" : "N";
        riscoSeguranca.cc_protecao_para_partes_moveis = binding.include2.checkBoxProtecaoPartesMoveis.isChecked() ? "S" : "N";
        riscoSeguranca.ce_definicao_das_zonas_de_controle = binding.include2.checkBoxDefinicoesZonasControle.isChecked() ? "S" : "N";
        riscoSeguranca.ce_desenergizar_bloqueio_eletrico = binding.include2.checkBoxDesenergizarBloqueioEletrico.isChecked() ? "S" : "N";
        riscoSeguranca.ce_diagrama_unifilar_de_operacao_atualizado = binding.include2.checkBoxDiagramaUnifilarOperacaoAtualizado.isChecked() ? "S" : "N";
        riscoSeguranca.ce_ferramentas_adequadas = binding.include2.checkBoxFerramentasAdequadas.isChecked() ? "S" : "N";
        riscoSeguranca.ce_instrucoes_de_operacao_atualizadas = binding.include2.checkBoxInstrucoesOperacoesAtualizadas.isChecked() ? "S" : "N";
        riscoSeguranca.ce_sequencimetro_adequado = binding.include2.checkBoxSquencimetroAdequado.isChecked() ? "S" : "N";
        riscoSeguranca.ce_utilizar_epis_especificos_nr10 = binding.include2.checkBoxUtilizarEPISEspecificosNR10.isChecked() ? "S" : "N";
        riscoSeguranca.ce_outros = binding.include2.checkBoxOutrosChoqueEletrico.isChecked() ? "S" : "N";
        riscoSeguranca.ds_elaborar_apt_escavacao = binding.include2.checkBoxElaborarApt.isChecked() ? "S" : "N";
        riscoSeguranca.ds_fazer_escoramento = binding.include2.checkBoxFazerEscoramento.isChecked() ? "S" : "N";
        riscoSeguranca.ds_outros = binding.include2.checkBoxOutrosDesabamentoSoterramento.isChecked() ? "S" : "N";
        riscoSeguranca.e_alongamento_ginastica_elaboral = binding.include2.checkBoxAlongamentoGinasticaLaboral.isChecked() ? "S" : "N";
        riscoSeguranca.e_ergonomia = binding.include2.checkBoxErgonomia.isChecked() ? "S" : "N";
        riscoSeguranca.e_outros = binding.include2.checkBoxOutrosErgonomia.isChecked() ? "S" : "N";
        riscoSeguranca.f_pausa_para_descanso = binding.include2.checkBoxPausaDescanso.isChecked() ? "S" : "N";
        riscoSeguranca.f_revezamento = binding.include2.checkBoxRevazemento.isChecked() ? "S" : "N";
        riscoSeguranca.f_outros = binding.include2.checkBoxOutrosFadiga.isChecked() ? "S" : "N";
        riscoSeguranca.gf_utilizar_mascara_com_filtro_p_gases = binding.include2.checkBoxUtilizarMascaraFiltroGases.isChecked() ? "S" : "N";
        riscoSeguranca.gf_utilizar_mascara_pff2_para_soldadores = binding.include2.checkBoxUtilizarMascaraPFF2Soldadores.isChecked() ? "S" : "N";
        riscoSeguranca.gf_utilizar_sistema_de_exaustao = binding.include2.checkBoxUtlilizarSistemaExaustao.isChecked() ? "S" : "N";
        riscoSeguranca.gf_outros = binding.include2.checkBoxOutrosGasesFumos.isChecked() ? "S" : "N";
        riscoSeguranca.i_utilizar_vestimenta_de_manga_longa = binding.include2.checkBoxUtilizarVestimentaMangaLonga.isChecked() ? "S" : "N";
        riscoSeguranca.i_outros = binding.include2.checkBoxOutrosInsolacao.isChecked() ? "S" : "N";
        riscoSeguranca.i_protetor_solar = binding.include2.checkBoxUtilizarProtetorSolar.isChecked() ? "S" : "N";
        riscoSeguranca.ide_instalar_iluminacao_adequada = binding.include2.checkBoxInstalarIluminacaoAdequada.isChecked() ? "S" : "N";
        riscoSeguranca.ide_solicitar_substituicao_de_lampadas_queimadas = binding.include2.checkBoxSolicitarSubsLampadasQueimadas.isChecked() ? "S" : "N";
        riscoSeguranca.ide_outros = binding.include2.checkBoxOutrosIluminacaoDeficienteExcesso.isChecked() ? "S" : "N";
        riscoSeguranca.p_utilizar_oculos_ampla_visao = binding.include2.checkBoxUtilizarOculosAmplaVisao.isChecked() ? "S" : "N";
        riscoSeguranca.p_utilizar_respirador_contra_poeira = binding.include2.checkBoxUtilizarRespiradorContraPoeira.isChecked() ? "S" : "N";
        riscoSeguranca.p_outros = binding.include2.checkBoxOutrosPoeira.isChecked() ? "S" : "N";
        riscoSeguranca.ie_existe_extintor_adequado = binding.include2.checkBoxExisteExtintorAdequado.isChecked() ? "S" : "N";
        riscoSeguranca.ie_inst_extintor_adequado = binding.include2.checkBoxInstExtintorAdequado.isChecked() ? "S" : "N";
        riscoSeguranca.ie_proteger_mat_combustivel = binding.include2.checkBoxProtegerMaterialCombustivel.isChecked() ? "S" : "N";
        riscoSeguranca.ie_rota_de_evacuacao = binding.include2.checkBoxRotaEvacuacao.isChecked() ? "S" : "N";
        riscoSeguranca.pm_vibracao_isolar_area = binding.include2.checkBoxIsolarArea.isChecked() ? "S" : "N";
        riscoSeguranca.pm_vibracao_oculos_de_seguranca = binding.include2.checkBoxOculosDeSeguranca.isChecked() ? "S" : "N";
        riscoSeguranca.pm_vibracao_protetor_facial = binding.include2.checkBoxProtetorFacial.isChecked() ? "S" : "N";
        riscoSeguranca.pm_vibracao_outros = binding.include2.checkBoxOutrosProjecao.isChecked() ? "S" : "N";
        riscoSeguranca.qm_amarrar_material = binding.include2.checkBoxAmarrarMaterial.isChecked() ? "S" : "N";
        riscoSeguranca.qm_definir_a_zona_controlada_e_seguranca = binding.include2.checkBoxDefinirZonaControladaSeguranca.isChecked() ? "S" : "N";
        riscoSeguranca.qm_elaborar_apt = binding.include2.checkBoxElaborarApt.isChecked() ? "S" : "N";
        riscoSeguranca.qm_isolar_area = binding.include2.checkBoxIsolarArea.isChecked() ? "S" : "N";
        riscoSeguranca.qm_outros = binding.include2.checkBoxOutrosQdMaterial.isChecked() ? "S" : "N";
        riscoSeguranca.qp_corrigir_local_escorregadio = binding.include2.checkBoxCorrigirLocalEscorregadio.isChecked() ? "S" : "N";
        riscoSeguranca.qp_utilizar_cabo_guia = binding.include2.checkBoxUtilizarCaboGuia.isChecked() ? "S" : "N";
        riscoSeguranca.qp_utilizar_cinto_de_seguranca = binding.include2.checkBoxUtilizarCintoSeguranca.isChecked() ? "S" : "N";
        riscoSeguranca.qp_utilizar_corrimao = binding.include2.checkBoxUtilizarCorrimao.isChecked() ? "S" : "N";
        riscoSeguranca.qp_utilizar_trava_quedas = binding.include2.checkBoxUtilizarTravaQuedas.isChecked() ? "S" : "N";
        riscoSeguranca.qp_outros = binding.include2.checkBoxQpOutros.isChecked() ? "S" : "N";
        riscoSeguranca.r_protetor_auditivo = binding.include2.checkBoxUtilizarProtetorAuditivo.isChecked() ? "S" : "N";
        riscoSeguranca.r_outros = binding.include2.checkBoxOutrosRuidos.isChecked() ? "S" : "N";
        riscoSeguranca.st_higienizacao_desinfeccao = binding.include2.checkBoxHigienizacaoDesinfeccao.isChecked() ? "S" : "N";
        riscoSeguranca.st_utilizar_creme_de_protecao = binding.include2.checkBoxUtilizarCremeProtecao.isChecked() ? "S" : "N";
        riscoSeguranca.st_utilizar_macacao_em_tnt = binding.include2.checkBoxUtilizarMacacaoTnt.isChecked() ? "S" : "N";
        riscoSeguranca.st_utilizar_oculos_ampla_visao = binding.include2.checkBoxUtilizarOculosAmplaVisao.isChecked() ? "S" : "N";
        riscoSeguranca.st_utilizar_resp_c_filtro_p_produtos_quimicos = binding.include2.checkBoxUtilizarRespFiltroProdutosQuimicos.isChecked() ? "S" : "N";
        riscoSeguranca.st_utlizar_luvas = binding.include2.checkBoxUtilizarLuvas.isChecked() ? "S" : "N";
        riscoSeguranca.vd_solicitar_apoio_da_brigada = binding.include2.checkBoxSolicitarApoioBrigada.isChecked() ? "S" : "N";
        riscoSeguranca.vd_providenciar_barreiras_de_contencao = binding.include2.checkBoxProvidenciarBarreirasContencao.isChecked() ? "S" : "N";
        riscoSeguranca.vd_providenciar_po_de_serragem = binding.include2.checkBoxProvidenciarPoSerragem.isChecked() ? "S" : "N";
        riscoSeguranca.vd_outros = binding.include2.checkBoxOutrosVazamentosDerramamentos.isChecked() ? "S" : "N";


        InspecaoAPR inspecaoAPR = new InspecaoAPR();

        //inspecaoAPR.fk_id_funcionario= String.valueOf();
        // inspecaoAPR.fk_id_usuario= String.valueOf();
        //inspecaoAPR.fk_id_servidor= String.valueOf();
        inspecaoAPR.ose_oss= binding.edtOseOss.getText().toString();
        inspecaoAPR.ost= binding.edtOst.getText().toString();
        inspecaoAPR.inicio_ativ1 = binding.include4.edtHoraInicio.getText().toString();
        inspecaoAPR.inicio_ativ2 = binding.include4.edtHoraInicio2.getText().toString();
        inspecaoAPR.termino_ativ1 =  binding.include4.edtHoraTermino.getText().toString();
        inspecaoAPR.termino_ativ2 =  binding.include4.edtHoraTermino2.getText().toString();
        inspecaoAPR.ts_sincronizacao = new Date();
        inspecaoAPR.ts_cadastro= new Date();
        inspecaoAPR.dt_ativ1 = binding.include4.edtData.getText().toString();
        inspecaoAPR.dt_ativ2 = binding.include4.edtData2.getText().toString();
        inspecaoAPR.solicitante =  funcionariosIds.get(binding.include4.spinnerSolicitante.getSelectedItemPosition()).toString();
        inspecaoAPR.resp1 =  funcionariosIds.get(binding.include4.spinnerReponsavel1.getSelectedItemPosition()).toString();
        inspecaoAPR.resp2 =  funcionariosIds.get(binding.include4.spinnerReponsavel2.getSelectedItemPosition()).toString();
        //inspecaoAPR.ativ1_executar =  funcionariosIds.get(binding.include4.spinnerAtividadeAExecutar.getSelectedItemPosition()).toString();
        //inspecaoAPR.ativ2_executar =  funcionariosIds.get(binding.include4.spinnerAtividadeAExecutar2.getSelectedItemPosition()).toString();
        inspecaoAPR.de_outro_controle = binding.include6.edtTxtOutrosControles.getText().toString();
        inspecaoAPR.planilha_risco_consultada = binding.include6.txtPlanilhaRiscoConsultada.getText().toString();
        inspecaoAPR.de_comentario = binding.include6.edtTxtComentarios.getText().toString();
        inspecaoAPR.ambientes_confinados = binding.include1.checkBoxAmbientesConfinados.isChecked() ? "S" : "N";
        inspecaoAPR.coordenacao_de_manobra_in_loco = binding.include1.checkBoxCoordenacaoManobraInloco.isChecked() ? "S" : "N";
        inspecaoAPR.trabalho_a_quente = binding.include1.checkBoxTrabalhoQuente.isChecked() ? "S" : "N";
        inspecaoAPR.escavacao_acima_de_05m = binding.include1.checkBoxEscavacaoAcima05.isChecked() ? "S" : "N";
        inspecaoAPR.explosivos = binding.include1.checkBoxExplosivos.isChecked() ? "S" : "N";
        inspecaoAPR.icamento = binding.include1.checkBoxIcamento.isChecked() ? "S" : "N";
        inspecaoAPR.trabalho_em_altura = binding.include1.checkBoxTrabalhoAltura.isChecked() ? "S" : "N";
        inspecaoAPR.levantamento_de_dados_em_area_energizada = binding.include1.checkBoxLevantamentoDadosAreaEnergizada.isChecked() ? "S" : "N";
        inspecaoAPR.manut_grupo_gerador_diesel = binding.include1.checkBoxManutGrupoGeradorDiesel.isChecked() ? "S" : "N";
        inspecaoAPR.manut_quadro_comando_energizado = binding.include1.checkBoxManutQuadroComandoEnerg.isChecked() ? "S" : "N";
        inspecaoAPR.manut_quadro_comando_denergizado = binding.include1.checkBoxManutQuadroComandoDesenerg.isChecked() ? "S" : "N";
        inspecaoAPR.media_e_alta_tensao_NR_10 = binding.include1.checkBoxMediaAltaTensaoNR10.isChecked() ? "S" : "N";
        inspecaoAPR.medicao_de_sequencia_de_fases_em_equip_energ = binding.include1.checkBoxMedicaoSeqFasesEquipEnerg.isChecked() ? "S" : "N";
        inspecaoAPR.numeracao_operativa_de_equipamento = binding.include1.checkBoxNumeracaoOperativaEquip.isChecked() ? "S" : "N";
        inspecaoAPR.outros_ver_matriz_de_risco = binding.include1.checkBoxOutrosMatrizRisco.isChecked() ? "S" : "N";
        inspecaoAPR.realizacao_de_testes_funcionais_operacionais_de_equipamento = binding.include1.checkBoxRealizacaoTestesFuncionaisOperEquip.isChecked() ? "S" : "N";
        inspecaoAPR.subs_quimica_perigosa = binding.include1.checkBoxSubsPerigosa.isChecked() ? "S" : "N";


        AnalisePessoal analisePessoal = new AnalisePessoal();


        //analisePessoal.fk_id_funcionario= String.valueOf();
        // analisePessoal.fk_id_inspecao_apr=String.valeuof();
        analisePessoal.ts_sincronizacao = new Date();
        analisePessoal.ts_cadastro= new Date();
        analisePessoal.as_minhas_ferramentas_estao_ok = getValueFromSpinner(binding.include3.spinnerFerramentasOk.getSelectedItemPosition());
        analisePessoal.as_pessoas_sabem_onde_estou = getValueFromSpinner(binding.include3.spinnerOndeEstou.getSelectedItemPosition());
        analisePessoal.conheco_a_atividade_a_ser_executada = getValueFromSpinner(binding.include3.spinnerConhecoAtividade.getSelectedItemPosition());
        analisePessoal.existe_rota_de_fuga = getValueFromSpinner(binding.include3.spinnerRotaFuga.getSelectedItemPosition());
        analisePessoal.sinto_me_bem_para_executar_a_atividade = getValueFromSpinner(binding.include3.spinnerSintoBem.getSelectedItemPosition());


        EPISConvencionais episConvencionais = new EPISConvencionais();

        //episConvencionais.fk_id_inspecao_apr= String.valueOf();
        // episConvencionais.fk_id_servidor= String.valueOf();
        episConvencionais.ts_sincronizacao = new Date();
        episConvencionais.ts_cadastro= new Date();
        episConvencionais.avental_de_couro = binding.include5.checkBoxAventalCouro.isChecked() ? "S" : "N";
        episConvencionais.balaclava = binding.include5.checkBoxBalaclava.isChecked() ? "S" : "N";
        episConvencionais.calcado_de_seguranca = binding.include5.calcadoSeguranca.isChecked() ? "S" : "N";
        episConvencionais.capacete_com_jugular = binding.include5.checkBoxCapaceteJugular.isChecked() ? "S" : "N";
        episConvencionais.cinto_de_seguranca_com_dois = binding.include5.checkBoxCintoSegDois.isChecked() ? "S" : "N";
        episConvencionais.conjunto_em_hourion_p_temp = binding.include5.checkBoxConjHourionTemp.isChecked() ? "S" : "N";
        episConvencionais.conjunto_em_texion_g_p_temp = binding.include5.checkBoxConjTexionGTemp.isChecked() ? "S" : "N";
        episConvencionais.conjunto_em_texion_w_p_trab_frio = binding.include5.checkBoxConjTexionWTrabFrio.isChecked() ? "S" : "N";
        episConvencionais.luva_de_pvc = binding.include5.checkBoxLuvaPvc.isChecked() ? "S" : "N";
        episConvencionais.luva_de_raspa = binding.include5.checkBoxLuvaRaspa.isChecked() ? "S" : "N";
        episConvencionais.Luva_de_vaqueta = binding.include5.checkBoxLuvaVaqueta.isChecked() ? "S" : "N";
        episConvencionais.macacao_tnt = binding.include5.checkBoxMacacaoTnt.isChecked() ? "S" : "N";
        episConvencionais.mangote_de_couro = binding.include5.checkBoxMangoteCouro.isChecked() ? "S" : "N";
        episConvencionais.mascara_autonoma = binding.include5.checkBoxMascaraAutonoma.isChecked() ? "S" : "N";
        episConvencionais.oculos_ampla_visao = binding.include5.checkBoxOculosAmplaVisao.isChecked() ? "S" : "N";
        episConvencionais.oculos_de_seguranca = binding.include5.checkBoxOculosSeguranca.isChecked() ? "S" : "N";
        episConvencionais.perneira = binding.include5.checkBoxPerneira.isChecked() ? "S" : "N";
        episConvencionais.protetor_auditivo = binding.include5.checkBoxProtetorAuditivo.isChecked() ? "S" : "N";
        episConvencionais.protetor_facial = binding.include5.checkBoxProtetorFacial.isChecked() ? "S" : "N";
        episConvencionais.respirador_contra_poeira = binding.include5.checkBoxRespiradorPoeira.isChecked() ? "S" : "N";
        episConvencionais.outros = binding.include5.checkBoxOutros.isChecked() ? "S" : "N";


        EPISEspecificos episEspecificos = new EPISEspecificos();

        //episEspecificos.fk_id_inspecao_apr= String.valueOf();
        // episEspecificos.fk_id_servidor= String.valueOf();
        episEspecificos.ts_sincronizacao = new Date();
        episEspecificos.ts_cadastro = new Date();
        episEspecificos.aterramento_temporario = binding.include5.checkBoxAterramentoTemporario.isChecked() ? "S" : "N";
        episEspecificos.balaclava = binding.include5.checkBoxBalaclava.isChecked() ? "S" : "N";
        episEspecificos.bastao_isolante = binding.include5.checkBoxBastaoIsolante.isChecked() ? "S" : "N";
        episEspecificos.cadeado_de_seguranca = binding.include5.checkBoxCadeadoSeguranca.isChecked() ? "S" : "N";
        episEspecificos.capuz_classe_4 = binding.include5.checkBoxCapuzClasse4.isChecked() ? "S" : "N";
        episEspecificos.etiqueta_de_bloqueio = binding.include5.checkBoxEtiquetaBloqueio.isChecked() ? "S" : "N";
        episEspecificos.detector_de_tensao = binding.include5.checkBoxDetectorTensao.isChecked() ? "S" : "N";
        episEspecificos.ferramentas_isoladas_p_1000 = binding.include5.checkBoxFerramentasIsoladas1000v.isChecked() ? "S" : "N";
        episEspecificos.luva_acima_de_138kv = binding.include5.checkBoxLuvaAcima138kv.isChecked() ? "S" : "N";
        episEspecificos.luva_para_300kv = binding.include5.checkBoxLuvas300kv.isChecked() ? "S" : "N";
        episEspecificos.luva_para_1000kv = binding.include5.checkBoxLuva1000kv.isChecked() ? "S" : "N";
        episEspecificos.roupa_protecao_arco_eletrico_classe4 = binding.include5.checkBoxRoupaProtecaoArcoEletricoClasse4.isChecked() ? "S" : "N";
        episEspecificos.roupa_protecao_arco_eletrico_classe_2 = binding.include5.checkBoxRoupaProtecaoArcoEletricoClasse2.isChecked() ? "S" : "N";
        episEspecificos.outros = binding.include5.checkBoxOutrosEpiEspecifico.isChecked() ? "S" : "N";


        if (!validate()) return;
        mViewModel.save(inspecaoAPR, episEspecificos, episConvencionais, riscoSeguranca, analisePessoal).observe(this, response -> {

            if (response != null) {
                setLoading(false);
                String message = response;

                if (response.equals(Messages.SUCCESS_MESSAGE))
                    message = Messages.INSERT_SUCCESS_MESSAGE;

                new MaterialAlertDialogBuilder(requireContext(), R.style.StyledThemeOverlay_MaterialComponents_MaterialAlertDialog)
                        .setTitle(R.string.sucesso)
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok, (dialog, id) -> {
                            final NavController navController = Navigation.findNavController(getView());
                            navController.navigate(R.id.homeFragment);
                        })
                        .setIcon(R.drawable.ic_success_check_24)
                        .show();
            }
        });

        mViewModel.getErrorSave().observe(this, error -> {
            if (error!=null) {
                setLoading(false);
                new MaterialAlertDialogBuilder(requireContext(), R.style.StyledThemeOverlay_MaterialComponents_MaterialAlertDialog)
                        .setTitle(R.string.error_message)
                        .setMessage(error)
                        .setPositiveButton(android.R.string.ok, null)
                        .setIcon(R.drawable.ic_error_24)
                        .show();
            }
        });

    }

    private void setLoading(Boolean action) {


    }



}

