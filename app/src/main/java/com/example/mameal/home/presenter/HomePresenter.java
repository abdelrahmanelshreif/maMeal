package com.example.mameal.home.presenter;

import android.content.Context;
import android.util.Log;

import com.example.mameal.db.MealsLocalDataSource;
import com.example.mameal.home.view.HomeView;
import com.example.mameal.model.MaMealRepository;
import com.example.mameal.model.MealResponse;
import com.example.mameal.network.MaMealRemoteDataSource;
import com.example.mameal.uiModel.MealUiModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenter {

    MaMealRepository maMealRepository;
    HomeView homeView;


    public HomePresenter(HomeView homeView, Context context) {
        this.homeView = homeView;
        this.maMealRepository = MaMealRepository.getInstance(MaMealRemoteDataSource.getInstance(), new MealsLocalDataSource(context));

    }
    public void loadMeals() {
        maMealRepository.getCategoryWithMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(categoryWithMeals -> {
                            homeView.setAdaptersOfCategories(categoryWithMeals);
                        },
                        throwable -> {
                            homeView.showError(throwable.getMessage());
                        }
                );
    }

    public void getDailyMeal() {
        maMealRepository.getDailyMeal()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(MealResponse::getMeals)
                .map(meals -> meals.get(0))
                .subscribe(
                        meal -> homeView.setDailyMealData(meal),
                        throwable -> homeView.showError(throwable.getMessage())
                );
    }
}
