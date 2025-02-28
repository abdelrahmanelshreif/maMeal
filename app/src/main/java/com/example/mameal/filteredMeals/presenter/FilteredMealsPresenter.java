package com.example.mameal.filteredMeals.presenter;

import android.content.Context;

import com.example.mameal.db.MealsLocalDataSource;
import com.example.mameal.model.MaMealRepository;
import com.example.mameal.model.MealResponse;
import com.example.mameal.network.MaMealRemoteDataSource;
import com.example.mameal.search.view.SearchView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FilteredMealsPresenter {


    FilteredMealsView filterView;
    CompositeDisposable compositeDisposable;
    MaMealRepository repository;

    public FilteredMealsPresenter(FilteredMealsView filterView, Context context) {
        this.repository = MaMealRepository.getInstance(MaMealRemoteDataSource.getInstance(), new MealsLocalDataSource(context));
        this.filterView = filterView;
        compositeDisposable = new CompositeDisposable();
    }

    public void getMealsFilteredByCategory(String category) {
        filterView.showLoading();
        Disposable disposable = repository.getAllMealsData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(MealResponse::getMeals)
                .flatMapIterable(meals -> meals)
                .filter(meal -> meal.getMealCategory().equals(category)
                ).toList()
                .subscribe(
                        meals -> {
                            filterView.showMealsFilteredByCategory(meals);
                            filterView.hideLoading();
                        },
                        throwable -> filterView.showError(throwable.getMessage())
                );
        compositeDisposable.add(disposable);
    }

    public void getMealsFilteredByIngredients(String ingredient) {
        filterView.showLoading();
        // getting all meals
        // then filter it if the ingredients any match with strIngredient
        // show meals that matches the required ingredient
        Disposable disposable = repository.getAllMealsData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(MealResponse::getMeals)
                .flatMapIterable(meals -> meals)
                .filter(meal -> {

                    for (String tempIngredient : meal.getIngredients()) {
                        if (tempIngredient.equals(ingredient))
                            return true;
                    }
                    return false;
                }).toList()
                .subscribe(

                        meals -> {
                            filterView.showMealsFilteredByIngredient(meals);
                            filterView.hideLoading();
                        },
                        throwable -> filterView.showError(throwable.getMessage())
                );
        compositeDisposable.add(disposable);
    }



    public void getMealsFilteredByCountry(String country) {
        filterView.showLoading();
        Disposable disposable = repository.getAllMealsData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(MealResponse::getMeals)
                .flatMapIterable(meals -> meals)
                .filter(meal -> meal.getMealArea().equals(country)
                ).toList()
                .subscribe(
                        meals -> {
                            filterView.showMealsFilteredByCountry(meals);
                            filterView.hideLoading();
                        },
                        throwable -> filterView.showError(throwable.getMessage())
                );
        compositeDisposable.add(disposable);
    }

    public void filterDelegator(String filterType, String filter) {
        switch (filterType) {
            case SearchView.CATEGORY:
                getMealsFilteredByCategory(filter);
                break;

            case SearchView.INGREDIENT:
                getMealsFilteredByIngredients(filter);
                break;
            case SearchView.COUNTRY:
                getMealsFilteredByCountry(filter);
                break;
        }
    }
}
