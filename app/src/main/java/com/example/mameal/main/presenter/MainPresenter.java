package com.example.mameal.main.presenter;

import android.content.Context;

import com.example.mameal.R;
import com.example.mameal.db.MealsLocalDataSource;
import com.example.mameal.main.view.MainView;
import com.example.mameal.model.MaMealRepository;
import com.example.mameal.network.MaMealRemoteDataSource;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainPresenter {
    private MainView view;
    private boolean isConnected = true;

    MaMealRepository maMealRepository;

    public MainPresenter(MainView view, Context context) {
        this.view = view;
        this.maMealRepository = MaMealRepository.getInstance(MaMealRemoteDataSource.getInstance(), new MealsLocalDataSource(context));
    }

    public void handleDestinationChange(int destinationId) {
        if (destinationId == R.id.splashScreenFragment ||
                destinationId == R.id.loginFragment ||
                destinationId == R.id.registerFragment ||
                destinationId == R.id.filteredMealsFragment) {

            view.updateBottomNavVisibility(false);
            view.setBottomPadding(0);
        } else {
            view.updateBottomNavVisibility(true);
            int bottomNavHeight = view.getBottomNavHeight();
            view.setBottomPadding(bottomNavHeight);
        }
    }

    public void handleBottomNavigationClick(int itemId) {
        if (!isConnected && isRestrictedForNetwork(itemId)) {
            view.showToast("No internet connection!");
            view.navigateTo(R.id.networkFailureFragment);
            return;
        }

        if (isGuestMode() && isRestrictedForGuest(itemId)) {
            view.showToast("Sign in to access this feature!");
            view.navigateTo(R.id.loginFragment); // Navigate to login fragment
            return;
        }

        view.navigateTo(itemId);
    }

    public void updateNetworkStatus(boolean isConnected) {
        this.isConnected = isConnected;
        int[] allowedOffline = {R.id.favouriteMealsFragment, R.id.calenderFragment};

        for (int itemId : allowedOffline) {
            view.disableMenuItem(itemId, false);
        }

        if (!isConnected) {
            view.navigateTo(R.id.networkFailureFragment);
        }
    }

    private boolean isRestrictedForNetwork(int itemId) {
        return !isConnected && (itemId != R.id.calenderFragment && itemId != R.id.favouriteMealsFragment);
    }

    private boolean isGuestMode() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        return currentUser == null;
    }

    private boolean isRestrictedForGuest(int itemId) {
        int[] allowedForGuest = {R.id.homeFragment, R.id.searchFragment};
        for (int allowedId : allowedForGuest) {
            if (itemId == allowedId) return false;
        }
        return true;
    }

    public void uploadCollectionsToFirebase() {
        maMealRepository.uploadMealsToFirebase();
        maMealRepository.uploadEventsToFirebase();
    }
}
