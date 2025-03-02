package com.example.mameal.profile.view;

public interface ProfileView {

    void showSignOutSeccess(String message);

    void navigateToLogin();

    void showError(String errorMessage);

    void navigateToFav();

    void navigateToPlans();
}
