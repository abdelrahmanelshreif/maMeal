package com.example.mameal.authentication.presenter;

import android.util.Patterns;

import com.example.mameal.authentication.view.RegisterView;
import com.example.mameal.network.AuthenticationCallback;
import com.example.mameal.network.FirebaseServices;

public class RegisterPresenter implements AuthenticationCallback {

    FirebaseServices firebaseServices;
    RegisterView registerView;

    public RegisterPresenter(FirebaseServices firebaseServices, RegisterView registerView) {
        this.firebaseServices = firebaseServices;
        this.registerView = registerView;
    }

    public void register(String email, String password, String confirmPassword, boolean termsAcceptance) {

        boolean isValidated = validateData(email, password, confirmPassword, termsAcceptance);
        if (isValidated) {
            firebaseServices.register(email, password, this);
        }

    }

    private boolean validateData(String email, String password, String confirmPassword, boolean termsAcceptance) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            registerView.invalidEmailLoginData();
            return false;
        }
        if (password.length() < 8) {
            registerView.invalidPasswordLoginData();
            return false;
        }
        if (!password.equals(confirmPassword)) {
            registerView.invalidPasswordConfirmation();
            return false;
        }
        if (!termsAcceptance) {
            registerView.invalidCheckingTerms();
            return false;
        }
        return true;
    }

    @Override
    public void onSuccess() {
        registerView.showRegisterSuccess();
    }

    @Override
    public void onFailure(String errorMessage) {
        registerView.showRegisterError(errorMessage);
    }
}
