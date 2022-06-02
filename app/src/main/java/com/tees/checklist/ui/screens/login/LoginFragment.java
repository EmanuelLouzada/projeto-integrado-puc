package com.tees.checklist.ui.screens.login;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.tees.checklist.R;
import com.tees.checklist.base.BaseFragment;
import com.tees.checklist.commons.Constants;
import com.tees.checklist.commons.Preferences;
import com.tees.checklist.databinding.FragmentLoginBinding;
import com.tees.checklist.ui.Injection;

public class LoginFragment extends BaseFragment {

    private FragmentLoginBinding binding;
    Preferences preferences;
    private LoginViewModel loginViewModel;
    private LoginViewModelFactory mViewModelFactory;

    public LoginFragment() {
        // Required empty public constructor
    }

    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        preferences = new Preferences(mContext);
        preferences.clearUser();
        mViewModelFactory = Injection.provideLoginViewModelFactory(mContext);
        loginViewModel = new ViewModelProvider(this, mViewModelFactory).get(LoginViewModel.class);
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
                R.layout.fragment_login,
                container,
                false
        );


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        config();
        final NavController navController = Navigation.findNavController(view);

        final TextInputEditText usernameEditText = binding.usernameTextInput;
        final TextInputEditText passwordEditText = binding.passwordTextInput;
        final AppCompatButton loginButton = binding.buttonLogin;

       /* final TextView versionText = binding.versionText;
        try{
            String version = BuildConfig.VERSION_NAME;
            versionText.setText(version);
        }catch (Exception e){
            versionText.setText("");
        }*/



        if(Constants.AUTOLOGIN){
            loginViewModel.login("admin","admin");
        }

        loginViewModel.getLoginFormState().observe(getViewLifecycleOwner(), loginFormState -> {
            if (loginFormState == null) {
                return;
            }
            loginButton.setEnabled(loginFormState.isDataValid());
            if (loginFormState.getUsernameError() != null) {
                usernameEditText.setError(getString(loginFormState.getUsernameError()));
            }
            if (loginFormState.getPasswordError() != null) {
                passwordEditText.setError(getString(loginFormState.getPasswordError()));
            }
        });

        loginViewModel.getLoginResult().observe(getViewLifecycleOwner(), loginResult -> {
            if (loginResult == null) {
                return;
            }
            setLoading(false);
            if (loginResult.getError() != null) {
                showLoginFailed(loginResult.getError());
            }
            if (loginResult.getSuccess() != null) {
                navController.popBackStack();
            }
            getActivity().setResult(Activity.RESULT_OK);


            //Complete and destroy login activity once successful
            // finish();
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };

        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);


        passwordEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
            return false;
        });

        loginButton.setOnClickListener(v -> {
            setLoading(true);
            loginViewModel.login(usernameEditText.getText().toString(),
                    passwordEditText.getText().toString());
            setLoading(false);
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        preferences.clearUser();
                    }
                });
    }

    private void showLoginFailed(String errorString) {
        new MaterialAlertDialogBuilder(requireContext(), R.style.StyledThemeOverlay_MaterialComponents_MaterialAlertDialog)
                .setTitle(R.string.error_message)
                .setMessage(errorString)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(R.drawable.ic_error_24)
                .show();
    }


    private void setLoading(Boolean action) {
        if (action) {
            binding.loading.setVisibility(View.VISIBLE);
            binding.buttonLogin.setVisibility(View.INVISIBLE);
        } else {
            binding.loading.setVisibility(View.GONE);
            binding.buttonLogin.setVisibility(View.VISIBLE);
        }
    }

}
