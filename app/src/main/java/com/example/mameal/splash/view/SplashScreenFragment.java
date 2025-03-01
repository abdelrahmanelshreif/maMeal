package com.example.mameal.splash.view;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.mameal.R;
import com.example.mameal.splash.presenter.SplashScreenPresenter;

public class SplashScreenFragment extends Fragment implements SplashScreenView {
    private static final int SPLASH_DISPLAY_LENGTH = 3000;
    SplashScreenPresenter presenter;

    public SplashScreenFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SplashScreenPresenter(this);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new Handler().postDelayed(() -> presenter.checkUserSession(), SPLASH_DISPLAY_LENGTH);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false);
    }

    @Override
    public void navigateToHome() {
        Navigation.findNavController(requireView()).navigate(R.id.action_splashScreenFragment_to_homeFragment);
    }

    @Override
    public void navToLogin() {
        Navigation.findNavController(requireView()).navigate(R.id.action_splashScreenFragment_to_loginFragment);

    }
}