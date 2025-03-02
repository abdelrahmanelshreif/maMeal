package com.example.mameal.profile.presenter;

import com.example.mameal.network.AuthenticationCallback;
import com.example.mameal.network.FirebaseServices;
import com.example.mameal.network.FirebaseServicesImpl;
import com.example.mameal.profile.view.ProfileView;
import com.google.firebase.auth.FirebaseUser;

public class ProfilePresenter implements AuthenticationCallback {

    FirebaseServices firebaseServices;

    ProfileView view;
    public ProfilePresenter(ProfileView profileView){
        this.view = profileView;
        firebaseServices = new FirebaseServicesImpl();
    }
    public void handleSignOut() {
        firebaseServices.signOut(this);
    }

    public void navToFav() {
        view.navigateToFav();
    }

    public void navigateToPlans() {
        view.navigateToPlans();
    }

    @Override
    public void onSuccess(FirebaseUser user) {
        view.showSignOutSeccess("Signed out successfully");
        view.navigateToLogin();

    }

    @Override
    public void onFailure(String errorMessage) {
        view.showError(errorMessage);
    }
}
