package com.tees.checklist.ui.screens.settings;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.tees.checklist.R;
import com.tees.checklist.base.BaseFragment;
import com.tees.checklist.commons.Messages;
import com.tees.checklist.commons.Preferences;
import com.tees.checklist.data.model.Configuracoes;
import com.tees.checklist.databinding.FragmentSettingsBinding;
import com.tees.checklist.ui.Injection;

//import org.apache.commons.lang3.StringUtils;

public class SettingsFragment extends BaseFragment {

    private FragmentSettingsBinding binding;
    private SettingsViewModel settingsViewModel;
    private SettingsViewModelFactory mViewModelFactory;

    public SettingsFragment() {
        // Required empty public constructor
    }


    private Context mContext;
    public Preferences preferences;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        config();
        mContext = context;
        preferences = new Preferences(mContext);
        mViewModelFactory = Injection.provideSettingsViewModelFactory(mContext);
        settingsViewModel = new ViewModelProvider(this, mViewModelFactory).get(SettingsViewModel.class);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_settings,
                container,
                false
        );
        toolBarConfig();
        setListeners();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Configuracoes settings = preferences.getSettings();

        binding.urlWebServiceTextInputLayout.getEditText().setText(settings.ip_servidor_carga);
        binding.portaWebServiceTextInputLayout.getEditText().setText(settings.porta_carga);

        if (settings.modo_debug == 1) binding.debug.setChecked(true);
        else binding.debug.setChecked(false);
    }


    private void setListeners() {
        binding.buttonSave.setOnClickListener(
                view -> save());

        binding.buttonBack.setOnClickListener(
                view -> {

                    final NavController navController = Navigation.findNavController(view);
                    navController.navigate(R.id.homeFragment);
                });

    }
    private void toolBarConfig() {
        Toolbar top_app_bar = binding.toolbar.findViewById(R.id.topAppBar);
        top_app_bar.setTitle(R.string.settings);
        top_app_bar.setNavigationOnClickListener(
                view -> {
                    final NavController navController = Navigation.findNavController(view);
                    navController.popBackStack();
                }
        );
    }

    public boolean validate(){

        /*if(!StringUtils.isNumeric(binding.portaWebServiceTextInputLayout.getEditText().getText().toString())){
            new MaterialAlertDialogBuilder(requireContext(), R.style.StyledThemeOverlay_MaterialComponents_MaterialAlertDialog)
                    .setTitle(R.string.alerta)
                    .setMessage("Campo Porta Webservice precisa ser numérico!")
                    .setPositiveButton(android.R.string.ok,null)
                    .setIcon(R.drawable.ic_warning_24)
                    .show();
            return false;s
        }*/

        return true;
    }

    public void save() {
        if(!validate()) return;
        Configuracoes newSettings = preferences.getSettings();
        newSettings.ip_servidor_carga = binding.urlWebServiceTextInputLayout.getEditText().getText().toString();
        newSettings.porta_carga = binding.portaWebServiceTextInputLayout.getEditText().getText().toString();
        if (binding.debug.isChecked()) newSettings.modo_debug = 1;
        else newSettings.modo_debug = 0;
        settingsViewModel.updateOffline(newSettings).observe(this, response -> {
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

        settingsViewModel.getError().observe(this, error -> {
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
        if (action) {
            binding.buttonSave.setVisibility(View.GONE);
            binding.buttonBack.setVisibility(View.GONE);
            binding.loading.setVisibility(View.VISIBLE);
        } else {
            binding.buttonSave.setVisibility(View.VISIBLE);
            binding.buttonBack.setVisibility(View.VISIBLE);
            binding.loading.setVisibility(View.GONE);
        }

    }
}