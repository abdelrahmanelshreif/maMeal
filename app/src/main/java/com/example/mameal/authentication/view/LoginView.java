package com.example.mameal.authentication.view;

public interface LoginView {
    void invalidEmailLoginData();
    void invalidPasswordLoginData();
    void showLoginSuccess();
    void showLoginError(String errorMessage);

}
