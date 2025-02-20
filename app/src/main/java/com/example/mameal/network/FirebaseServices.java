package com.example.mameal.network;

public interface FirebaseServices {

    void login(String email, String password, AuthenticationCallback authenticationCallback);

    void register(String email, String password, AuthenticationCallback authenticationCallback);

}
