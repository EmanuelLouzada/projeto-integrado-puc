package com.tees.checklist.ui.screens.home;

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
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.tees.checklist.R;
import com.tees.checklist.base.BaseFragment;
import com.tees.checklist.commons.Preferences;
import com.tees.checklist.databinding.FragmentHomeBinding;
import com.tees.checklist.ui.Injection;


public class HomeFragment extends BaseFragment {

    private HomeFaturamentoViewModel mViewModel;
    private FragmentHomeBinding binding;
    private Context mContext;
    public Preferences preferences;
    AlertDialog dialog;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_home,
                container,
                false);

        toolBarConfig();
        setListeners();

        return binding.getRoot();
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        preferences = new Preferences(mContext);
        HomeViewModelFactory mViewModelFactory = Injection.provideHomeFaturamentoViewModelFactory(mContext);
        mViewModel = new ViewModelProvider(this, mViewModelFactory).get(HomeFaturamentoViewModel.class);

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
        top_app_bar.setNavigationIcon(null);
        top_app_bar.setTitle(R.string.home_screen_name);
    }

    private void setListeners() {


        binding.buttonConfiguracoes.setOnClickListener(
                view -> {
                    NavDirections action = HomeFragmentDirections.actionHomeFragmentToSettingsFragment();
                    Navigation.findNavController(view).navigate(action);
                }
        );

        binding.buttonCheklist.setOnClickListener(
                view -> {
                    NavDirections action = HomeFragmentDirections.actionHomeFragmentToCabecalhoChecklistFragment2();
                    Navigation.findNavController(view).navigate(action);
                }
        );


        binding.buttonSair.setOnClickListener(
                view -> {
                    Preferences preferences = new Preferences(getContext());
                    preferences.clearUser();
                    preferences.clearSettings();
                    final NavController navController = Navigation.findNavController(view);
                    navController.navigate(R.id.loginFragment);
                }
        );
    }
}
