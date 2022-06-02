package com.tees.checklist.ui.screens.unload;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import com.tees.checklist.commons.Messages;
import com.tees.checklist.commons.Preferences;
import com.tees.checklist.commons.TableNames;
import com.tees.checklist.databinding.FragmentLoadBinding;
import com.tees.checklist.ui.Injection;


public class UnloadFragment extends BaseFragment {

    private FragmentLoadBinding binding;
    private ProgressBar progressBar;
    private UnloadViewModel viewModelUnload;
    private TextView load_text;
    private TextView  percent;
    private UnloadViewModelFactory mViewModelFactory;
    private AppCompatButton button_finalizar;
    public Preferences preferences;
    public String message = "";



    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        preferences = new Preferences(mContext);
        mViewModelFactory = Injection.provideDescargaViewModelFactory(mContext);
        viewModelUnload = new ViewModelProvider(this, mViewModelFactory).get(UnloadViewModel.class);
        viewModelUnload.stopThread();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        viewModelUnload.cancelRequests();
        viewModelUnload.stopThread();
        mContext = null;
    }

    public UnloadFragment() {
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
        load_text  = binding.loadText;
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
        setObeservers(TableNames.INSPECAO_APR);
    }

    private void toolBarConfig() {
        Toolbar top_app_bar = binding.toolbar.findViewById(R.id.topAppBar);
        top_app_bar.setNavigationIcon(null);
        top_app_bar.setTitle(R.string.unload_screen_name);
    }


    private void setListeners() {

        button_finalizar.setOnClickListener(
                view -> {
                    final NavController navController = Navigation.findNavController(view);
                    navController.navigateUp();
                }
        );
    }

    public void setObeservers(String table){

        LiveData<String> liveDataUnload   = viewModelUnload.initUnload(table);
        LiveData<Integer> liveDataPercent   = viewModelUnload.getPercent();
        LiveData<String> liveDataError = viewModelUnload.getLoadError();

        liveDataUnload.observe(this, response -> {
            if(response!=null){
                if(response.equals(Messages.SUCCESS_UNLOAD) || response.equals(Messages.SUCCESS_PARTIAL_UNLOAD)){
                    String current = binding.loadText.getText().toString();
                    String newResponse = current + getString(R.string.load_text, response);
                    load_text.setText(newResponse);
                    load_text.setText(response);
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
                }else {
                    String current = binding.loadText.getText().toString();
                    String newResponse = current + getString(R.string.load_text, response.split("-")[0]);

                    load_text.setText(newResponse);
                    binding.srollview.post(() -> binding.srollview.fullScroll(View.FOCUS_DOWN));
                }
            }
        });

        liveDataPercent.observe(this, response -> {
            if(response!=null){
                progressBar.setProgress(response);
                percent.setText(response +"%");
            }
        });


        liveDataError.observe(this, response -> {
            if(response!= null && !response.isEmpty()) {


                liveDataUnload.removeObservers(this);
                    liveDataError.removeObservers(this);
                    button_finalizar.setVisibility(View.VISIBLE);
                    button_finalizar.setEnabled(true);
                    progressBar.setProgress(0);
                    percent.setText("0%");
                    String current = binding.loadText.getText().toString();
                    String newResponse = current + getString(R.string.load_text, response);
                    load_text.setText(newResponse);

            }
        });
    }

}
