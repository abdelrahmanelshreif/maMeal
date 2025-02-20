package com.example.mameal.authentication.presenter;

import android.util.Patterns;

import com.example.mameal.authentication.view.LoginView;
import com.example.mameal.network.AuthenticationCallback;
import com.example.mameal.network.FirebaseServices;

public class LoginPresenter implements AuthenticationCallback {

    private LoginView loginView;
    private FirebaseServices firebaseServices;

    public LoginPresenter(LoginView loginView, FirebaseServices firebaseServices) {
        this.loginView = loginView;
        this.firebaseServices = firebaseServices;
    }


    public void loginUser(String email, String password) {

        boolean isValidData = validateData(email, password);

        if (isValidData) {
            firebaseServices.login(email, password, this);
        }
    }

    private boolean validateData(String email, String password) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            loginView.invalidEmailLoginData();
            return false;
        } else {
            if (password.length() < 6) {
                loginView.invalidPasswordLoginData();
                return false;
            }
        }
        return true;
    }

    @Override
    public void onSuccess() {
        loginView.showLoginSuccess();
    }

    @Override
    public void onFailure(String errorMessage) {
        loginView.showLoginError(errorMessage);
    }
}
