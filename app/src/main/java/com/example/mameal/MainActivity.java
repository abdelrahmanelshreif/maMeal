package com.example.mameal;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    private NavController navController;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavigationViewLayout);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        NavController navController = navHostFragment.getNavController();

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId() == R.id.splashScreenFragment
                    || destination.getId() == R.id.loginFragment
                    || destination.getId() == R.id.registerFragment
                    || destination.getId() == R.id.mealDescFragment
                    || destination.getId() == R.id.filteredMealsFragment
            ) {

                bottomNavigationView.setVisibility(View.GONE);
                findViewById(R.id.fragmentContainerView).setPadding(0, 0, 0, 0);
            } else {
                bottomNavigationView.setVisibility(View.VISIBLE);
                int bottomNavHeight = getResources().getDimensionPixelSize(R.dimen.bottom_nav_height);
                findViewById(R.id.fragmentContainerView).setPadding(0, 0, 0, bottomNavHeight);
            }
        });


        NavigationUI.setupWithNavController(bottomNavigationView, navController);


    }
}



