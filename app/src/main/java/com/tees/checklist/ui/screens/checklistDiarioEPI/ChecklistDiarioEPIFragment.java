package com.tees.checklist.ui.screens.checklistDiarioEPI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.tees.checklist.data.model.InspecaoEPI;
import com.tees.checklist.databinding.FragmentChecklistDiarioEpiBinding;
import com.tees.checklist.ui.Injection;

import java.util.Date;

public class ChecklistDiarioEPIFragment extends Fragment {

    private FragmentChecklistDiarioEpiBinding binding;
    private Context mContext;
    public Preferences preferences;
    AlertDialog dialog;
    private ChecklistDiarioEpiViewModel mViewModel;
    private ChecklistDiarioEpiViewModelFactory mViewModelFactory;

    public static ChecklistDiarioEPIFragment newInstance() {
        return new ChecklistDiarioEPIFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_checklist_diario_epi,
                container,
                false);

        toolBarConfig();
        setListeners();
        return binding.getRoot();


    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        mViewModelFactory = Injection.provideChecklistDiarioEpiViewModelFactory(mContext);
        mViewModel = new ViewModelProvider(this, mViewModelFactory).get(ChecklistDiarioEpiViewModel.class);
    }

    private void toolBarConfig() {
        Toolbar top_app_bar = binding.toolbar.findViewById(R.id.topAppBar);
        top_app_bar.setTitle(R.string.checklistDiarioEPI_screen_name);
        top_app_bar.setNavigationOnClickListener(
                view -> {
                    final NavController navController = Navigation.findNavController(view);
                    navController.popBackStack();
                }


        );

    }


    public String getValueFromSpinner(int position) {
        String[] array = getResources().getStringArray(R.array.values_sim_nao);
        return array[position];
    }


    public boolean validate() {
        return true;
    }

    @SuppressLint("FragmentLiveDataObserve")
    public void save() {

        InspecaoEPI item = new InspecaoEPI();

        item.ts_sincronizacao = new Date();
        item.ts_cadastro = new Date();
        item.calcado_de_seguranca_s_parte_metalica = getValueFromSpinner(binding.spinnerCalcadoSegSParteMetalica.getSelectedItemPosition());
        item.capacete_de_segunranca_aba_frontal = getValueFromSpinner(binding.spinnerCapaceteAbaFrontal.getSelectedItemPosition());
        item.cinto_de_segunranca_tipo_para_quedista = getValueFromSpinner(binding.spinnerCintoSegParaquedista.getSelectedItemPosition());
        item.cracha_de_identificacao = getValueFromSpinner(binding.spinnerCrachaIdentificaccao.getSelectedItemPosition());
        item.har_habilitacao_de_acesso_a_rede = getValueFromSpinner(binding.spinnerHar.getSelectedItemPosition());
        item.laudos_dos_epis = getValueFromSpinner(binding.spinnerLaudosEpis.getSelectedItemPosition());
        item.luva_de_protecao_vaqueta = getValueFromSpinner(binding.spinnerLuvaProtecaoVaqueta.getSelectedItemPosition());
        item.luva_pu = getValueFromSpinner(binding.spinnerLuvasPu.getSelectedItemPosition());
        item.luvas_isolantes_classe_0 = getValueFromSpinner(binding.spinnerLuvasIsolantesClasse0.getSelectedItemPosition());
        item.luvas_isolantes_classe_2 = getValueFromSpinner(binding.spinnerLuvasIsolantesClasse2.getSelectedItemPosition());
        item.manga_isolante_classe_2 = getValueFromSpinner(binding.spinnerMangaIsolanteClasse2.getSelectedItemPosition());
        item.oculos_de_seguranca_lentes_fume = getValueFromSpinner(binding.spinnerOculosSegLentesFume.getSelectedItemPosition());
        item.oculos_de_seguranca_lentes_incolor = getValueFromSpinner(binding.spinnerOculosSegLentesIncolor.getSelectedItemPosition());
        item.perneira_tipo_canavieiro = getValueFromSpinner(binding.spinnerPerneiraCanavieiro.getSelectedItemPosition());
        item.protetor_facial = getValueFromSpinner(binding.spinnerProtetorFacial.getSelectedItemPosition());
        item.uniforme_resistente_a_chama = getValueFromSpinner(binding.spinnerUniformeResChama.getSelectedItemPosition());


        if (!validate()) return;
        mViewModel.updateOffline(item).observe(this, response -> {
            setLoading(true);
            if (response != null) {
                setLoading(false);
                String message = response;
                if (response.equals(Messages.SUCCESS_MESSAGE))
                    message = Messages.UPDATE_SUCCESS_MESSAGE;

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

        }

    private void setListeners() {

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

        binding.buttonSave.setOnClickListener(
                view -> save());

    }


    private void setLoading(Boolean action) {


    }

}



