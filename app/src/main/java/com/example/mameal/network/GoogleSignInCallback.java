package com.example.mameal.network;

import com.google.firebase.auth.FirebaseUser;

public interface GoogleSignInCallback {
    void onSuccess(FirebaseUser user);
    void onFailure(String errorMessage);
}
