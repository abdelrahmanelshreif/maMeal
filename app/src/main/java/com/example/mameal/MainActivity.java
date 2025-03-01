package com.example.mameal;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.mameal.network.NetworkChangeListener;
import com.example.mameal.network.NetworkMonitor;
import com.example.mameal.shared.Utility;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements NetworkChangeListener {

    private NavController navController;
    private BottomNavigationView bottomNavigationView;
    private NetworkMonitor networkMonitor;
    private boolean isConnected = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUiComponent();

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }

        if (navController != null) {
            navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
                if (destination.getId() == R.id.splashScreenFragment
                        || destination.getId() == R.id.loginFragment
                        || destination.getId() == R.id.registerFragment
                        || destination.getId() == R.id.filteredMealsFragment) {

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

        networkMonitor = new NetworkMonitor(this);
        registerReceiver(networkMonitor, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        // Set listener for bottom navigation
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (!isConnected && isRestrictedDestination(item.getItemId())) {
                Utility.showToast(this, "No internet connection. Please try again later.");
                return false;
            }

            if (navController != null) {
                int currentDestination = navController.getCurrentDestination() != null ?
                        navController.getCurrentDestination().getId() : -1;

                if (currentDestination != item.getItemId()) {
                    navController.navigate(item.getItemId());
                }
            }
            return true;
        });
    }

    private void setupUiComponent() {
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavigationViewLayout);
    }

    @Override
    public void onNetworkChanged(boolean isConnected) {
        this.isConnected = isConnected;
        Menu menu = bottomNavigationView.getMenu();

        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setEnabled(true);
        }

        if (navController != null) {
            int currentDestination = navController.getCurrentDestination() != null ?
                    navController.getCurrentDestination().getId() : -1;

            if (!isConnected && currentDestination != R.id.networkFailureFragment) {
                navController.navigate(R.id.networkFailureFragment);
            } else if (isConnected && currentDestination == R.id.networkFailureFragment) {
                navController.popBackStack();
            }
        }
    }

    private boolean isRestrictedDestination(int itemId) {
        return !isConnected && (itemId != R.id.calenderFragment && itemId != R.id.favouriteMealsFragment );
    }
}
