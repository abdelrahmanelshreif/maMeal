package com.example.mameal.profile.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.mameal.R;
import com.example.mameal.profile.presenter.ProfilePresenter;
import com.example.mameal.shared.Utility;


public class ProfileFragment extends Fragment implements ProfileView {

    Button btnSignOut;
    Button btnSavedPlans;
    Button btnFavouriteMeals;
    ProfilePresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ProfilePresenter(this, requireContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupUiComponent(view);

        btnSignOut.setOnClickListener(v -> presenter.handleSignOut());
        btnSavedPlans.setOnClickListener(v -> presenter.navigateToPlans());
        btnFavouriteMeals.setOnClickListener(v -> presenter.navToFav());

    }

    private void setupUiComponent(@NonNull View view) {
        btnSignOut = view.findViewById(R.id.btnSignOut);
        btnSavedPlans = view.findViewById(R.id.btnPlannedMeals);
        btnFavouriteMeals = view.findViewById(R.id.btnFavoriteMeals);
    }

    @Override
    public void showSignOutSeccess(String message) {
        Utility.showToast(requireContext(), "SignOut Done Successfully");
    }

    @Override
    public void navigateToLogin() {
        Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_loginFragment);
    }

    @Override
    public void showError(String errorMessage) {
        Utility.showToast(requireContext(), errorMessage);
    }

    @Override
    public void navigateToFav() {
        Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_favouriteMealsFragment);
    }

    @Override
    public void navigateToPlans() {
        Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_calenderFragment);

    }
}