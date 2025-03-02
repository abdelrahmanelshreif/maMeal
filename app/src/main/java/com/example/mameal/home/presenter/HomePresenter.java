package com.example.mameal.home.presenter;

import android.content.Context;
import android.view.View;

import com.example.mameal.db.MealsLocalDataSource;
import com.example.mameal.home.view.HomeView;
import com.example.mameal.model.MaMealRepository;
import com.example.mameal.model.MealResponse;
import com.example.mameal.network.FirebaseServices;
import com.example.mameal.network.FirebaseServicesImpl;
import com.example.mameal.network.MaMealRemoteDataSource;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenter {

    MaMealRepository maMealRepository;
    HomeView homeView;

    private CompositeDisposable compositeDisposable;
    FirebaseServices firebaseServices;

    public HomePresenter(HomeView homeView, Context context) {
        this.homeView = homeView;
        this.maMealRepository = MaMealRepository.getInstance(MaMealRemoteDataSource.getInstance(), new MealsLocalDataSource(context));
        compositeDisposable = new CompositeDisposable();
        this.firebaseServices = new FirebaseServicesImpl();
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

    public void handleAddToFav(String mealId) {
        if (!firebaseServices.isUserLoggedIn()){
            homeView.showError("You Should Log in to access this feature");
            return;
        }
        Disposable disposable = maMealRepository.getMealById(mealId)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(
                        meal -> maMealRepository.insert(meal)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(() -> homeView.successAddingToFav("Successfully added to favourite")
                                        , throwable -> homeView.showError("Error at Adding to Favourite")
                                )
                );
        compositeDisposable.add(disposable);
    }
}
