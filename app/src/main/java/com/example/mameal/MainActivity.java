package com.example.mameal;

import android.database.Observable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.mameal.db.MealsLocalDataSource;
import com.example.mameal.model.MaMealRepository;
import com.example.mameal.model.Meal;
import com.example.mameal.network.MaMealRemoteDataSource;
import com.example.mameal.network.NetworkCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NetworkCallback {


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
                    || destination.getId() == R.id.mealDescFragment) {

                bottomNavigationView.setVisibility(View.GONE);
                findViewById(R.id.fragmentContainerView).setPadding(0, 0, 0, 0);
            } else {
                bottomNavigationView.setVisibility(View.VISIBLE);
                int bottomNavHeight = getResources().getDimensionPixelSize(R.dimen.bottom_nav_height);
                findViewById(R.id.fragmentContainerView).setPadding(0, 0, 0, bottomNavHeight);
            }
        });


        NavigationUI.setupWithNavController(bottomNavigationView, navController);

//        MaMealRepository.getInstance(MaMealRemoteDataSource.getInstance(),new MealsLocalDataSource(this)).getAllMealsData();
    }

    @Override
    public void onSuccessResult(Observable<List<Meal>> meals) {
        Log.i("TAG", "onSuccessResult: " + meals);
    }

    @Override
    public void onFailureResult(String errMsg) {
        Log.i("TAG", "onFailureResult: " + errMsg);
    }
}