package com.tees.checklist.ui.screens.checklist;

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
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.tees.checklist.R;
import com.tees.checklist.base.BaseFragment;
import com.tees.checklist.commons.Preferences;
import com.tees.checklist.databinding.FragmentChecklistBinding;
import com.tees.checklist.ui.Injection;

public class ChecklistFragment extends BaseFragment {

        private ChecklistViewModel mViewModel;
        private FragmentChecklistBinding binding;
        private Context mContext;
        public Preferences preferences;
        AlertDialog dialog;


    public static ChecklistFragment newInstance() {
        return new ChecklistFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_checklist,
                container,
                false);

        toolBarConfig();
        setListeners();

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ChecklistViewModel.class);
        // TODO: Use the ViewModel
    }

        public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        preferences = new Preferences(mContext);
        ChecklistViewModelFactory mViewModelFactory = Injection.provideChecklistViewModelFactory(mContext);
        mViewModel = new ViewModelProvider(this, mViewModelFactory).get(ChecklistViewModel.class);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        config();
        Preferences preferences = new Preferences(getContext());
        checkAuthentication(view, preferences);
    }

    private void setPerfil(Preferences preferences) {
      /*  String tipo = preferences.getUser().TipoLeiturista;
        if (tipo.equals("A")) {
            binding.perfilText.setText(R.string.admin);
            binding.buttonCarga.setVisibility(View.VISIBLE);
            binding.buttonDescarga.setVisibility(View.VISIBLE);
            binding.buttonDescargaFoto.setVisibility(View.VISIBLE);
            binding.buttonConfiguracoes.setVisibility(View.VISIBLE);
        } else binding.perfilText.setText(R.string.Usuario); */
    }

    private void checkAuthentication(@NonNull View view, Preferences preferences) {
        if (!preferences.isLogged()) {
            final NavController navController = Navigation.findNavController(view);
            navController.navigate(R.id.loginFragment);

        } else {
            setPerfil(preferences);
        }
    }

    private void toolBarConfig() {
        Toolbar top_app_bar = binding.toolbar.findViewById(R.id.topAppBar);
        top_app_bar.setTitle(R.string.checklist_screen_name);
        top_app_bar.setNavigationOnClickListener(
                view -> {
                    final NavController navController = Navigation.findNavController(view);
                    navController.popBackStack();
                }
        );
    }



    private void setListeners() {

        binding.buttonInspecaoVeiculo.setOnClickListener(
            view -> {
                NavDirections action = ChecklistFragmentDirections.actionChecklistFragmentToInspecaoDiariaVeiculoFragment();
                Navigation.findNavController(view).navigate(action);

            }
        );

        binding.buttonChecklistEPI.setOnClickListener(
            view -> {
                NavDirections action = ChecklistFragmentDirections.actionChecklistFragmentToChecklistDiarioEPIFragment();
                Navigation.findNavController(view).navigate(action);
            }
        );

        binding.buttonChecklistEPC.setOnClickListener(
            view -> {
                NavDirections action = ChecklistFragmentDirections.actionChecklistFragmentToChecklistDiarioEPCFragment();
                Navigation.findNavController(view).navigate(action);

           }
       );
    }
}