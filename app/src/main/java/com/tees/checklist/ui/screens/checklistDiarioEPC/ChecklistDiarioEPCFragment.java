package com.tees.checklist.ui.screens.checklistDiarioEPC;

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
import com.tees.checklist.data.model.InspecaoEPC;
import com.tees.checklist.databinding.FragmentChecklistDiarioEpcBinding;
import com.tees.checklist.ui.Injection;

import java.util.Date;

public class ChecklistDiarioEPCFragment extends Fragment {

    private ChecklistDiarioEPCViewModel ViewModel;
    private FragmentChecklistDiarioEpcBinding binding;
    private Context mContext;
    public Preferences preferences;
    AlertDialog dialog;
    private ChecklistDiarioEPCViewModel mViewModel;
    private ChecklistDiarioEpcViewModelFactory mViewModelFactory;


    public static ChecklistDiarioEPCFragment newInstance() {
        return new ChecklistDiarioEPCFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_checklist_diario_epc,
                container,
                false);

        toolBarConfig();
        setListeners();
        return binding.getRoot();
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        mViewModelFactory = Injection.provideChecklistDiarioEpcViewModelFactory(mContext);
        mViewModel = new ViewModelProvider(this, mViewModelFactory).get(ChecklistDiarioEPCViewModel.class);
    }

    private void toolBarConfig() {
        Toolbar top_app_bar = binding.toolbar.findViewById(R.id.topAppBar);
        top_app_bar.setTitle(R.string.checklistDiarioEPC_screen_name);
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

        InspecaoEPC item = new InspecaoEPC();

        item.ts_sincronizacao = new Date();
        item.ts_cadastro = new Date();
        item.bandeirola_sinalizacao = getValueFromSpinner(binding.spinnerItsApr.getSelectedItemPosition());
        item.bandeirola_sinalizacao = getValueFromSpinner(binding.spinnerBandeirolaSinalizacao.getSelectedItemPosition());
        item.corda_cordilha_dupla_acao = getValueFromSpinner(binding.spinnerCordasCarretilhaDupla.getSelectedItemPosition());
        item.detector_presenca_ausencia = getValueFromSpinner(binding.spinnerDetectorPresencaAusencia.getSelectedItemPosition());
        item.escada_fibra_vidro_extensiva_singela = getValueFromSpinner(binding.spinnerEscadasFibraVidro.getSelectedItemPosition());
        item.laudo_dieletrico = getValueFromSpinner(binding.spinnerLaudoDieletrico.getSelectedItemPosition());
        item.varas_manobras_telescopia = getValueFromSpinner(binding.spinnerVarasManobraTelecospia.getSelectedItemPosition());

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
            if (!error.isEmpty()) {
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
