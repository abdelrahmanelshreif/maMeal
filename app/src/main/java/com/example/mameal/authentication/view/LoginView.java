package com.example.mameal.authentication.view;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseUser;

public interface LoginView {
    void launchGoogleSignIn();
    void invalidEmailLoginData();
    void invalidPasswordLoginData();
    void showLoginSuccess();
    void showLoginError(String errorMessage);
    void onGoogleSignInSuccess(FirebaseUser user);
    void onGoogleSignInFailure(String errorMessage);

}
