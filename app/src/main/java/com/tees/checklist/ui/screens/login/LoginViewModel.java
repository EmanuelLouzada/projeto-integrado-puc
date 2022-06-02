package com.tees.checklist.ui.screens.login;

import android.os.AsyncTask;
import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tees.checklist.R;
import com.tees.checklist.commons.Messages;
import com.tees.checklist.data.model.Usuario;
import com.tees.checklist.service.LoginService;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private final LoginService mService;

    public LoginViewModel(LoginService service) {
        mService = service;
    }


    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        class GetLogin extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(Void... voids) {
                String result = mService.login(username, password);
                return result;
            }
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if(result.equals(Messages.SUCCESS_MESSAGE)){
                    Usuario data =    mService.getUser();
                    loginResult.setValue(new LoginResult(new LoggedInUserView(data.no_usuario)));
                }else{
                    loginResult.setValue(new LoginResult(result));
                }
            }
        }

        try {
            GetLogin result = new GetLogin();
            result.execute();
        } catch (Exception e) {
            loginResult.setValue(new LoginResult(String.valueOf(R.string.login_failed)));

        }
    }


    public void loginDataChanged(String username, String password) {
       /* if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {*/
            loginFormState.setValue(new LoginFormState(true));
        //}
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}
