package com.example.mameal.authentication.presenter;

import android.util.Patterns;

import androidx.navigation.Navigation;

import com.example.mameal.authentication.view.LoginView;
import com.example.mameal.network.AuthenticationCallback;
import com.example.mameal.network.FirebaseServices;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;


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

    public void initiateGoogleSignIn() {
        loginView.launchGoogleSignIn();
    }

    public void handleGoogleSignInResult(Task<GoogleSignInAccount> task)  {
        GoogleSignInAccount account = null;
        try {
            account = task.getResult(ApiException.class);
        } catch (ApiException e) {
            loginView.showLoginError("Error At Google Sign in");
        }
        if (account == null) {
            loginView.onGoogleSignInFailure("Google Sign-In failed: Account is null");
            return;
        }
        String idToken = account.getIdToken();
        if (idToken != null) {
            firebaseServices.signInWithGoogle(idToken, new AuthenticationCallback() {
                @Override
                public void onSuccess(FirebaseUser user) {
                    loginView.onGoogleSignInSuccess(user);
                }

                @Override
                public void onFailure(String errorMessage) {
                    loginView.onGoogleSignInFailure(errorMessage);
                }
            });
        } else {
            loginView.onGoogleSignInFailure("Google Sign-In failed: ID token is null");
        }

    }

    @Override
    public void onSuccess(FirebaseUser user) {
        loginView.showLoginSuccess();
    }

    @Override
    public void onFailure(String errorMessage) {
        loginView.showLoginError(errorMessage);
    }

    public void register() {
        loginView.NavigateRegisterScreen();
    }
}
