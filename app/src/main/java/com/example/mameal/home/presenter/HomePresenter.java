package com.example.mameal.home.presenter;

import android.content.Context;
import android.view.View;

import com.example.mameal.db.MealsLocalDataSource;
import com.example.mameal.home.view.HomeView;
import com.example.mameal.model.MaMealRepository;
import com.example.mameal.model.MealResponse;
import com.example.mameal.network.MaMealRemoteDataSource;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenter {

    MaMealRepository maMealRepository;
    HomeView homeView;

    private CompositeDisposable compositeDisposable;


    public HomePresenter(HomeView homeView, Context context) {
        this.homeView = homeView;
        this.maMealRepository = MaMealRepository.getInstance(MaMealRemoteDataSource.getInstance(), new MealsLocalDataSource(context));
        compositeDisposable = new CompositeDisposable();
    }

    public void loadMealsSections() {
        Disposable disposable = maMealRepository.getCategoryWithMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(categoryWithMeals -> {
                            homeView.setAdaptersOfCategories(categoryWithMeals);
                        },
                        throwable -> {
                            homeView.showError(throwable.getMessage());
                        }
                );
        compositeDisposable.add(disposable);

    }

    public void getDailyMeal() {
        Disposable disposable = maMealRepository.getDailyMeal()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(MealResponse::getMeals)
                .map(meals -> meals.get(0))
                .subscribe(
                        meal -> homeView.setDailyMealData(meal),
                        throwable -> homeView.showError(throwable.getMessage())
                );

        compositeDisposable.add(disposable);
    }

    public void detachView() {
        compositeDisposable.clear();
    }

    public void handleMealsNavigation(View view, String mealId) {
        homeView.navigateToMealDescription(view, mealId);

    }
}
