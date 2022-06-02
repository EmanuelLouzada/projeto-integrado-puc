package com.tees.checklist.ui.screens.load;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.tees.checklist.R;
import com.tees.checklist.base.BaseFragment;
import com.tees.checklist.commons.Constants;
import com.tees.checklist.commons.LogArquivo;
import com.tees.checklist.commons.Messages;
import com.tees.checklist.commons.Preferences;
import com.tees.checklist.commons.TableNames;
import com.tees.checklist.databinding.FragmentLoadBinding;
import com.tees.checklist.ui.Injection;


public class LoadFragment extends BaseFragment {

    private FragmentLoadBinding binding;
    private ProgressBar progressBar;
    private LoadViewModel viewModelLoad;
    private TextView load_text;
    private TextView  percent;
    private LoadViewModelFactory mViewModelFactory;
    private AppCompatButton button_finalizar;
    public int currentAttempt = 0;
    public Preferences preferences;
    public String message = "";


    private Context mContext = null;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        preferences = new Preferences(mContext);
        mViewModelFactory = Injection.provideCargaViewModelFactory(mContext);
        viewModelLoad = new ViewModelProvider(this, mViewModelFactory).get(LoadViewModel.class);
        viewModelLoad.stopThread();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        viewModelLoad.cancelRequests();
        viewModelLoad.stopThread();
        mContext = null;
    }

    public LoadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for mContext fragment

        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_load,
                container,
                false
        );
        load_text = binding.loadText;
        progressBar = binding.progress;
        percent = binding.percent;
        button_finalizar = binding.buttonFinalizar;
        button_finalizar.setVisibility(View.GONE);
        button_finalizar.setEnabled(false);
        setListeners();
        toolBarConfig();

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        config();
        loadProcess();
    }

    private void toolBarConfig() {
        Toolbar top_app_bar = binding.toolbar.findViewById(R.id.topAppBar);
        top_app_bar.setNavigationIcon(null);
        top_app_bar.setTitle(R.string.load_screen_name);
    }


    private void setListeners() {

        button_finalizar.setOnClickListener(
                view -> {
                    final NavController navController = Navigation.findNavController(view);
                    navController.navigateUp();
                }
        );
    }

    public void setObeservers(String table) {

        LiveData<String> liveDataLoad = viewModelLoad.initLoad(table);
        LiveData<Integer> liveDataPercent = viewModelLoad.getPercent();
        LiveData<String> liveDataError = viewModelLoad.getLoadError();

        liveDataLoad.observe(this, response -> {
            if (response != null) {
                if (response.equals(Messages.SUCCESS_LOAD)) {

                    String current = binding.loadText.getText().toString();
                    String newResponse = current + getString(R.string.load_text, response);
                    load_text.setText(newResponse);
                    new MaterialAlertDialogBuilder(requireContext(), R.style.StyledThemeOverlay_MaterialComponents_MaterialAlertDialog)
                            .setTitle(R.string.sucesso)
                            .setMessage(response)
                            .setPositiveButton(android.R.string.ok, null)
                            .setIcon(R.drawable.ic_success_check_24)
                            .show();

                    progressBar.setProgress(100);
                    percent.setText("100%");
                    button_finalizar.setVisibility(View.VISIBLE);
                    button_finalizar.setEnabled(true);
                } else {
                    Constants.OnlineReadWriteTimeOut = 30;
                    String current = binding.loadText.getText().toString();
                    String newResponse = current + getString(R.string.load_text, response.split("-")[0]);

                    load_text.setText(newResponse);
                    binding.srollview.post(() -> binding.srollview.fullScroll(View.FOCUS_DOWN));
                }
            }
        });

        liveDataPercent.observe(this, response -> {
            if (response != null) {
                progressBar.setProgress(response);
                percent.setText(response +"%");
            }
        });


        liveDataError.observe(this, response -> {
            if (response != null && !response.isEmpty()) {

                if(Constants.OnlineReadWriteTimeOut==30){
                    String[] responseArray = response.split("-");
                    Constants.OnlineReadWriteTimeOut = Constants.OnlineReadWriteTimeOut+30;
                    retry(responseArray[0],false);

                }else{


                    currentAttempt++;
                    if (currentAttempt <= Constants.TentativaCarga) {

                        liveDataLoad.removeObservers(this);
                        liveDataError.removeObservers(this);

                        new MaterialAlertDialogBuilder(requireContext(), R.style.StyledThemeOverlay_MaterialComponents_MaterialAlertDialog)
                                .setTitle(R.string.attention)
                                .setMessage(response + ". Tentar novamente?")
                                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                                    if (Constants.OnlineReadWriteTimeOut < 120)
                                        Constants.OnlineReadWriteTimeOut = Constants.OnlineReadWriteTimeOut + 30;
                                    String[] responseArray = response.split("-");
                                    String method = responseArray[0];
                                    logContinueLoad(preferences.getUser().no_login, method);
                                    retry(method,true);
                                })
                                .setNegativeButton(android.R.string.no, (dialog, which) -> {
                                    String current = binding.loadText.getText().toString();
                                    String newResponse = current + getString(R.string.load_text, "Carga Finalizada!");
                                    load_text.setText(newResponse);
                                    progressBar.setProgress(100);
                                    percent.setText(response + "%");
                                    button_finalizar.setVisibility(View.VISIBLE);
                                    button_finalizar.setEnabled(true);
                                    viewModelLoad.clearDatabase();
                                })
                                .setIcon(R.drawable.ic_warning_24)
                                .show();
                    } else {

                        liveDataLoad.removeObservers(this);
                        liveDataError.removeObservers(this);
                        button_finalizar.setVisibility(View.VISIBLE);
                        button_finalizar.setEnabled(true);
                        progressBar.setProgress(0);
                        percent.setText("0%");
                        String current = binding.loadText.getText().toString();
                        String newResponse = current + getString(R.string.load_text, "Falha na carga!");
                        load_text.setText(newResponse);
                        viewModelLoad.clearDatabase();
                    }
                }
            }
        });

    }

    public void loadProcess() {

        if (preferences.getSettings().carregado.equals("S")) {
            new MaterialAlertDialogBuilder(requireContext(), R.style.StyledThemeOverlay_MaterialComponents_MaterialAlertDialog)
                    .setTitle(R.string.attention)
                    .setMessage(R.string.alerta_itens_pendentes)
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        setObeservers(TableNames.ATIVIDADE);
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(R.drawable.ic_warning_24)
                    .show();
        } else {
            setObeservers(TableNames.ATIVIDADE);
        }
    }

    private void logContinueLoad(String idColetor, String metodo) {
        LogArquivo.GravaArquivoTextoDetalhado("O usuário " + preferences.getUser().no_usuario
                + " optou por continuar a Carga. Método "
                + metodo + " não retornando dados.");
    }

    public void retry(String process, boolean showToast) {
        if(showToast) {
            Toast.makeText(mContext, currentAttempt + 1 + "ª Tentativa de Carga ", Toast.LENGTH_SHORT).show();
        }
        progressBar.setVisibility(View.VISIBLE);
        setObeservers(process);
    }


}
