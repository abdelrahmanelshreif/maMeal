package com.example.mameal.network;

import android.credentials.Credential;

public interface FirebaseServices {

    void login(String email, String password, AuthenticationCallback authenticationCallback);

    void register(String email, String password, AuthenticationCallback authenticationCallback);

    void signInWithGoogle(String idToken, AuthenticationCallback callback);
    void signOut(AuthenticationCallback callback);

    boolean isUserLoggedIn();

}
