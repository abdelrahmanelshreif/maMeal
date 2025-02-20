package com.example.mameal.authentication.view;

public interface RegisterView {
    void invalidEmailLoginData();

    void invalidPasswordLoginData();


    void invalidPasswordConfirmation();

    void invalidCheckingTerms();

    void showRegisterSuccess();

    void showRegisterError(String errorMessage);
}
