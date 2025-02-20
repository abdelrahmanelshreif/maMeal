package com.example.mameal.network;

public interface AuthenticationCallback {
    void onSuccess();

    void onFailure(String errorMessage);
}
