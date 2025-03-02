package com.example.mameal.profile.presenter;

import android.content.Context;

import com.example.mameal.db.MealsLocalDataSource;
import com.example.mameal.model.MaMealRepository;
import com.example.mameal.network.AuthenticationCallback;
import com.example.mameal.network.FirebaseServices;
import com.example.mameal.network.FirebaseServicesImpl;
import com.example.mameal.network.MaMealRemoteDataSource;
import com.example.mameal.profile.view.ProfileView;
import com.google.firebase.auth.FirebaseUser;

public class ProfilePresenter implements AuthenticationCallback {

    FirebaseServices firebaseServices;
    ProfileView view;
    private MaMealRepository repository;
    public ProfilePresenter(ProfileView profileView, Context context){
        this.view = profileView;
        repository = MaMealRepository.getInstance(MaMealRemoteDataSource.getInstance(),new MealsLocalDataSource(context));
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
        repository.userOutClear();
        view.showSignOutSeccess("Signed out successfully");
        view.navigateToLogin();

    }

    @Override
    public void onFailure(String errorMessage) {
        view.showError(errorMessage);
    }
}
