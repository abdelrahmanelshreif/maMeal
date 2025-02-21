package com.example.mameal;

import android.database.Observable;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mameal.authentication.view.LoginFragment;
import com.example.mameal.authentication.view.RegisterFragment;
import com.example.mameal.home.view.HomeFragment;
import com.example.mameal.model.MaMealRepository;
import com.example.mameal.model.Meal;
import com.example.mameal.network.MaMealRemoteDataSource;
import com.example.mameal.network.NetworkCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NetworkCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainerView, new HomeFragment());
        fragmentTransaction.commit();

        MaMealRepository.getInstance(new MaMealRemoteDataSource()).getAllMealsData(this);
    }

    @Override
    public void onSuccessResult(Observable<List<Meal>> meals) {
        Log.i("TAG", "onSuccessResult: "+meals);
    }

    @Override
    public void onFailureResult(String errMsg) {
        Log.i("TAG", "onFailureResult: "+errMsg);
    }
}