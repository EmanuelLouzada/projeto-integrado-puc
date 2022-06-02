package com.tees.checklist.ui.screens.inspecaoVeicular;

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
import com.tees.checklist.R;
import com.tees.checklist.commons.Messages;
import com.tees.checklist.commons.Preferences;
import com.tees.checklist.data.model.Funcionario;
import com.tees.checklist.data.model.InspecaoVeicular;
import com.tees.checklist.data.model.Veiculo;
import com.tees.checklist.databinding.FragmentInspecaoDiariaVeiculo2Binding;
import com.tees.checklist.ui.Injection;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InspecaoDiariaVeiculoFragment extends Fragment {

    private InspecaoDiariaVeiculoViewModelFactory mViewModelFactory;
    private FragmentInspecaoDiariaVeiculo2Binding binding;
    private Context mContext;
    public Preferences preferences;
    AlertDialog dialog;
    private InspecaoDiarioVeiculoViewModel mViewModel;
    private ArrayList<Integer> funcionariosIds = new ArrayList<>();
    private ArrayList<Integer> veiculosIds = new ArrayList<>();


    public static InspecaoDiariaVeiculoFragment newInstance() {
        return new InspecaoDiariaVeiculoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_inspecao_diaria_veiculo2,
                container,
                false);

        toolBarConfig();
        setListeners();
        return binding.getRoot();
    }


    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        mViewModelFactory = Injection.provideInspecaoDiariaVeiculoViewModelFactory(mContext);
        mViewModel = new ViewModelProvider(this, mViewModelFactory).get(InspecaoDiarioVeiculoViewModel.class);
    }


    private void toolBarConfig() {
        Toolbar top_app_bar = binding.toolbar.findViewById(R.id.topAppBar);
        top_app_bar.setTitle(R.string.inspecaoVeicular_screen_name);
        top_app_bar.setNavigationOnClickListener(
                view -> {
                    final NavController navController = Navigation.findNavController(view);
                    navController.popBackStack();
                }
        );
    }


    private void  populateFuncionarios(List<Funcionario> funcionarios){
        Spinner sItem = binding.spinnerFuncionario;
        List<String> spinnerArray = new ArrayList<>();
        for (Funcionario funcionario:funcionarios ) {
            spinnerArray.add(funcionario.no_funcionario + " - " + funcionario.co_cnh + " - " + funcionario.dt_validade_cnh );
            funcionariosIds.add(funcionario.id);
        }

        if (spinnerArray.size()>0) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        sItem.setAdapter(adapter);
    }
}


    private void populateVeiculos (List<Veiculo> veiculos){
        Spinner sItem = binding.spinnerVeiculo;
        List<String> spinnerArray = new ArrayList<>();
        for (Veiculo veiculo:veiculos ) {
            spinnerArray.add(veiculo.no_ano + " - " + veiculo.no_marca + " - " + veiculo.no_modelo+ "-" + veiculo.no_placa);
            veiculosIds.add(veiculo.id);
        }

        if (spinnerArray.size()>0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
            sItem.setAdapter(adapter);
        }
    }

    private void populateAdapter(List<Object> list) {
        populateFuncionarios((List<Funcionario>)list.get(0));
        populateVeiculos((List<Veiculo>)list.get(1));
}

    private void setListeners() {
        mViewModel.getResult().observe(this, result -> {
            if (result == null) {
                return ;
            }
            populateAdapter(result);

        });

        mViewModel.getError().observe(this, result -> {
            if (result != null) {
                new MaterialAlertDialogBuilder(requireContext(), R.style.StyledThemeOverlay_MaterialComponents_MaterialAlertDialog)
                        .setTitle(R.string.error_message)
                        .setMessage(result)
                        .setPositiveButton(android.R.string.ok, null)
                        .setIcon(R.drawable.ic_error_24)
                        .show();

            }

        });


        binding.buttonSave.setOnClickListener(
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

        InspecaoVeicular item = new InspecaoVeicular();

        item.ts_cadastro= new Date();
        item.ts_sincronizacao= new Date();
        item.ts_inspecao= new Date();
        item.fk_id_funcionario =  funcionariosIds.get(binding.spinnerFuncionario.getSelectedItemPosition()).toString();
        item.fk_id_veiculo =  veiculosIds.get(binding.spinnerVeiculo.getSelectedItemPosition()).toString();
        item.nu_km =  binding.edtKmAtual.getText().toString();
        item.agua_arrefecimento =     getValueFromSpinner(binding.aguaArrefecimento.getSelectedItemPosition());
        item.alinhamento_balanceamento =  getValueFromSpinner(binding.alinhamentoBalanceamento.getSelectedItemPosition());
        item.ar_condicionado =  getValueFromSpinner(binding.arcondicionado.getSelectedItemPosition());
        item.bancos_do_veiculo =  getValueFromSpinner(binding.spinnerBancosdocarro.getSelectedItemPosition());
        item.buzina =  getValueFromSpinner(binding.buzina.getSelectedItemPosition());
        item.camera_externa =  getValueFromSpinner(binding.spinnerCameraexterna.getSelectedItemPosition());
        item.camera_interna =  getValueFromSpinner(binding.spinnerCamerainterna.getSelectedItemPosition());
        item.chave_de_roda =  getValueFromSpinner(binding.chaveRoda.getSelectedItemPosition());
        item.cinto_de_seguranca =  getValueFromSpinner(binding.cintoSeguranca.getSelectedItemPosition());
        item.embreagem =  getValueFromSpinner(binding.spinnerEmbreagem.getSelectedItemPosition());
        item.escapamento =  getValueFromSpinner(binding.escapamento.getSelectedItemPosition());
        item.espelho_retrovisor_externo =  getValueFromSpinner(binding.retrovisorExterno.getSelectedItemPosition());
        item.espelho_retrovisor_interno =  getValueFromSpinner(binding.retrovisorInterno.getSelectedItemPosition());
        item.estepe =  getValueFromSpinner(binding.estepe.getSelectedItemPosition());
        item.extintor_de_incendio =  getValueFromSpinner(binding.extintorIncendio.getSelectedItemPosition());
        item.ferois =  getValueFromSpinner(binding.farois.getSelectedItemPosition());
        item.freio =  getValueFromSpinner(binding.spinnerFreio.getSelectedItemPosition());
        item.freio_de_mao =  getValueFromSpinner(binding.freioMao.getSelectedItemPosition());
        item.lampadas_inter =  getValueFromSpinner(binding.lampadasInter.getSelectedItemPosition());
        item.lanternas =  getValueFromSpinner(binding.lanternas.getSelectedItemPosition());
        item.limpador_para_brisa=  getValueFromSpinner(binding.paraBrisas.getSelectedItemPosition());
        item.limpeza_do_veiculo=  getValueFromSpinner(binding.spinnerLimpezadoveiculo.getSelectedItemPosition());
        item.luz_alta =  getValueFromSpinner(binding.luzAlta.getSelectedItemPosition());
        item.luz_baixa =  getValueFromSpinner(binding.luzBaixa.getSelectedItemPosition());
        item.luz_de_freio=  getValueFromSpinner(binding.luzFreios.getSelectedItemPosition());
        item.luz_de_re=  getValueFromSpinner(binding.luzRe.getSelectedItemPosition());
        item.macaco =  getValueFromSpinner(binding.macaco.getSelectedItemPosition());
        item.motor =  getValueFromSpinner(binding.spinnerMotor.getSelectedItemPosition());
        item.nivel_oleo_freio =  getValueFromSpinner(binding.spinnerNivelOleoFreio.getSelectedItemPosition());
        item.nivel_oleo_motor =  getValueFromSpinner(binding.spinnerNivelOleoMotor.getSelectedItemPosition());
        item.painel=  getValueFromSpinner(binding.painel.getSelectedItemPosition());
        item.para_choque=  getValueFromSpinner(binding.paraChoque.getSelectedItemPosition());
        item.pedais =  getValueFromSpinner(binding.pedais.getSelectedItemPosition());
        item.pisca_alerta =  getValueFromSpinner(binding.spinnerPiscaalerta.getSelectedItemPosition());
        item.placa_dianteira=  getValueFromSpinner(binding.placaDianteira.getSelectedItemPosition());
        item.placa_traseira=  getValueFromSpinner(binding.placaTraseira.getSelectedItemPosition());
        item.pneus =  getValueFromSpinner(binding.pneus.getSelectedItemPosition());
        item.portas_e_travas =  getValueFromSpinner(binding.portasTravas.getSelectedItemPosition());
        item.revisoes=  getValueFromSpinner(binding.revisoes.getSelectedItemPosition());
        item.setas=  getValueFromSpinner(binding.setas.getSelectedItemPosition());
        item.sinal_sonoro_de_re =  getValueFromSpinner(binding.sinalSonoroRe.getSelectedItemPosition());
        item.suspensao =  getValueFromSpinner(binding.suspensao.getSelectedItemPosition());
        item.triangulo_sinalizador=  getValueFromSpinner(binding.trianguloSinalizador.getSelectedItemPosition());
        item.velocimetro=  getValueFromSpinner(binding.velocimetro.getSelectedItemPosition());
        item.vidros=  getValueFromSpinner(binding.vidros.getSelectedItemPosition());
        item.velocimetro=  getValueFromSpinner(binding.velocimetro.getSelectedItemPosition());
        item.vidros=  getValueFromSpinner(binding.vidros.getSelectedItemPosition());

        if(!validate()) return;
        mViewModel.updateOffline(item).observe(this, response -> {
            setLoading(true);
            if (response != null) {
                setLoading(false);
                String message = response;
                if(response.equals(Messages.SUCCESS_MESSAGE)) message = Messages.UPDATE_SUCCESS_MESSAGE;

                new MaterialAlertDialogBuilder(requireContext(), R.style.StyledThemeOverlay_MaterialComponents_MaterialAlertDialog)
                        .setTitle(R.string.sucesso)
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok,(dialog, id) -> {
                            final NavController navController = Navigation.findNavController(getView());
                            navController.navigate(R.id.homeFragment);
                        })
                        .setIcon(R.drawable.ic_success_check_24)
                        .show();
            }
        });

        mViewModel.getErrorSave().observe(this, error -> {
            if (!error.isEmpty()) {
                setLoading(false);
                new MaterialAlertDialogBuilder(requireContext(), R.style.StyledThemeOverlay_MaterialComponents_MaterialAlertDialog)
                        .setTitle(R.string.error_message)
                        .setMessage(error)
                        .setPositiveButton(android.R.string.ok,null)
                        .setIcon(R.drawable.ic_error_24)
                        .show();
            }
        });


    }

    private void setLoading(Boolean action) {


    }



}

