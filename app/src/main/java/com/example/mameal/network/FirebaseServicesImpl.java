package com.example.mameal.network;

import androidx.annotation.NonNull;

import com.example.mameal.authentication.presenter.LoginPresenter;
import com.example.mameal.authentication.presenter.RegisterPresenter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;

public class FirebaseServicesImpl implements FirebaseServices {

    private final FirebaseAuth auth;

    LoginPresenter loginPresenter;

    RegisterPresenter registerPresenter;


    public FirebaseServicesImpl() {
        this.auth = FirebaseAuth.getInstance();
    }

    @Override
    public void login(String email, String password, AuthenticationCallback authenticationCallback) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    authenticationCallback.onSuccess(auth.getCurrentUser());
                } else {
                    authenticationCallback.onFailure(task.getException().getMessage().toString());
                }
            }
        });

    }

    @Override
    public void register(String email, String password, AuthenticationCallback authenticationCallback) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    authenticationCallback.onSuccess(auth.getCurrentUser());
                } else {
                    authenticationCallback.onFailure(task.getException().getMessage().toString());
                }
            }
        });

    }

    @Override
    public void signInWithGoogle(String idToken, AuthenticationCallback callback) {
        try {
            AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

            auth.signInWithCredential(credential)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            callback.onSuccess(user);
                        } else {
                            Exception exception = task.getException();
                            String errorMessage = (exception != null) ?
                                    exception.getMessage() :
                                    "Unknown error during Google sign-in";
                            callback.onFailure(errorMessage);
                        }
                    });
        } catch (Exception e) {
            callback.onFailure("Error creating credential: " + e.getMessage());
        }
    }


}
