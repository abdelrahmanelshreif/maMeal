package com.example.mameal.network;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FirebaseServicesImpl implements FirebaseServices {

    private final FirebaseAuth auth;

    public FirebaseServicesImpl() {
        this.auth = FirebaseAuth.getInstance();
    }

    @Override
    public void login(String email, String password, AuthenticationCallback authenticationCallback) {
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

            }
        });

    }

    @Override
    public void register(String email, String password, AuthenticationCallback authenticationCallback) {

    }
}
