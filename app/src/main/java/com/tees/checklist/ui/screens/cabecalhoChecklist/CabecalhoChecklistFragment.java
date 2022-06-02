package com.tees.checklist.ui.screens.cabecalhoChecklist;

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
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.tees.checklist.R;
import com.tees.checklist.commons.Preferences;
import com.tees.checklist.data.model.Atividade;
import com.tees.checklist.data.model.Funcionario;
import com.tees.checklist.data.model.Localidade;
import com.tees.checklist.databinding.FragmentCabecalhoChecklistBinding;
import com.tees.checklist.ui.Injection;

import java.util.ArrayList;
import java.util.List;

public class CabecalhoChecklistFragment extends Fragment {

    private CabecalhoChecklistViewModel ViewModel;
    private CabecalhoChecklistViewModelFactory mViewModelFactory;
    private FragmentCabecalhoChecklistBinding binding;
    private Context mContext;
    public Preferences preferences;
    AlertDialog dialog;
    private CabecalhoChecklistViewModel mViewModel;


    public static CabecalhoChecklistFragment newInstance() {
        return new CabecalhoChecklistFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_cabecalho_checklist,
                container,
                false);

        toolBarConfig();

        return binding.getRoot();
    }


    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        mViewModelFactory = Injection.provideCabecalhoChecklistViewModelFactory(mContext);
        mViewModel = new ViewModelProvider(this, mViewModelFactory).get(CabecalhoChecklistViewModel.class);
        setListeners();
    }


    private void toolBarConfig() {
        Toolbar top_app_bar = binding.toolbar.findViewById(R.id.topAppBar);
        top_app_bar.setTitle(R.string.cabecalhoChecklist_screen_name);
        top_app_bar.setNavigationOnClickListener(
                view -> {
                    final NavController navController = Navigation.findNavController(view);
                    navController.popBackStack();
                }
        );
    }


    private void  populateFuncionarios(List<Funcionario>funcionarios){
        Spinner sItem = binding.spinnerFuncionario;
        List<String> spinnerArray =  new ArrayList<String>();
        for (Funcionario funcionario:funcionarios ) {
            spinnerArray.add(funcionario.no_funcionario + " - " + funcionario.co_cnh + " - " + funcionario.dt_validade_cnh );
        }

        if (spinnerArray.size()>0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
            sItem.setAdapter(adapter);
        }
    }


    private void  populateLocalidades(List<Localidade> localidades){

        Spinner sItem = binding.spinnerLocalidade;

        List<String> spinnerArray =  new ArrayList<String>();
        for (Localidade localidade:localidades ) {
            spinnerArray.add(localidade.no_localidade + " - " + localidade.sg_uf);
        }

        if (spinnerArray.size()>0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
            sItem.setAdapter(adapter);
        }
    }

    private void  populateAtividades(List<Atividade> atividades){
        Spinner sItem = binding.spinnerAtividade;
        List<String> spinnerArray =  new ArrayList<String>();
        for (Atividade atividade:atividades ) {
            spinnerArray.add(atividade.no_atividade);
        }
        if (spinnerArray.size()>0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
            sItem.setAdapter(adapter);
        }
    }

    private void populateAdapter(List<Object> list) {
        populateFuncionarios((List<Funcionario>)list.get(0));
        populateAtividades((List<Atividade>)list.get(1));
        populateLocalidades((List<Localidade>)list.get(2));
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
                    binding.buttonEntrar.setOnClickListener(
                    view -> {
                    NavDirections action = CabecalhoChecklistFragmentDirections.actionCabecalhoChecklistFragmentToChecklistFragment();
                    Navigation.findNavController(view).navigate(action);
                }
        );
        });
    }



}

