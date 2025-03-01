package com.example.mameal.splash.presenter;

import com.example.mameal.network.FirebaseServices;
import com.example.mameal.network.FirebaseServicesImpl;
import com.example.mameal.splash.view.SplashScreenView;

public class SplashScreenPresenter {
    SplashScreenView view;
    FirebaseServices firebaseServices;

    public SplashScreenPresenter(SplashScreenView view) {
        this.view = view;
        firebaseServices = new FirebaseServicesImpl();
    }

    public void checkUserSession() {
        boolean isAuthenticated = firebaseServices.isUserLoggedIn();
        if (isAuthenticated)
            view.navigateToHome();
        else
            view.navToLogin();
    }

}
