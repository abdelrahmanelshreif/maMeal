package com.example.mameal.main.view;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.mameal.R;
import com.example.mameal.main.presenter.MainPresenter;
import com.example.mameal.main.view.MainView;
import com.example.mameal.network.NetworkChangeListener;
import com.example.mameal.network.NetworkMonitor;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements MainView, NetworkChangeListener {
    private NavController navController;
    private BottomNavigationView bottomNavigationView;
    private MainPresenter presenter;
    private NetworkMonitor networkMonitor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUiComponent();

        presenter = new MainPresenter(this,this);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView);
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        }

        if (navController != null) {
            navController.addOnDestinationChangedListener((controller, destination, arguments) ->
                    presenter.handleDestinationChange(destination.getId())
            );
            NavigationUI.setupWithNavController(bottomNavigationView, navController);
        }

        networkMonitor = new NetworkMonitor(this);
        registerReceiver(networkMonitor, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        bottomNavigationView.setOnItemSelectedListener(item -> {
            presenter.handleBottomNavigationClick(item.getItemId());
            return true;
        });
        presenter.uploadCollectionsToFirebase();

    }

    private void setupUiComponent() {
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavigationViewLayout);
    }

    @Override
    public void onNetworkChanged(boolean isConnected) {
        presenter.updateNetworkStatus(isConnected);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateBottomNavVisibility(boolean isVisible) {
        bottomNavigationView.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setBottomPadding(int padding) {
        findViewById(R.id.fragmentContainerView).setPadding(0, 0, 0, padding);
    }
    @Override
    public void navigateTo(int destination) {
        if (navController != null) {
            navController.navigate(destination);
        }
    }

    @Override
    public int getBottomNavHeight() {
        return bottomNavigationView.getHeight();
    }

    @Override
    public void disableMenuItem(int itemId, boolean isDisabled) {
        MenuItem item = bottomNavigationView.getMenu().findItem(itemId);
        if (item != null) {
            item.setEnabled(!isDisabled);
        }
    }
}
